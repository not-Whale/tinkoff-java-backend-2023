package edu.homework10.cache_proxy;

import edu.homework10.cache_proxy.annotations.Cache;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheProxy implements AutoCloseable {
    private static final List<Handler> HANDLERS = new ArrayList<>();

    public CacheProxy() {}

    public Object create(Object targetInstance, Class<?> targetInterface) {
        if (!implementsInterface(targetInstance, targetInterface) && targetInterface.getMethods().length != 1) {
            throw new IllegalArgumentException();
        }
        Cache annotation = targetInterface.getMethods()[0].getAnnotation(Cache.class);
        Handler handler = new Handler(targetInstance, annotation.persist());
        if (annotation.persist()) {
            HANDLERS.add(handler);
        }
        return Proxy.newProxyInstance(
            targetInstance.getClass().getClassLoader(),
            new Class[] {targetInterface},
            handler
        );
    }

    private boolean implementsInterface(Object instance, Class<?> targetInterface) {
        for (var currentInterface : instance.getClass().getInterfaces()) {
            if (currentInterface.equals(targetInterface)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() {
        for (var handler : HANDLERS) {
            handler.close();
        }
    }

    private static class Handler implements InvocationHandler, Closeable {
        private static final Path ROOT_CACHE_PATH = Path.of("src", "main", "resources", "homework10");

        private final Object targetInstance;

        private final Map<Object[], Object> cache;

        private final Path cachePath;

        private final boolean persist;

        Handler(Object targetInstance, boolean persist) {
            this.targetInstance = targetInstance;
            this.cachePath = ROOT_CACHE_PATH.resolve(targetInstance.toString() + ".txt");
            this.persist = persist;
            if (persist) {
                cache = null;
                initDiskCache();
            } else {
                cache = new HashMap<>();
            }
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args)
            throws InvocationTargetException, IllegalAccessException {
            Object result;

            if (persist) {
                result = checkDiskCache(args);
            } else {
                result = checkCache(cache, args);
            }

            if (result != null) {
                return result;
            }

            result = method.invoke(targetInstance, args);
            if (persist) {
                putInDiskCache(args, result);
            } else {
                cache.put(args, result);
            }

            return result;
        }

        private void initDiskCache() {
            try {
                Files.createDirectories(ROOT_CACHE_PATH);
                Files.createFile(cachePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try (ObjectOutputStream out = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(cachePath.toFile())))) {
                Map<Object[], Object> diskCache = new HashMap<>();
                out.writeObject(diskCache);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private Object checkDiskCache(Object[] args) {
            try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(cachePath.toFile())))) {
                Map<Object[], Object> mapCache = (Map<Object[], Object>) in.readObject();
                return checkCache(mapCache, args);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        private void putInDiskCache(Object[] args, Object result) {
            try (ObjectInputStream in = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(cachePath.toFile())));
                 ObjectOutputStream out = new ObjectOutputStream(
                     new BufferedOutputStream(new FileOutputStream(cachePath.toFile())))) {
                Map<Object[], Object> mapCache = (Map<Object[], Object>) in.readObject();
                mapCache.put(args, result);
                out.writeObject(mapCache);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }

        private static Object checkCache(Map<Object[], Object> cache, Object[] args) {
            for (var entry : cache.entrySet()) {
                if (Arrays.equals(entry.getKey(), args)) {
                    return entry.getValue();
                }
            }
            return null;
        }

        @Override
        public void close() {
            cachePath.toFile().delete();
            ROOT_CACHE_PATH.toFile().delete();
        }
    }
}

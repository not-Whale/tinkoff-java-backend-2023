package edu.homework7.person_database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Database implements PersonDatabase {
    ReadWriteLock lock = new ReentrantReadWriteLock(true);

    Map<String, Person> idMap = new HashMap<>();

    Map<String, List<Person>> nameMap = new HashMap<>();

    Map<String, List<Person>> addressMap = new HashMap<>();

    Map<String, List<Person>> phoneMap = new HashMap<>();

    private String reverseIdToString(int id) {
        return new StringBuilder(String.valueOf(id)).reverse().toString();
    }

    private String reverseString(String s) {
        return new StringBuilder(s).reverse().toString();
    }

    @Override
    public void add(Person person) {
        if (person.name() != null && person.address() != null && person.phoneNumber() != null) {
            lock.writeLock().lock();
            try {
                // id map add
                idMap.put(reverseIdToString(person.id()), person);

                // name map add
                String nameKey = reverseString(person.name());
                List<Person> nameList = nameMap.getOrDefault(nameKey, new ArrayList<>());
                nameList.add(person);
                nameMap.put(nameKey, nameList);

                // address map add
                String addressKey = reverseString(person.address());
                List<Person> addressList = addressMap.getOrDefault(addressKey, new ArrayList<>());
                addressList.add(person);
                addressMap.put(addressKey, addressList);

                // phone map add
                String phoneKey = reverseString(person.phoneNumber());
                List<Person> phoneList = phoneMap.getOrDefault(phoneKey, new ArrayList<>());
                phoneMap.put(phoneKey, phoneList);
            } finally {
                lock.writeLock().unlock();
            }
        }
    }

    @Override
    public void delete(int id) {
        lock.writeLock().lock();
        try {
            Person person = idMap.get(reverseIdToString(id));

            // id map remove
            idMap.remove(reverseIdToString(id));

            // name map remove
            String nameKey = reverseString(person.name());
            List<Person> nameList = nameMap.getOrDefault(nameKey, new ArrayList<>());
            nameList.remove(person);
            nameMap.put(nameKey, nameList);

            // address map remove
            String addressKey = reverseString(person.address());
            List<Person> addressList = addressMap.getOrDefault(addressKey, new ArrayList<>());
            addressList.remove(person);
            addressMap.put(addressKey, addressList);

            // phone map remove
            String phoneKey = reverseString(person.phoneNumber());
            List<Person> phoneList = phoneMap.getOrDefault(phoneKey, new ArrayList<>());
            phoneList.remove(person);
            phoneMap.put(phoneKey, phoneList);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        lock.readLock().lock();
        try {
            return nameMap.getOrDefault(reverseString(name), List.of());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        lock.readLock().lock();
        try {
            return addressMap.getOrDefault(reverseString(address), List.of());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        lock.readLock().lock();
        try {
            return phoneMap.getOrDefault(reverseString(phone), List.of());
        } finally {
            lock.readLock().unlock();
        }
    }
}

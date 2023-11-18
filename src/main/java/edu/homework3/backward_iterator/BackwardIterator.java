package edu.homework3.backward_iterator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class BackwardIterator<T> implements Iterator<T> {
    private final ArrayList<T> container;

    private int position;

    public BackwardIterator(Collection<T> collection) {
        this.container = initContainer(collection);
        this.position = 0;
    }

    private ArrayList<T> initContainer(Collection<T> collection) {
        ArrayList<T> tmpContainer = new ArrayList<>();
        for (T elem : collection) {
            tmpContainer.addFirst(elem);
        }
        return tmpContainer;
    }

    @Override
    public boolean hasNext() {
        return position < container.size();
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return container.get(position++);
    }
}

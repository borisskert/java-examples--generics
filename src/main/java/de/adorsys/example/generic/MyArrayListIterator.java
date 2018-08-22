package de.adorsys.example.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class MyArrayListIterator<E> implements Iterator<E> {

    private final MyArrayList<E> list;

    private int position = 0;

    public MyArrayListIterator(MyArrayList<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {

        if (position < list.size()) {
            return true;
        }
        return false;
    }

    @Override
    public E next() {
        if (position < list.size()) {
            E element = list.get(position);
            position++;
            return element;
        }

        throw new NoSuchElementException();
    }
}

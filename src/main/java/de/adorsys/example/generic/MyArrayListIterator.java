package de.adorsys.example.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

class MyArrayListIterator<E> implements Iterator<E> {

    private final MyArrayList<E> list;
    private int position = 0;

    MyArrayListIterator(MyArrayList<E> list) {
        this.list = list;
    }

    @Override
    public boolean hasNext() {
        return hasNextElement();
    }

    @Override
    public E next() {
        if (hasNextElement()) {
            E element = list.get(position);
            position++;
            return element;
        }

        throw new NoSuchElementException();
    }

    private boolean hasNextElement() {
        return position < list.size();
    }
}

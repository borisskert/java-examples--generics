package de.adorsys.example.generic;

import java.util.*;

public class MyArrayList<T> implements List<T> {

    private T[] array = (T[]) new Object[1];
    private int size = 0;

    public T get(int index) {
        return array[index];
    }

    public T set(int index, T s) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        return setElementOnIndex(index, s);
    }

    public void add(int index, T element) {

    }

    public T remove(int index) {
        return null;
    }

    public int indexOf(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], o)) {
                return i;
            }
        }
        return -1;
    }

    public int lastIndexOf(Object o) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(array[i], o)) {
                return i;
            }
        }
        return -1;
    }

    public ListIterator<T> listIterator() {
        return null;
    }

    public ListIterator<T> listIterator(int index) {
        return null;
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return null;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(array[i], o)) {
                return true;
            }
        }
        return false;
    }

    public Iterator<T> iterator() {
        return null;
    }

    public Object[] toArray() {
        return new Object[0];
    }

    public <T1> T1[] toArray(T1[] a) {
        return null;
    }

    public boolean add(T s) {
        setElementOnIndex(size, s);
        size++;

        return true;
    }

    public boolean remove(Object o) {
        for (int i = 0; i < size; i++) {
            if (!contains(o)) {
                return false;
            }
        }
        size--;
        return true;
    }

    public boolean containsAll(Collection<?> otherCollection) {
        for (Object currentElementOfOtherCollection : otherCollection) {
            if (!contains(currentElementOfOtherCollection)) {
                return false;
            }
        }

        return true;
    }

    public boolean addAll(int index, Collection<? extends T> otherCollection) {
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }
        int sizeBefore = size;

        size += otherCollection.size();
        extendArrayIfNeeded(size - 1);
        int currentIndex = sizeBefore - 1;

        for(; currentIndex >= index; currentIndex--) {
            array[currentIndex + otherCollection.size()] = array[currentIndex];
        }

        currentIndex = index;
        for(T currentElementOfOtherCollection : otherCollection) {
            setElementOnIndex(currentIndex, currentElementOfOtherCollection);
            currentIndex++;
        }

        return true;
    }

    public boolean addAll(Collection<? extends T> otherCollection) {
        for (T currentElementOfOtherCollection : otherCollection) {
            add(currentElementOfOtherCollection);
        }
        return true;
    }

    public boolean removeAll(Collection<?> otherCollection) {
        for (Object currentElementOfOtherCollection : otherCollection) {
            if (contains(currentElementOfOtherCollection)) {
                remove(otherCollection);
                return true;
            }
        }
        return false;
    }

    public boolean retainAll(Collection<?> otherCollection) {
        return false;
    }

    public void clear() {

    }

    private T setElementOnIndex(int index, T s) {
        extendArrayIfNeeded(index);

        T previous = array[index];
        array[index] = s;

        return previous;
    }

    private void extendArrayIfNeeded(int index) {
        if (index > array.length - 1) {
            T[] copiedArray = (T[]) new Object[array.length * 8];

            System.arraycopy(array, 0, copiedArray, 0, array.length);

            array = copiedArray;
        }
    }
}

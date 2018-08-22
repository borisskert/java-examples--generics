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
        if(index < 0 || index > size){
            throw new IndexOutOfBoundsException();
        }

        extendArrayIfNeeded(size);

        T previous = setElementOnIndex(index, element);
        index++;
        size++;

        for (; index < size; index++) {
           previous =  setElementOnIndex(index, previous);
        }
    }

    public T remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }

        T previous = array[index];
        removeOnIndex(index);

        return previous;
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
        if(fromIndex < 0 || toIndex > size || fromIndex > toIndex){
            throw new IndexOutOfBoundsException();
        }

        MyArrayList<T> myArrayList = new MyArrayList<>();
        for(; fromIndex < toIndex; fromIndex++){
           myArrayList.add(array[fromIndex]);
        }

        return myArrayList;
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

        Object[] newArray = new Object[size];
        System.arraycopy(array, 0, newArray, 0, size);

        return newArray;
    }

    public <T1> T1[] toArray(T1[] a) {
        // TODO
        return null;
    }

    public boolean add(T s) {
        setElementOnIndex(size, s);
        size++;

        return true;
    }

    public boolean remove(Object o) {
        int index = indexOf(o);

        if (index < 0) {
            return false;
        }

        removeOnIndex(index);
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException();
        }
        int sizeBefore = size;

        size += otherCollection.size();
        extendArrayIfNeeded(size - 1);
        int currentIndex = sizeBefore - 1;

        for (; currentIndex >= index; currentIndex--) {
            array[currentIndex + otherCollection.size()] = array[currentIndex];
        }

        currentIndex = index;
        for (T currentElementOfOtherCollection : otherCollection) {
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
        boolean changed = false;
        int index = 0;

        while(index < size){
            if(otherCollection.contains(array[index])){
                index++;
            } else {
                removeOnIndex(index);
                changed = true;
            }
        }
        return changed;
    }

    public void clear() {
        size = 0;
        array = (T[]) new Object[1];
    }

    private T setElementOnIndex(int index, T s) {
        extendArrayIfNeeded(index);

        T previous = array[index];
        array[index] = s;

        return previous;
    }

    private void extendArrayIfNeeded(int index) {
        int newSize = calculateNewSize(index);

        if (newSize > array.length) {
            extendArray(newSize);
        }
    }

    private void extendArray(int newSize) {
        T[] copiedArray = (T[]) new Object[newSize];

        System.arraycopy(array, 0, copiedArray, 0, array.length);

        array = copiedArray;
    }

    private int calculateNewSize(int index) {
        int newSize = array.length;

        while (index >= newSize) {
            newSize = newSize * 8;
        }

        return newSize;
    }

    private void removeOnIndex(int index) {
        size--;

        for (; index < size; index++) {
            array[index] = array[index + 1];
        }

        array[index] = null;
    }
}

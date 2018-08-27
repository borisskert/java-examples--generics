package de.adorsys.example.generic;

import java.util.*;

public class Dictionary<K, V> implements Map<K, V> {

    private final List<DictionaryEntry<K, V>> entries = new MyArrayList<>();


    public V get(int index) {
        DictionaryEntry<K, V> entry = entries.get(index);
        return entry.value;
    }

    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    @Override
    public V get(Object key) {
        Iterator<DictionaryEntry<K, V>> carIterator = entries.iterator();

        while(carIterator.hasNext()){
            DictionaryEntry<K, V> foundEntry = carIterator.next();
            K identifier = foundEntry.key;

            if(identifier.equals(key)){
                return foundEntry.value;
            }
        }

        return null;
    }

    @Override
    public V put(K key, V value) {
        DictionaryEntry<K, V> entry = new DictionaryEntry<>();
        entry.key = key;
        entry.value = value;

        entries.add(entry);

        return null;
    }

    @Override
    public V remove(Object key) {
        Iterator<DictionaryEntry<K, V>> carIterator = entries.iterator();
        while(carIterator.hasNext()){
            DictionaryEntry<K, V> foundEntry = carIterator.next();
            K identifier = foundEntry.key;
            if(identifier.equals(key)){
                entries.remove(foundEntry);
            }
        }

        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}

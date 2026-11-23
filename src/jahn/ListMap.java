/*
 * Course: CSC 1020
 * Lab 13
 * ListMap
 * Name: Owen Jahn
 * Created: 12/9
 */
package jahn;

import java.util.*;

public class ListMap<K, V> implements Map<K, V> {

    private final List<Entry<K, V>> entries;
    public ListMap() {
        this.entries = new ArrayList<>();
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public boolean isEmpty() {
        return entries.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        for(Entry<K, V> e : entries) {
            if(e.getKey() == key) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) throws UnsupportedOperationException{
        return false;
    }

    @Override
    public V get(Object key) {
        for(Entry<K, V> e : entries) {
            if(e.getKey() == key) {
                return e.getValue();
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        for(Entry<K, V> e : entries) {
            if(e.getKey() == key) {
                V oldvalue = e.getValue();
                e.setValue(value);
                return oldvalue;
            }
        }
        entries.add(new AbstractMap.SimpleEntry<>(key, value));
        return null;
    }

    @Override
    public V remove(Object key) {
        for(Entry<K, V> e : entries) {
            if(e.getKey() == key) {
                V removedObject = e.getValue();
                entries.remove(key);
                return removedObject;
            }
        }
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) throws UnsupportedOperationException {
    }

    @Override
    public void clear() {
        entries.clear();
    }

    @Override
    public Set<K> keySet() throws UnsupportedOperationException {
        return Set.of();
    }

    @Override
    public Collection<V> values() throws UnsupportedOperationException{
        return List.of();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new LinkedHashSet<>(entries);
    }
}

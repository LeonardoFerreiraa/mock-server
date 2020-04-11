package br.com.leonardoferreira.mockserver.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class DelegateMap<K, V> implements Map<K, V> {

    private final Map<K, V> delegate;

    protected DelegateMap(final Map<K, V> delegate) {
        this.delegate = delegate == null ? new HashMap<>() : delegate;
    }

    protected abstract K transformKey(final Object key);

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public boolean containsKey(final Object key) {
        return delegate.containsKey(transformKey(key));
    }

    @Override
    public boolean containsValue(final Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public V get(final Object key) {
        return delegate.get(transformKey(key));
    }

    @Override
    public V put(final K key, final V value) {
        return delegate.put(transformKey(key), value);
    }

    @Override
    public V remove(final Object key) {
        return delegate.remove(transformKey(key));
    }

    @Override
    public void putAll(final Map<? extends K, ? extends V> m) {
        delegate.putAll(m);
    }

    @Override
    public Set<K> keySet() {
        return delegate.keySet();
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public Collection<V> values() {
        return delegate.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return delegate.entrySet();
    }

}
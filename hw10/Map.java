package hw214_9;

public interface Map<K, V> {
    int size();
    boolean isEmpty();
    V get(K key);
    V put(K key, V value);
    V remove(K key);
    Iterable<K> keys();
    Iterable<V> values();
    Iterable<Entry<K,V>> entries();
}

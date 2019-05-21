package mobi.mixiong.cache;

import java.util.List;

/**
 * -------------------------------------
 * -------------------------------------
 */
public interface RemovableListCache<K, V> {
    public void mutRemove(List<V> values);
    public void mutRemove(K key, List<V> values);
    public void remove(V value);
    public void remove(K key, V value);
}

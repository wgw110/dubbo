package mobi.mixiong.cache;

import com.google.common.collect.Lists;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class BaseListCache<K, V> extends BaseCache<K, V> {

    public void add(K key, V value) {
        mutAdd(key, Lists.newArrayList(value));
    }

    public void add(V value) {
        add(null, value);
    }

    public void addIfExist(K key, V value) {
        String cacheKey = buildKey(key);
        RedisTemplate<String, V> redisTemplate = getRedisTemplate();
        if (CacheUtil.hasKey(redisTemplate, cacheKey)) {
            add(key, value);
        }
    }

    public void addIfExist(V value) {
        addIfExist(null, value);
    }


    public Map<K, List<V>> batchList(List<K> keys) {
        List<Object> objects = getRedisTemplate().executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                StringRedisSerializer keySerializer = (StringRedisSerializer) getRedisTemplate().getKeySerializer();
                for (K key : keys) {
                    connection.lRange(keySerializer.serialize(buildKey(key)), 0, Integer.MAX_VALUE);
                }
                return null;
            }
        });
        Map<K, List<V>> result = new HashMap<>();
        for (int i = 0; i < objects.size(); i++) {
            List<V> set = (ArrayList<V>) objects.get(i);
            if (set.isEmpty()) {
                if (exist(keys.get(i))) {
                    result.put(keys.get(i), Lists.newArrayList());
                }
            } else {
                result.put(keys.get(i), set.stream().collect(Collectors.toList()));
            }
        }
        return result;
    }

    public void mutAdd(K key, List<V> values) {
        String cacheKey = buildKey(key);
        Long ttl = getTTL();
        if (ttl != null) {
            CacheUtil.list().mutAdd(getRedisTemplate(), cacheKey, values, ttl);
        } else {
            CacheUtil.list().mutAdd(getRedisTemplate(), cacheKey, values);
        }
    }

    public void mutAdd(List<V> values) {
        mutAdd(null, values);
    }

    public void mutAddIfExist(K key, List<V> values) {
        String cacheKey = buildKey(key);
        RedisTemplate<String, V> redisTemplate = getRedisTemplate();
        if (CacheUtil.hasKey(redisTemplate, cacheKey)) {
            mutAdd(key, values);
        }
    }

    public void mutAddIfExist(List<V> values) {
        mutAddIfExist(null, values);
    }

    public List<V> list(K key, int start, int size) {
        String cacheKey = buildKey(key);
        return CacheUtil.list().list(getRedisTemplate(), cacheKey, start, size);
    }

    public void push(K key, V value) {
        String cacheKey = buildKey(key);
        CacheUtil.list().leftPush(getRedisTemplate(), cacheKey, value);
    }

    public V pop(K key){
        String cacheKey = buildKey(key);
        return CacheUtil.list().rightPop(getRedisTemplate(), cacheKey);
    }
    public List<V> list(int start, int size) {
        return list(null, start, size);
    }

    public List<V> listAll(K key) {
        String cacheKey = buildKey(key);
        return CacheUtil.list().listAll(getRedisTemplate(), cacheKey);
    }

    public List<V> listAll() {
        return listAll(null);
    }
}

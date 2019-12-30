package javase.socket;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : peng
 * @Description : 自定义的 HashMap
 * @Date : 2019-12-22
 */
@Slf4j
public class MyHashMap<K, V> {

    /**
     * 线程安全的类
     */
    private Map<K, V> hashMap = Collections.synchronizedMap(new HashMap<K, V>());

    /**
     * 存入一个 Key Value ,且Value不重复
     *
     * @param key   key
     * @param value value
     */
    public synchronized void put(K key, V value) {
        if (value == null) {
            return;
        }
        for (Map.Entry<K, V> entry : hashMap.entrySet()) {
            if (value.equals(entry.getValue())) {
                log.error("已有重复Value");
                return;
            }
        }
        hashMap.put(key, value);
    }

    /**
     * 根据 Value 获取 Key
     *
     * @param value Value
     * @return 返回 Key
     */
    public K getKey(V value) {
        for (Map.Entry<K, V> entry : hashMap.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    /**
     * 获取用户 V
     *
     * @param k key
     * @return Value
     */
    public V getValue(K k) {
        return hashMap.get(k);
    }

    /**
     * 获取 hashMap
     */
    public Map<K, V> getHashMap(){
        return hashMap;
    }

    /**
     * 去除其中的聊天室
     */
    public V remove(K k){
        return hashMap.remove(k);
    }


}

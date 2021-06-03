package com.mx.dev.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @create: 2021-01-22 16:36
 * @Description:
 */
public class MapUtils<K,V> {

    private final Map<K, V> MAP = new HashMap<>();

//    private final static MapUtils ME = new MapUtils();


    public Map<K, V> emptyMap(){
        return MAP;
    }
//
//    public static MapUtils<k,v> builder() {
//        return ME;
//    }

    public MapUtils<K,V> put(K key, V value) {
        MAP.put(key, value);
        return this;
    }

    public void remove(K key) {
        MAP.remove(key);
    }

    public Map<K, V> toMap() {
        return MAP;
    }

    public void foreach(Foreach<K,V> foreach) {
        for (Map.Entry<K, V> entry : MAP.entrySet()) {
            WAY way = foreach.loop(entry.getKey(), entry.getValue());
            switch (way) {
                case BREAK:
                    break;
                case CONTINUE:
                    continue;
                default:
            }
        }
    }

    public void foreachKey(ForeachKey<K,V> foreachKey) {
        for (K key : MAP.keySet()) {
            WAY way = foreachKey.loop(key);
            switch (way) {
                case BREAK:
                    break;
                case CONTINUE:
                    continue;
                default:
            }
        }
    }

    public void foreachValue(ForeachValue<K,V> foreachValue) {
        for (V value : MAP.values()) {
            WAY way = foreachValue.loop(value);
            switch (way) {
                case BREAK:
                    break;
                case CONTINUE:
                    continue;
                default:
            }
        }
    }


    public enum WAY {
        /**
         * 终止方式
         */
        BREAK,
        CONTINUE;
    }

    public interface Foreach<K,V> {

        WAY loop(K key, V value);
    }

    public interface ForeachKey<K,V> {

        WAY loop(K key);
    }

    public interface ForeachValue<K,V> {

        WAY loop(V value);
    }
}

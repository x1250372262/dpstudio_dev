package com.dpstudio.dev.utils;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @create: 2021-01-22 16:36
 * @Description:
 */
public class MapUtils {

    private final static Map<String, Object> MAP = new HashMap<>();

    private final static MapUtils ME = new MapUtils();


    public static MapUtils builder() {
        return ME;
    }

    public MapUtils put(String key, Object value) {
        MAP.put(key, value);
        return ME;
    }

    public static void remove(String key) {
        MAP.remove(key);
    }

    public Map<String, Object> build() {
        return MAP;
    }

    public static void foreach(Foreach foreach) {
        for (Map.Entry<String, Object> entry : MAP.entrySet()) {
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

    public static void foreachKey(ForeachKey foreachKey) {
        for (String key : MAP.keySet()) {
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

    public static void foreachValue(ForeachValue foreachValue) {
        for (Object value : MAP.values()) {
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

    public interface Foreach {

        WAY loop(String key, Object value);
    }

    public interface ForeachKey {

        WAY loop(String key);
    }

    public interface ForeachValue {

        WAY loop(Object value);
    }
}

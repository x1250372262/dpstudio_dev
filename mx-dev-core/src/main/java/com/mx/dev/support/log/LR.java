package com.mx.dev.support.log;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: mengxiang.
 * @Date: 2020/10/15.
 * @Time: 10:52 上午.
 * @Description:
 */
public class LR {

    private LR(){

    }

    public static LR create(){
        return new LR();
    }

    private final Map<String, Item> attrs = new HashMap<>();

    public LR set(String key, Item item) {
        if (StringUtils.isBlank(key)) {
            key = "default";
        }
        this.attrs.put(key, item);
        return this;
    }

    public Item get(String key) {
        if (StringUtils.isBlank(key)) {
            key = "default";
        }
        return this.attrs.get(key);
    }

    public Map<String, Item> attrs() {
        return this.attrs;
    }


    public static class Item {
        private final Map<String, Object> attrs = new HashMap<>();

       private Item(){

       }

        public static Item create(){
            return new Item();
        }


        public Item set(String key, Object value) {
            this.attrs.put(key, value);
            return this;
        }

        public Object get(String key) {
            return this.attrs.get(key);
        }

        public Map<String, Object> attrs() {
            return this.attrs;
        }
    }

    public static class Cache {

        private static final Map<String,String> attrs = new HashMap<>();

        public static Object detail(String key,Object obj){
            attrs.put(key, JSON.toJSONString(obj));
            return obj;
        }

        public static String get(String key){
            return attrs.get(key);
        }
    }
}

package com.dpstudio.dev.jdbc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 徐建鹏.
 * @Date: 2020/4/15.
 * @Time: 6:04 下午.
 * @Description:
 */
public class Params {

    public Map<String, Object> params = new HashMap<>();

    private Params() {

    }

    public static Params create() {
        return new Params();
    }

    public Map<String, Object> params() {
        return this.params;
    }

    public Object attr(String key) {
        return this.params.get(key);
    }

    public Params attr(String key, String val) {
        this.params.put(key, val);
        return this;
    }


}

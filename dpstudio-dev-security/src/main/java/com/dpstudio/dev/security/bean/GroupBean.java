package com.dpstudio.dev.security.bean;

/**
 * @Author: mengxiang.
 * @Date: 2019-01-16.
 * @Time: 11:48.
 * @Description:
 */
public class GroupBean {

    private String name;

    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public GroupBean(String name, String id) {
        this.name = name;
        this.id = id;
    }
}

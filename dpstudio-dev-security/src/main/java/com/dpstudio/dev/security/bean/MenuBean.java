package com.dpstudio.dev.security.bean;


/**
 * @Author: xujianpeng.
 * @Date: 2020/6/17.
 * @Time: 5:46 下午.
 * @Description:
 */
public class MenuBean {

    private String id;
    private String name;
    private String value;
    private String icon;
    private String url;
    private String pid;
    private String permissions;

    public static MenuBean builder() {
        return new MenuBean();
    }

    public MenuBean id(String id) {
        this.id = id;
        return this;
    }

    public String id() {
        return this.id;
    }

    public MenuBean name(String name) {
        this.name = name;
        return this;
    }

    public String name() {
        return this.name;
    }

    public MenuBean value(String value) {
        this.value = value;
        return this;
    }

    public String value() {
        return this.value;
    }


    public MenuBean icon(String icon) {
        this.icon = icon;
        return this;
    }

    public String icon() {
        return this.icon;
    }

    public MenuBean url(String url) {
        this.url = url;
        return this;
    }

    public String url() {
        return this.url;
    }

    public MenuBean pid(String pid) {
        this.pid = pid;
        return this;
    }

    public String pid() {
        return this.pid;
    }

    public MenuBean permissions(String permissions) {
        this.permissions = permissions;
        return this;
    }

    public String permissions() {
        return this.permissions;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
}

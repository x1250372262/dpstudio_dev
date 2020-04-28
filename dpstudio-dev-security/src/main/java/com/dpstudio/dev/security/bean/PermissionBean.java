package com.dpstudio.dev.security.bean;

/**
 * @Author: 徐建鹏.
 * @Date: 2019-01-16.
 * @Time: 11:48.
 * @Description:
 */
public class PermissionBean {

    private String name;

    private String code;

    private String groupId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public PermissionBean(String name, String code, String groupId) {
        this.name = name;
        this.code = code;
        this.groupId = groupId;
    }
}

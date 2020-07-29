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

    private String groupName;

    private String level;

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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public PermissionBean(String name, String code, String groupId, String groupName,String level) {
        this.name = name;
        this.code = code;
        this.groupId = groupId;
        this.groupName = groupName;
        this.level = level;
    }

}

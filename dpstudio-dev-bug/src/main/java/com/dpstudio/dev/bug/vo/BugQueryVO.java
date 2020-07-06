package com.dpstudio.dev.bug.vo;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午3:40.
 * @Description:
 */
public class BugQueryVO {

    private String id;
    private String title;
    private String type;
    private String content;
    private String handlerUser;
    private String handlerTime;
    private String createTime;
    private String lastModifyUser;
    private String lastModifyTime;
    private String status;
    private String level;
    private String createUser;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getHandlerUser() {
        return handlerUser;
    }

    public void setHandlerUser(String handlerUser) {
        this.handlerUser = handlerUser;
    }

    public String getHandlerTime() {
        return handlerTime;
    }

    public void setHandlerTime(String handlerTime) {
        this.handlerTime = handlerTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }
}

package com.dpstudio.dev.bug.vo;

import net.ymate.platform.validation.validate.VRequired;
import net.ymate.platform.webmvc.annotation.RequestParam;

/**
 * @Author: xujianpeng.
 * @Date: 2020/7/6.
 * @Time: 下午3:12.
 * @Description:
 */
public class BugOpVO {

    @VRequired(msg = "标题不能为空")
    @RequestParam
    private String title;
    @VRequired(msg = "请选择类型")
    @RequestParam
    private Integer type;
    @VRequired(msg = "请选择优先级")
    @RequestParam
    private Integer level;
    @VRequired(msg = "内容不能为空")
    @RequestParam
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

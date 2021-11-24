package com.sy.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class SignBackUp {
    private Integer id;
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date createtime;
    private Integer flag;
    private Integer uid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
}

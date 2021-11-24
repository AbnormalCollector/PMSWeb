package com.sy.entity;

import com.alibaba.fastjson.annotation.JSONField;
import java.util.Date;

public class Sign {
    private Integer id;
    @JSONField(format = "yyyy-MM-dd")
    private Date createtime;
    @JSONField(format = "HH:mm:ss")
    private Date start;
    @JSONField(format = "HH:mm:ss")
    private Date end;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

}

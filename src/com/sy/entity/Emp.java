package com.sy.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class Emp {
    private Integer id;
    private String name;
    private String cardid;
    private String address;
    private String postCose;
    private String tel;
    private String qqNum;
    private String email;
    private Integer sex;
    private String party;
    @JSONField(format = "yyyy-MM-dd hh:mm:ss")
    private Date birthday;
    private String rece;
    private String education;
    private String speciality;
    private String hobby;
    private String remark;
    @JSONField(format = "yyyy-MM-dd")
    private Date createDate;
    private Integer uid;
    private Integer did;
    private Integer jid;

    public Emp() {
    }

    public Emp(String name, String cardid, String address,
               String postCose, String tel, String qqNum, String email,
               Integer sex, String party, Date birthday, String rece,
               String education, String speciality, String hobby, String
                       remark) {
        this.name = name;
        this.cardid = cardid;
        this.address = address;
        this.postCose = postCose;
        this.tel = tel;
        this.qqNum = qqNum;
        this.email = email;
        this.sex = sex;
        this.party = party;
        this.birthday = birthday;
        this.rece = rece;
        this.education = education;
        this.speciality = speciality;
        this.hobby = hobby;
        this.remark = remark;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardid() {
        return cardid;
    }

    public void setCardid(String cardid) {
        this.cardid = cardid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCose() {
        return postCose;
    }

    public void setPostCose(String postCose) {
        this.postCose = postCose;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getQqNum() {
        return qqNum;
    }

    public void setQqNum(String qqNum) {
        this.qqNum = qqNum;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getParty() {
        return party;
    }

    public void setParty(String party) {
        this.party = party;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getRece() {
        return rece;
    }

    public void setRece(String rece) {
        this.rece = rece;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public Integer getJid() {
        return jid;
    }

    public void setJid(Integer jid) {
        this.jid = jid;
    }
}

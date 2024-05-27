package com.wiocrm.model;

import java.util.Date;

public class User {
    private String id;
    private String password;
    private String gubn;
    private Date insertTime;
    private String confirmYn;
    private Date confirmTime;
    private String confirmEmp;
    private String userId;
    private String username;
    private String repass;

    // getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGubn() {
        return gubn;
    }

    public void setGubn(String gubn) {
        this.gubn = gubn;
    }

    public Date getInsertTime() {
        return insertTime;
    }

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }

    public String getConfirmYn() {
        return confirmYn;
    }

    public void setConfirmYn(String confirmYn) {
        this.confirmYn = confirmYn;
    }

    public Date getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(Date confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmEmp() {
        return confirmEmp;
    }

    public void setConfirmEmp(String confirmEmp) {
        this.confirmEmp = confirmEmp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRepass() {
        return repass;
    }

    public void setRepass(String repass) {
        this.repass = repass;
    }
}

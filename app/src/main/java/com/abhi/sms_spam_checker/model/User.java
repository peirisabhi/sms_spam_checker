package com.abhi.sms_spam_checker.model;

import java.util.Date;

public class User {
    String fullName;
    String email;
    String mobile;
    boolean status;
    Date registeredAt;
    String userDocumentId;

    public User() {
    }

    public User(String fullName, String email, String mobile, boolean status, Date registeredAt, String userDocumentId) {
        this.fullName = fullName;
        this.email = email;
        this.mobile = mobile;
        this.status = status;
        this.registeredAt = registeredAt;
        this.userDocumentId = userDocumentId;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getRegisteredAt() {
        return registeredAt;
    }

    public void setRegisteredAt(Date registeredAt) {
        this.registeredAt = registeredAt;
    }

    public String getUserDocumentId() {
        return userDocumentId;
    }

    public void setUserDocumentId(String userDocumentId) {
        this.userDocumentId = userDocumentId;
    }
}

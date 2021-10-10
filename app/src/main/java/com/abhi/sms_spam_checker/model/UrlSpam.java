package com.abhi.sms_spam_checker.model;

import java.util.Date;

public class UrlSpam {
    Date fondedAt;
    String url;
    String senderNumber;
    String senderName;
    int maliciousCount;
    String title;
    String requestId;

    public UrlSpam() {
    }

    public UrlSpam(Date fondedAt, String url, String senderNumber, String senderName, int maliciousCount, String title, String requestId) {
        this.fondedAt = fondedAt;
        this.url = url;
        this.senderNumber = senderNumber;
        this.senderName = senderName;
        this.maliciousCount = maliciousCount;
        this.title = title;
        this.requestId = requestId;
    }


    public Date getFondedAt() {
        return fondedAt;
    }

    public void setFondedAt(Date fondedAt) {
        this.fondedAt = fondedAt;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSenderNumber() {
        return senderNumber;
    }

    public void setSenderNumber(String senderNumber) {
        this.senderNumber = senderNumber;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public int getMaliciousCount() {
        return maliciousCount;
    }

    public void setMaliciousCount(int maliciousCount) {
        this.maliciousCount = maliciousCount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}

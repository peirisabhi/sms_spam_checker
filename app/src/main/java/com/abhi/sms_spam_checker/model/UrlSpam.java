package com.abhi.sms_spam_checker.model;

import java.io.Serializable;
import java.util.Date;

public class UrlSpam implements Serializable {
    Date fondedAt;
    String url;
    String senderNumber;
    String senderName;
    int maliciousCount;
    int harmlessCount;
    int suspiciousCount;
    int undetectedCount;
    int timeoutCount;
    String title;
    String requestId;
    String fullMessage;

    boolean mobileValidity;
    String international;
    String mobileCountry;
    String mobileCountryCode;
    String mobileCountryPrefix;
    String mobileLocation;
    String mobileType;
    String mobileCarrier;

    String email;
    boolean emailDeliverability;
    String emailQuality;
    boolean emailIsValidFormat;
    boolean emailIsFreeEmail;
    boolean emailIdDisposable;
    boolean emailIsRole;
    boolean emailIsCatchall;
    boolean emailIsMxFound;
    boolean emailIsSmtpValid;





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

    public int getHarmlessCount() {
        return harmlessCount;
    }

    public void setHarmlessCount(int harmlessCount) {
        this.harmlessCount = harmlessCount;
    }

    public int getSuspiciousCount() {
        return suspiciousCount;
    }

    public void setSuspiciousCount(int suspiciousCount) {
        this.suspiciousCount = suspiciousCount;
    }

    public int getUndetectedCount() {
        return undetectedCount;
    }

    public void setUndetectedCount(int undetectedCount) {
        this.undetectedCount = undetectedCount;
    }

    public int getTimeoutCount() {
        return timeoutCount;
    }

    public void setTimeoutCount(int timeoutCount) {
        this.timeoutCount = timeoutCount;
    }

    public String getFullMessage() {
        return fullMessage;
    }

    public void setFullMessage(String fullMessage) {
        this.fullMessage = fullMessage;
    }

    public boolean isMobileValidity() {
        return mobileValidity;
    }

    public void setMobileValidity(boolean mobileValidity) {
        this.mobileValidity = mobileValidity;
    }

    public String getInternational() {
        return international;
    }

    public void setInternational(String international) {
        this.international = international;
    }

    public String getMobileCountry() {
        return mobileCountry;
    }

    public void setMobileCountry(String mobileCountry) {
        this.mobileCountry = mobileCountry;
    }

    public String getMobileCountryCode() {
        return mobileCountryCode;
    }

    public void setMobileCountryCode(String mobileCountryCode) {
        this.mobileCountryCode = mobileCountryCode;
    }

    public String getMobileCountryPrefix() {
        return mobileCountryPrefix;
    }

    public void setMobileCountryPrefix(String mobileCountryPrefix) {
        this.mobileCountryPrefix = mobileCountryPrefix;
    }

    public String getMobileLocation() {
        return mobileLocation;
    }

    public void setMobileLocation(String mobileLocation) {
        this.mobileLocation = mobileLocation;
    }

    public String getMobileType() {
        return mobileType;
    }

    public void setMobileType(String mobileType) {
        this.mobileType = mobileType;
    }

    public String getMobileCarrier() {
        return mobileCarrier;
    }

    public void setMobileCarrier(String mobileCarrier) {
        this.mobileCarrier = mobileCarrier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEmailDeliverability() {
        return emailDeliverability;
    }

    public void setEmailDeliverability(boolean emailDeliverability) {
        this.emailDeliverability = emailDeliverability;
    }

    public String getEmailQuality() {
        return emailQuality;
    }

    public void setEmailQuality(String emailQuality) {
        this.emailQuality = emailQuality;
    }

    public boolean isEmailIsValidFormat() {
        return emailIsValidFormat;
    }

    public void setEmailIsValidFormat(boolean emailIsValidFormat) {
        this.emailIsValidFormat = emailIsValidFormat;
    }

    public boolean isEmailIsFreeEmail() {
        return emailIsFreeEmail;
    }

    public void setEmailIsFreeEmail(boolean emailIsFreeEmail) {
        this.emailIsFreeEmail = emailIsFreeEmail;
    }

    public boolean isEmailIdDisposable() {
        return emailIdDisposable;
    }

    public void setEmailIdDisposable(boolean emailIdDisposable) {
        this.emailIdDisposable = emailIdDisposable;
    }

    public boolean isEmailIsRole() {
        return emailIsRole;
    }

    public void setEmailIsRole(boolean emailIsRole) {
        this.emailIsRole = emailIsRole;
    }

    public boolean isEmailIsCatchall() {
        return emailIsCatchall;
    }

    public void setEmailIsCatchall(boolean emailIsCatchall) {
        this.emailIsCatchall = emailIsCatchall;
    }

    public boolean isEmailIsMxFound() {
        return emailIsMxFound;
    }

    public void setEmailIsMxFound(boolean emailIsMxFound) {
        this.emailIsMxFound = emailIsMxFound;
    }

    public boolean isEmailIsSmtpValid() {
        return emailIsSmtpValid;
    }

    public void setEmailIsSmtpValid(boolean emailIsSmtpValid) {
        this.emailIsSmtpValid = emailIsSmtpValid;
    }
}

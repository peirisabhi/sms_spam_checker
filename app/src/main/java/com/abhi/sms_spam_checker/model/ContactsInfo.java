package com.abhi.sms_spam_checker.model;

public class ContactsInfo {
    private String contactId;
    private String displayName;
    private String phoneNumber;

    public ContactsInfo() {
    }

    public ContactsInfo(String contactId, String displayName, String phoneNumber) {
        this.contactId = contactId;
        this.displayName = displayName;
        this.phoneNumber = phoneNumber;
    }

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "ContactsInfo{" +
                "contactId='" + contactId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}

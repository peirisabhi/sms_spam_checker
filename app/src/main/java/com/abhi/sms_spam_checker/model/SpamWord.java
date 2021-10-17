package com.abhi.sms_spam_checker.model;

public class SpamWord {
    int id;
    String word;
    boolean status;

    public SpamWord() {
    }

    public SpamWord(int id, String word, boolean status) {
        this.id = id;
        this.word = word;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}

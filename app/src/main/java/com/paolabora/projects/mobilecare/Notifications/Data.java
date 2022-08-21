package com.paolabora.projects.mobilecare.Notifications;

public class Data {
    private String sent, title;

    public Data() {
    }

    public Data(String sent, String title) {
        this.sent = sent;
        this.title = title;
    }

    public String getSent() {
        return sent;
    }

    public void setSent(String sent) {
        this.sent = sent;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

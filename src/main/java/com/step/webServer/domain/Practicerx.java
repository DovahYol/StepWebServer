package com.step.webServer.domain;

public class Practicerx implements Domain {
    private Integer practicerxId;
    private Integer followupId;
    private String note;

    public Integer getPracticerxId() {
        return practicerxId;
    }

    public void setPracticerxId(Integer practicerxId) {
        this.practicerxId = practicerxId;
    }

    public Integer getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Integer followupId) {
        this.followupId = followupId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

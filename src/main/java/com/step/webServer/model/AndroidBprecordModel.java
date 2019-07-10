package com.step.webServer.model;

public class AndroidBprecordModel {
    private String measState;
    private String symptom;
    private String createDatetime;
    private String addPrcId;
    private String uploadType;
    private String dbp;
    private String sbp;
    private String pulseRate;

    public String getMeasState() {
        return measState;
    }

    public void setMeasState(String measState) {
        this.measState = measState;
    }

    public String getSymptom() {
        return symptom;
    }

    public void setSymptom(String symptom) {
        this.symptom = symptom;
    }

    public String getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(String createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getAddPrcId() {
        return addPrcId;
    }

    public void setAddPrcId(String addPrcId) {
        this.addPrcId = addPrcId;
    }

    public String getUploadType() {
        return uploadType;
    }

    public void setUploadType(String uploadType) {
        this.uploadType = uploadType;
    }

    public String getDbp() {
        return dbp;
    }

    public void setDbp(String dbp) {
        this.dbp = dbp;
    }

    public String getSbp() {
        return sbp;
    }

    public void setSbp(String sbp) {
        this.sbp = sbp;
    }

    public String getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(String pulseRate) {
        this.pulseRate = pulseRate;
    }
}

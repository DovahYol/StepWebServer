package com.step.webServer.domain;

import java.time.LocalDateTime;

public class Bprecord  implements Domain{
    private Integer bprecordId;

    private Double sbp;

    private Double dbp;

    private Double map;

    private Double pulseRate;

    private String measState;

    private String symptom;

    private LocalDateTime createDatetime;

    private Integer patientId;

    public Integer getBprecordId() {
        return bprecordId;
    }

    public void setBprecordId(Integer bprecordId) {
        this.bprecordId = bprecordId;
    }

    public Double getSbp() {
        return sbp;
    }

    public void setSbp(Double sbp) {
        this.sbp = sbp;
    }

    public Double getDbp() {
        return dbp;
    }

    public void setDbp(Double dbp) {
        this.dbp = dbp;
    }

    public Double getMap() {
        return map;
    }

    public void setMap(Double map) {
        this.map = map;
    }

    public Double getPulseRate() {
        return pulseRate;
    }

    public void setPulseRate(Double pulseRate) {
        this.pulseRate = pulseRate;
    }

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

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}

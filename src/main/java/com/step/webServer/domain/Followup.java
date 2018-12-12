package com.step.webServer.domain;

import java.time.LocalDateTime;

public class Followup  implements Domain{
    private Integer followupId;
    private LocalDateTime createDatetime;
    private String complaint;
    private String historyPresentIllness;
    private String historyPastIllness;
    private String personalHistory;
    private String allergicHistory;
    private String familyHistory;
    private Double height;
    private Double weight;
    private Double sbp;
    private Double dbp;
    private Double meanbp;
    private Double heartRate;
    private Double bodyTemp;
    private Double breathe;
    private Double bloodSugar;
    private String physExam;
    private String labExam;
    private Double sbpTarget;
    private Double dbpTarget;
    private String medicineRx;
    private Integer isReferral;
    private Integer patientId;
    private Integer userId;

    public Integer getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Integer followupId) {
        this.followupId = followupId;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getComplaint() {
        return complaint;
    }

    public void setComplaint(String complaint) {
        this.complaint = complaint;
    }

    public String getHistoryPresentIllness() {
        return historyPresentIllness;
    }

    public void setHistoryPresentIllness(String historyPresentIllness) {
        this.historyPresentIllness = historyPresentIllness;
    }

    public String getHistoryPastIllness() {
        return historyPastIllness;
    }

    public void setHistoryPastIllness(String historyPastIllness) {
        this.historyPastIllness = historyPastIllness;
    }

    public String getPersonalHistory() {
        return personalHistory;
    }

    public void setPersonalHistory(String personalHistory) {
        this.personalHistory = personalHistory;
    }

    public String getAllergicHistory() {
        return allergicHistory;
    }

    public void setAllergicHistory(String allergicHistory) {
        this.allergicHistory = allergicHistory;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
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

    public Double getMeanbp() {
        return meanbp;
    }

    public void setMeanbp(Double meanbp) {
        this.meanbp = meanbp;
    }

    public Double getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Double heartRate) {
        this.heartRate = heartRate;
    }

    public Double getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(Double bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public Double getBreathe() {
        return breathe;
    }

    public void setBreathe(Double breathe) {
        this.breathe = breathe;
    }

    public Double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(Double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public String getPhysExam() {
        return physExam;
    }

    public void setPhysExam(String physExam) {
        this.physExam = physExam;
    }

    public String getLabExam() {
        return labExam;
    }

    public void setLabExam(String labExam) {
        this.labExam = labExam;
    }

    public Double getSbpTarget() {
        return sbpTarget;
    }

    public void setSbpTarget(Double sbpTarget) {
        this.sbpTarget = sbpTarget;
    }

    public Double getDbpTarget() {
        return dbpTarget;
    }

    public void setDbpTarget(Double dbpTarget) {
        this.dbpTarget = dbpTarget;
    }

    public String getMedicineRx() {
        return medicineRx;
    }

    public void setMedicineRx(String medicineRx) {
        this.medicineRx = medicineRx;
    }

    public Integer getIsReferral() {
        return isReferral;
    }

    public void setIsReferral(Integer isReferral) {
        this.isReferral = isReferral;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}

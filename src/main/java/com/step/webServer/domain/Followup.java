package com.step.webServer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Followup  implements Domain{
    private Integer followupId;
    private LocalDate createDate;
    private String complaint;
    private String historyPresentIllness;
    private String historyPastIllness;
    private String personalHistory;
    private String allergicHistory;
    private String familyHistory;
    private String height;
    private String weight;
    private double sbp;
    private double dbp;
    private String meanbp;
    private String heartRate;
    private String bodyTemp;
    private String breathe;
    private String bloodSugar;
    private String physExam;
    private String labExam;
    private String dzDx;
    private double sbpTarget;
    private double dbpTarget;
    private String medicineRx;
    private Boolean isReferral;
    private Integer patientId;
    private Integer userId;
    private LocalDateTime nextDatetime;
    private Boolean nextConfirmed;

    public Integer getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Integer followupId) {
        this.followupId = followupId;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
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

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getMeanbp() {
        return meanbp;
    }

    public void setMeanbp(String meanbp) {
        this.meanbp = meanbp;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(String heartRate) {
        this.heartRate = heartRate;
    }

    public String getBodyTemp() {
        return bodyTemp;
    }

    public void setBodyTemp(String bodyTemp) {
        this.bodyTemp = bodyTemp;
    }

    public String getBreathe() {
        return breathe;
    }

    public void setBreathe(String breathe) {
        this.breathe = breathe;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
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

    public String getMedicineRx() {
        return medicineRx;
    }

    public void setMedicineRx(String medicineRx) {
        this.medicineRx = medicineRx;
    }

    public Boolean getReferral() {
        return isReferral;
    }

    public void setReferral(Boolean referral) {
        isReferral = referral;
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

    public LocalDateTime getNextDatetime() {
        return nextDatetime;
    }

    public void setNextDatetime(LocalDateTime nextDatetime) {
        this.nextDatetime = nextDatetime;
    }

    public Boolean getNextConfirmed() {
        return nextConfirmed;
    }

    public void setNextConfirmed(Boolean nextConfirmed) {
        this.nextConfirmed = nextConfirmed;
    }

    public double getSbp() {
        return sbp;
    }

    public void setSbp(double sbp) {
        this.sbp = sbp;
    }

    public double getDbp() {
        return dbp;
    }

    public void setDbp(double dbp) {
        this.dbp = dbp;
    }

    public String getDzDx() {
        return dzDx;
    }

    public void setDzDx(String dzDx) {
        this.dzDx = dzDx;
    }

    public double getSbpTarget() {
        return sbpTarget;
    }

    public void setSbpTarget(double sbpTarget) {
        this.sbpTarget = sbpTarget;
    }

    public double getDbpTarget() {
        return dbpTarget;
    }

    public void setDbpTarget(double dbpTarget) {
        this.dbpTarget = dbpTarget;
    }
}

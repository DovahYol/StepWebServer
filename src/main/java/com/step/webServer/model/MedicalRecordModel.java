package com.step.webServer.model;

public class MedicalRecordModel {
    private String patientId;
    private String createDate;
    private String complaint;
    private String historyPresentIllness;
    private String historyPastIllness;
    private String personalHistory;
    private String allergicHistory;
    private String familyHistory;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
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
}

package com.step.webServer.domain;

public class Courserecord  implements Domain{
    private Integer courserecordId;
    private Integer selfMonitor;
    private Integer dietGuide;
    private Integer saltLimitAndDrugGuidance;
    private Integer patientId;

    public Integer getCourserecordId() {
        return courserecordId;
    }

    public void setCourserecordId(Integer courserecordId) {
        this.courserecordId = courserecordId;
    }

    public Integer getSelfMonitor() {
        return selfMonitor;
    }

    public void setSelfMonitor(Integer selfMonitor) {
        this.selfMonitor = selfMonitor;
    }

    public Integer getDietGuide() {
        return dietGuide;
    }

    public void setDietGuide(Integer dietGuide) {
        this.dietGuide = dietGuide;
    }

    public Integer getSaltLimitAndDrugGuidance() {
        return saltLimitAndDrugGuidance;
    }

    public void setSaltLimitAndDrugGuidance(Integer saltLimitAndDrugGuidance) {
        this.saltLimitAndDrugGuidance = saltLimitAndDrugGuidance;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}

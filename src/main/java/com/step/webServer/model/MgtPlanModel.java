package com.step.webServer.model;

public class MgtPlanModel {
    private String patientId;
    private String createDate;
    private String sbpTarget;
    private String dbpTarget;
    private String medicineRx;
    private boolean isReferral;
    private String nextDatetime;

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

    public String getSbpTarget() {
        return sbpTarget;
    }

    public void setSbpTarget(String sbpTarget) {
        this.sbpTarget = sbpTarget;
    }

    public String getDbpTarget() {
        return dbpTarget;
    }

    public void setDbpTarget(String dbpTarget) {
        this.dbpTarget = dbpTarget;
    }

    public String getMedicineRx() {
        return medicineRx;
    }

    public void setMedicineRx(String medicineRx) {
        this.medicineRx = medicineRx;
    }

    public boolean isReferral() {
        return isReferral;
    }

    public void setReferral(boolean referral) {
        isReferral = referral;
    }

    public String getNextDatetime() {
        return nextDatetime;
    }

    public void setNextDatetime(String nextDatetime) {
        this.nextDatetime = nextDatetime;
    }
}

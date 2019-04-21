package com.step.webServer.model;

import java.util.ArrayList;

public class PutRiskfactorModel {
    private String patientId;
    private String createDate;
    private ArrayList<RiskfactorModel> riskfactors;

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

    public ArrayList<RiskfactorModel> getRiskfactors() {
        return riskfactors;
    }

    public void setRiskfactors(ArrayList<RiskfactorModel> riskfactors) {
        this.riskfactors = riskfactors;
    }
}

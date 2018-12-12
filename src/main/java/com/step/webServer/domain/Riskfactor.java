package com.step.webServer.domain;

public class Riskfactor implements Domain {
    private Integer riskfactorId;
    private String riskfactorName;
    private Integer riskfactorclassId;

    public Integer getRiskfactorId() {
        return riskfactorId;
    }

    public void setRiskfactorId(Integer riskfactorId) {
        this.riskfactorId = riskfactorId;
    }

    public String getRiskfactorName() {
        return riskfactorName;
    }

    public void setRiskfactorName(String riskfactorName) {
        this.riskfactorName = riskfactorName;
    }

    public Integer getRiskfactorclassId() {
        return riskfactorclassId;
    }

    public void setRiskfactorclassId(Integer riskfactorclassId) {
        this.riskfactorclassId = riskfactorclassId;
    }
}

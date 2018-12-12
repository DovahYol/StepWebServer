package com.step.webServer.domain;

public class Riskfactorclass implements Domain {
    private Integer riskfactorclassId;
    private String riskfactorclassName;

    public Integer getRiskfactorclassId() {
        return riskfactorclassId;
    }

    public void setRiskfactorclassId(Integer riskfactorclassId) {
        this.riskfactorclassId = riskfactorclassId;
    }

    public String getRiskfactorclassName() {
        return riskfactorclassName;
    }

    public void setRiskfactorclassName(String riskfactorclassName) {
        this.riskfactorclassName = riskfactorclassName;
    }
}

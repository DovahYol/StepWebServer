package com.step.webServer.model;

public class RiskfactorModel {
    private String riskfactorId;
    private boolean checked;

    public String getRiskfactorId() {
        return riskfactorId;
    }

    public void setRiskfactorId(String riskfactorId) {
        this.riskfactorId = riskfactorId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}

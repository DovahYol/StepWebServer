package com.step.webServer.domain;

public class RiskfactorFollowup implements Domain {
    private Integer riskfactorId;
    private Integer followupId;
    private Boolean checked;

    public Integer getRiskfactorId() {
        return riskfactorId;
    }

    public void setRiskfactorId(Integer riskfactorId) {
        this.riskfactorId = riskfactorId;
    }

    public Integer getFollowupId() {
        return followupId;
    }

    public void setFollowupId(Integer followupId) {
        this.followupId = followupId;
    }

    public Boolean getChecked() {
        return checked;
    }

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}

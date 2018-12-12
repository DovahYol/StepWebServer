package com.step.webServer.domain;

public class Medicalinsurancetype implements Domain {
    private Integer medicalinsurancetypeId;
    private String medicalinsurancetypeName;

    public Integer getMedicalinsurancetypeId() {
        return medicalinsurancetypeId;
    }

    public void setMedicalinsurancetypeId(Integer medicalinsurancetypeId) {
        this.medicalinsurancetypeId = medicalinsurancetypeId;
    }

    public String getMedicalinsurancetypeName() {
        return medicalinsurancetypeName;
    }

    public void setMedicalinsurancetypeName(String medicalinsurancetypeName) {
        this.medicalinsurancetypeName = medicalinsurancetypeName;
    }
}

package com.step.webServer.domain;

public class MedicalInsuranceType implements Domain {
    private Integer medicalInsuranceTypeId;
    private String medicalInsuranceTypeName;

    public Integer getMedicalInsuranceTypeId() {
        return medicalInsuranceTypeId;
    }

    public void setMedicalInsuranceTypeId(Integer medicalInsuranceTypeId) {
        this.medicalInsuranceTypeId = medicalInsuranceTypeId;
    }

    public String getMedicalInsuranceTypeName() {
        return medicalInsuranceTypeName;
    }

    public void setMedicalInsuranceTypeName(String medicalInsuranceTypeName) {
        this.medicalInsuranceTypeName = medicalInsuranceTypeName;
    }
}


package com.step.webServer.domain;

public class Medicinerxtemplate implements Domain {
    private Integer medicinerxtemplateId;
    private String medicineName;
    private String specification;
    private String unit;
    private String medicineMode;
    private String medicineDosage;
    private String medicineTime;

    public Integer getMedicinerxtemplateId() {
        return medicinerxtemplateId;
    }

    public void setMedicinerxtemplateId(Integer medicinerxtemplateId) {
        this.medicinerxtemplateId = medicinerxtemplateId;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMedicineMode() {
        return medicineMode;
    }

    public void setMedicineMode(String medicineMode) {
        this.medicineMode = medicineMode;
    }

    public String getMedicineDosage() {
        return medicineDosage;
    }

    public void setMedicineDosage(String medicineDosage) {
        this.medicineDosage = medicineDosage;
    }

    public String getMedicineTime() {
        return medicineTime;
    }

    public void setMedicineTime(String medicineTime) {
        this.medicineTime = medicineTime;
    }
}

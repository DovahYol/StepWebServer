package com.step.webServer.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Patient implements Domain {
    private Integer patientId;
    private String patientName;
    private LocalDateTime createDatetime;
    private String gender;
    private String prcId;
    private LocalDate birthday;
    private Integer age;
    private String marriage;
    private String ethnicity;
    private String degreeEducation;
    private String profession;
    private String address;
    private String phoneNo;
    private Integer medicalInsuranceTypeId;
    private String emergContName;
    private String emergContPhoneNo;
    private String emergContRelationship;
    private Integer selfMonitor;
    private Integer dietGuide;
    private Integer saltLimitAndDrugGuidance;
    private Integer teamId;
    private LocalDate hbpDxDate;
    private double maxSbp;
    private double maxDbp;
    private String password;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPrcId() {
        return prcId;
    }

    public void setPrcId(String prcId) {
        this.prcId = prcId;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getMarriage() {
        return marriage;
    }

    public void setMarriage(String marriage) {
        this.marriage = marriage;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getDegreeEducation() {
        return degreeEducation;
    }

    public void setDegreeEducation(String degreeEducation) {
        this.degreeEducation = degreeEducation;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getMedicalInsuranceTypeId() {
        return medicalInsuranceTypeId;
    }

    public void setMedicalInsuranceTypeId(Integer medicalInsuranceTypeId) {
        this.medicalInsuranceTypeId = medicalInsuranceTypeId;
    }

    public String getEmergContName() {
        return emergContName;
    }

    public void setEmergContName(String emergContName) {
        this.emergContName = emergContName;
    }

    public String getEmergContPhoneNo() {
        return emergContPhoneNo;
    }

    public void setEmergContPhoneNo(String emergContPhoneNo) {
        this.emergContPhoneNo = emergContPhoneNo;
    }

    public String getEmergContRelationship() {
        return emergContRelationship;
    }

    public void setEmergContRelationship(String emergContRelationship) {
        this.emergContRelationship = emergContRelationship;
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

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public LocalDate getHbpDxDate() {
        return hbpDxDate;
    }

    public void setHbpDxDate(LocalDate hbpDxDate) {
        this.hbpDxDate = hbpDxDate;
    }

    public double getMaxSbp() {
        return maxSbp;
    }

    public void setMaxSbp(double maxSbp) {
        this.maxSbp = maxSbp;
    }

    public double getMaxDbp() {
        return maxDbp;
    }

    public void setMaxDbp(double maxDbp) {
        this.maxDbp = maxDbp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

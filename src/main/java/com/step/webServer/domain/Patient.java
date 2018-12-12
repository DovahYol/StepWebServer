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
    private String marriage;
    private String ethnicity;
    private String degreeEducation;
    private String profession;
    private String address;
    private String phoneNo;
    private String medicalInsuranceType;
    private String emergContName;
    private String emergContPhoneNo;
    private String emergContRelationship;
    private Integer teamId;

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

    public String getMedicalInsuranceType() {
        return medicalInsuranceType;
    }

    public void setMedicalInsuranceType(String medicalInsuranceType) {
        this.medicalInsuranceType = medicalInsuranceType;
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

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }
}

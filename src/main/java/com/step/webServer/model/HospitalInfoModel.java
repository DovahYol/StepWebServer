package com.step.webServer.model;

public class HospitalInfoModel {
    private String hospitalName;
    private String address;
    private String username;
    private String phoneNo;
    private String prcId;
    private Integer teamsNum;
    private Integer patientNum;
    private Integer validNum;
    private String validRate;

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPrcId() {
        return prcId;
    }

    public void setPrcId(String prcId) {
        this.prcId = prcId;
    }

    public Integer getTeamsNum() {
        return teamsNum;
    }

    public void setTeamsNum(Integer teamsNum) {
        this.teamsNum = teamsNum;
    }

    public Integer getPatientNum() {
        return patientNum;
    }

    public void setPatientNum(Integer patientNum) {
        this.patientNum = patientNum;
    }

    public Integer getValidNum() {
        return validNum;
    }

    public void setValidNum(Integer validNum) {
        this.validNum = validNum;
    }

    public String getValidRate() {
        return validRate;
    }

    public void setValidRate(String validRate) {
        this.validRate = validRate;
    }
}

package com.step.webServer.model;

import java.util.ArrayList;

public class PutPracticerxModel {
    private String patientId;
    private String createDate;
    ArrayList<PracticeRxModel> practiceRxes;

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public ArrayList<PracticeRxModel> getPracticeRxes() {
        return practiceRxes;
    }

    public void setPracticeRxes(ArrayList<PracticeRxModel> practiceRxes) {
        this.practiceRxes = practiceRxes;
    }
}

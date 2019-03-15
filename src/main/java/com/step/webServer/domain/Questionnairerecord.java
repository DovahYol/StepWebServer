package com.step.webServer.domain;

import java.time.LocalDate;

public class Questionnairerecord implements Domain{
    private Integer questionnairerecordId;
    private LocalDate questionnairerecordDate;
    private Integer questionnairePatientId;

    public Integer getQuestionnairerecordId() {
        return questionnairerecordId;
    }

    public void setQuestionnairerecordId(Integer questionnairerecordId) {
        this.questionnairerecordId = questionnairerecordId;
    }

    public LocalDate getQuestionnairerecordDate() {
        return questionnairerecordDate;
    }

    public void setQuestionnairerecordDate(LocalDate questionnairerecordDate) {
        this.questionnairerecordDate = questionnairerecordDate;
    }

    public Integer getQuestionnairePatientId() {
        return questionnairePatientId;
    }

    public void setQuestionnairePatientId(Integer questionnairePatientId) {
        this.questionnairePatientId = questionnairePatientId;
    }
}

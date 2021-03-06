package com.step.webServer.domain;

import java.time.LocalDateTime;

public class QuestionnairePatient implements Domain {
    private Integer questionnaireId;
    private Integer patientId;
    private LocalDateTime createDatetime;
    private Integer questionnairePatientId;

    public Integer getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Integer questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public LocalDateTime getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(LocalDateTime createDatetime) {
        this.createDatetime = createDatetime;
    }

    public Integer getQuestionnairePatientId() {
        return questionnairePatientId;
    }

    public void setQuestionnairePatientId(Integer questionnairePatientId) {
        this.questionnairePatientId = questionnairePatientId;
    }
}

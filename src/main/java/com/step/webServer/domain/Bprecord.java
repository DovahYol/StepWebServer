package com.step.webServer.domain;

import java.time.LocalDateTime;

public class Bprecord  implements Domain{
    private Integer bprecordId;

    private Double sbp;

    private Double dbp;

    private Double map;

    private Double pulseRate;

    private String measState;

    private String symptom;

    private LocalDateTime createDatetime;

    private Integer patientId;

}

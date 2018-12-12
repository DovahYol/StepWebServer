package com.step.webServer.util;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

@JsonPropertyOrder
public class ResponseError {
    public String code;
    public String message;
    public ResponseError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        try {
            return JsonUtil.defaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;//Never reach here.
    }
}

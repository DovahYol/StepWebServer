package com.step.webServer.util;

public interface ResponseErrorFactory {
    ResponseError create(String code, String message);
}

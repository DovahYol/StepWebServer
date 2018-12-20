package com.step.webServer.util;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class DefaultResponseErrorFactory implements ResponseErrorFactory{
    @Override
    public ResponseError create(String code, String message) {
        return new ResponseError(code, message);
    }
}

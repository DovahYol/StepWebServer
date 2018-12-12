package com.step.webServer.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public final class JsonUtil {
    private final static ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
    private JsonUtil(){}
    public static ObjectWriter defaultPrettyPrinter(){
        return ow;
    }
}

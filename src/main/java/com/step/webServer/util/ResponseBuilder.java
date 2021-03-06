package com.step.webServer.util;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TimeZone;

/**
 * https://stackoverflow.com/questions/14371335/spring-scoped-proxy-bean
 * https://stackoverflow.com/questions/29439528/spring-mvc-scope-proxy-bean-jackson-2
 */
@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class ResponseBuilder {
    @JsonIgnore
    private ObjectMapper objectMapper;
    private ResponseError error;
    private Map<String, Object> map;

    public ResponseBuilder(@Autowired ObjectMapper objectMapper){
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        this.objectMapper = objectMapper;
    }

    public ResponseError getError() {
        return error;
    }

    public void setError(ResponseError error) {
        this.error = error;
    }

    @JsonAnyGetter
    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    @JsonIgnore
    public String getJson(){
        try {
            return objectMapper.writerFor(ResponseBuilder.class).writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}

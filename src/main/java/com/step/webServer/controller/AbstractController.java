package com.step.webServer.controller;

import com.step.webServer.util.ResponseBuilder;
import org.springframework.beans.factory.annotation.Autowired;


public abstract class AbstractController {
    @Autowired
    protected ResponseBuilder responseBuilder;
}

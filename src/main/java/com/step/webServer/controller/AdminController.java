package com.step.webServer.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/admin", produces = "application/json;charset=UTF-8")
public class AdminController extends AbstractController {

    @GetMapping("/overview")
    public Object admin() {
        Map<String, Object> map = new HashMap<>();
        map.put("numTeam", 3);
        map.put("numDoctor", 3);
        map.put("numNurse", 3);
        map.put("numPatient", 3);
        responseBuilder.setMap(map);
        return map;
    }
}

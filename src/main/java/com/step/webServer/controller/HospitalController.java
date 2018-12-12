package com.step.webServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.step.webServer.dao.HospitalDao;
import com.step.webServer.domain.Hospital;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/hospital", produces = "application/json;charset=UTF-8")
public class HospitalController extends AbstractController{

    private HospitalDao hospitalDao;

    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
    }

    @GetMapping()
    public Object getAll() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitals", hospitalDao.selectAll());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping(value = "/insertOne", consumes = "application/json")
    public Object insertOne(@RequestBody Hospital hospital) throws JsonProcessingException {
        hospitalDao.insertOne(hospital);
        return responseBuilder.getJson();
    }
}

package com.step.webServer.controller;

import com.step.webServer.dao.MedicalInsuranceTypeDao;
import com.step.webServer.domain.MedicalInsuranceType;
import com.step.webServer.util.MapFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/medicalInsuranceType", produces = "application/json;charset=UTF-8")
public class MedicalInsuranceTypeController extends AbstractController{
    MedicalInsuranceTypeDao medicalInsuranceTypeDao;
    MapFactory<String, Object> mapFactory;

    public MedicalInsuranceTypeController(MedicalInsuranceTypeDao medicalInsuranceTypeDao, MapFactory<String, Object> beanMap) {
        this.medicalInsuranceTypeDao = medicalInsuranceTypeDao;
        this.mapFactory = beanMap;
    }

    @GetMapping("")
    public Object getAll() {
        List<MedicalInsuranceType> medicalInsuranceTypes =  medicalInsuranceTypeDao.getAll();
        Map<String, Object> map = mapFactory.create();
        map.put("medicalInsuranceTypes", medicalInsuranceTypes);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }
}

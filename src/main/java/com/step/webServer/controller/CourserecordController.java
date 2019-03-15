package com.step.webServer.controller;

import com.step.webServer.dao.PatientDao;
import com.step.webServer.util.MapFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/courserecord", produces = "application/json;charset=UTF-8")
public class CourserecordController extends AbstractController {
    private final PatientDao patientDao;
    private final MapFactory<String, Object> mapFactory;

    public CourserecordController(PatientDao patientDao, MapFactory<String, Object> mapFactory) {
        this.patientDao = patientDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping
    public Object courserecord(String patientId) {
        responseBuilder.setMap(patientDao.courserecord(Integer.valueOf(patientId)));
        return responseBuilder.getJson();
    }
}

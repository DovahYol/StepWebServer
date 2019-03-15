package com.step.webServer.controller;

import com.step.webServer.dao.QuestionnaireDao;
import com.step.webServer.util.MapFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/questionnaire", produces = "application/json;charset=UTF-8")
public class QuestionnaireController extends AbstractController{
    private final QuestionnaireDao questionnaireDao;
    private final MapFactory<String, Object> mapFactory;

    public QuestionnaireController(QuestionnaireDao questionnaireDao, MapFactory<String, Object> mapFactory) {
        this.questionnaireDao = questionnaireDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping
    public Object questionnaire(String patientId) {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("questionnaires", questionnaireDao.questionnairesByPatientId(Integer.valueOf(patientId)));
        return responseBuilder.getJson();
    }
}

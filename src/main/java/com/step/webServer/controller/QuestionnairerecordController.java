package com.step.webServer.controller;

import com.step.webServer.dao.QuestionnairerecordDao;
import com.step.webServer.util.MapFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/questionnairerecord", produces = "application/json;charset=UTF-8")
public class QuestionnairerecordController extends AbstractController{

    private final QuestionnairerecordDao questionnairerecordDao;
    private final MapFactory<String, Object> mapFactory;

    public QuestionnairerecordController(QuestionnairerecordDao questionnairerecordDao, MapFactory<String, Object> mapFactory) {
        this.questionnairerecordDao = questionnairerecordDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping
    public Object questionnairerecord(String questionnairePatientId) {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("questionnairerecords",
                questionnairerecordDao
                        .questionnairerecordsByQuestionnairePatientId(
                                Integer.valueOf(questionnairePatientId)));
        return responseBuilder.getJson();
    }
}

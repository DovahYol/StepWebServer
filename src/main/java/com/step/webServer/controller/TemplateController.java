package com.step.webServer.controller;

import com.step.webServer.dao.TemplateDao;
import com.step.webServer.util.MapFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/template", produces = "application/json;charset=UTF-8")
public class TemplateController extends AbstractController{

    private final TemplateDao templateDao;
    private final MapFactory<String, Object> mapFactory;

    public TemplateController(TemplateDao templateDao, MapFactory<String, Object> mapFactory) {
        this.templateDao = templateDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping("")
    public Object getTemplate(String templateType){
        Map<String, Object> map = mapFactory.create();
        map.put("contents", templateDao.templatesByType(templateType));
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping("/delete")
    @Transactional
    public Object deleteTemplatesById(String contentId) throws Exception {
        int numDelete = templateDao.deleteTemplatesById(contentId);
        return responseBuilder.getJson();
    }

    @PostMapping("/add")
    public Object addTemplate(String templateType, String templateContent) {
        templateDao.insertTemplate(templateContent, templateType);
        return responseBuilder.getJson();
    }
}

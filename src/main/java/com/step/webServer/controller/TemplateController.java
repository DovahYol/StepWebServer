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

    @DeleteMapping("/delete")
    @Transactional
    public Object deleteTemplatesById(List<String> contentIds) throws Exception {
        for (String contentId :
                contentIds) {
            int numDelete = templateDao.deleteTemplatesById(contentId);
            if (numDelete != 1) throw new Exception("删除模板数不为1");
        }
        return responseBuilder.getJson();
    }

    @PostMapping("/template/add")
    public Object addTemplate(String templateType, List<String> templateContents) {
        for (String templateContent :
                templateContents) {
            templateDao.insertTemplate(templateContent, templateType);
        }
        return responseBuilder.getJson();
    }
}

package com.step.webServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.step.webServer.dao.RoleDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/role", produces = "application/json;charset=UTF-8")
public class RoleController extends AbstractController{

    private RoleDao roleDao;

    public RoleController(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @GetMapping("/employee")
    public Object getAll() throws JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("roles", roleDao.selectAllEmployees());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

}

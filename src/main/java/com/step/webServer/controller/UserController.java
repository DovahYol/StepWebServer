package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController extends AbstractController{
    private UserDao userDao;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/mySidebar")
    public Object getMySidebar(Authentication authentication) {
        Map<String, Object> map = new HashMap<>();
        List<String> sideBar = userDao.getSidebarByUsername(((UserPrincipal)authentication.getPrincipal()).getUsername());
        map.put("sections", sideBar);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }
}

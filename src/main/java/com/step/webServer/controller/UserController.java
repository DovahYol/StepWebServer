package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.model.UserModel;
import com.step.webServer.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController extends AbstractController{
    private UserDao userDao;
    private FileService fileService;
    @Autowired
    HttpServletRequest request;

    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping("/mySidebar")
    public Object getMySidebar() {
        Map<String, Object> map = new HashMap<>();
        List<String> sideBar = userDao.getSidebarByUsername((String)request.getSession().getAttribute("username"));
        map.put("sections", sideBar);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping("/myself")
    public Object myself() {
        int userId = (int) request.getSession().getAttribute("userId");
        ApplicationUser applicationUser = userDao.selectByUserId(userId);
        Map<String, Object> map = new HashMap<>();
        map.put("username", applicationUser.getUsername());
        map.put("password", applicationUser.getPassword());
        map.put("gender", applicationUser.getGender());
        map.put("birthday", applicationUser.getBirthday());
        map.put("prcId", applicationUser.getPrcId());
        map.put("hospitalId", applicationUser.getHospitalId());
        map.put("department", applicationUser.getDepartment());
        map.put("position", applicationUser.getPosition());
        map.put("licenseId", applicationUser.getLicenseId());
        map.put("phoneNo", applicationUser.getPhoneNo());
        map.put("teamId", applicationUser.getTeamId());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping(value = "/downloadMyPicture", produces = "application/octet-stream")
    public void downloadMyPicture(HttpServletResponse response) throws IOException {
        int userId = (int) request.getSession().getAttribute("userId");
        ApplicationUser user = userDao.selectByUserId(userId);
        FileCopyUtils.copy(
                new FileInputStream(new File(user.getPicturePath())),
                response.getOutputStream()
        );
    }

    @PostMapping(value = "/myself")
    @Transactional
    public Object postMyself(UserModel userModel) throws IOException {
        int userId = (int) request.getSession().getAttribute("userId");
        CompletableFuture<String> future = null;
        if(userModel.getPicture() != null) {
            future = fileService.savePicture(userModel.getPicture());
        }
        ApplicationUser applicationUser = userDao.selectByUserId(userId);
        applicationUser.setUsername(userModel.getUsername());
        applicationUser.setPassword(userModel.getPassword());
        applicationUser.setGender(userModel.getGender());
        applicationUser.setAge(userModel.getAge());
        applicationUser.setPrcId(userModel.getPrcId());
        applicationUser.setDepartment(userModel.getDepartment());
        applicationUser.setLicenseId(userModel.getLicenseId());
        applicationUser.setPhoneNo(userModel.getPhoneNo());
        applicationUser.setHospitalId(Integer.valueOf(userModel.getHospitalId()));
        applicationUser.setRoleId(Integer.valueOf(userModel.getRoleId()));
        if (future != null) {
            try {
                applicationUser.setPicturePath(future.get());
            } catch (InterruptedException | ExecutionException e) {//TODO
                e.printStackTrace();
            }
        }
        userDao.updateOne(applicationUser);
        return responseBuilder.getJson();
    }
}

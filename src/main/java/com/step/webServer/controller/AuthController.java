package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.model.UserModel;
import com.step.webServer.service.FileService;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.ResponseError;
import com.step.webServer.util.ResponseErrorFactory;
import com.step.webServer.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AuthController extends AbstractController{

    private final UserDao userDao;
    private final FileService fileService;
    private final ResponseErrorFactory responseErrorFactory;
    private final MapFactory<String, Object> mapFactory;
    @Autowired
    HttpServletRequest request;

    public AuthController(UserDao userDao, FileService fileService, ResponseErrorFactory responseErrorFactory, MapFactory<String, Object> mapFactory) {
        this.userDao = userDao;
        this.fileService = fileService;
        this.responseErrorFactory = responseErrorFactory;
        this.mapFactory = mapFactory;
    }

    @PostMapping("/login")
    public Object login(String username, String password) {
        ApplicationUser appUser = userDao.selectByUsername(username);
        if (password == null || !password.equals(appUser.getPassword())) {
            ResponseError error = new ResponseError("待定", "登录密码不正确");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }else if (!appUser.getConfirmed()) {
            ResponseError error = new ResponseError("待定", "账号尚未通过审核！");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        request.getSession().setAttribute(SessionUtil.USERNAME, username);
        request.getSession().setAttribute(SessionUtil.USER_ID, appUser.getUserId());
        request.getSession().setAttribute(SessionUtil.ROLE_ID, appUser.getRoleId());
        return responseBuilder.getJson();
    }


    @GetMapping("/")
    public Object root() {
        return responseBuilder.getJson();
    }

    @PostMapping("/signup")
    @Transactional
    public Object signup(UserModel userModel) throws IOException {
        if (userModel.getUsername() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "username为null"));
            return responseBuilder.getJson();
        }else if (userModel.getPassword() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "password为null"));
            return responseBuilder.getJson();
        }else if (userModel.getHospitalId() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "hospitalId为null"));
            return responseBuilder.getJson();
        }else if (userModel.getRoleId() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "roleId为null"));
            return responseBuilder.getJson();
        }
        int hospitalIdInt;
        try{
            hospitalIdInt = Integer.valueOf(userModel.getHospitalId());
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "hospitalId应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        int roleIdInt;
        try {
            roleIdInt = Integer.valueOf(userModel.getRoleId());
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "roleId应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        ApplicationUser applicationUser = new ApplicationUser();
        if (userModel.getPicture() != null) {
            String path = fileService.savePicture(userModel.getPicture());
            applicationUser.setPicturePath(path);
        }
        applicationUser.setUsername(userModel.getUsername());
        applicationUser.setPassword(userModel.getPassword());
        applicationUser.setGender(userModel.getGender());
        applicationUser.setAge(userModel.getAge());
        applicationUser.setPrcId(userModel.getPrcId());
        applicationUser.setDepartment(userModel.getDepartment());
        applicationUser.setLicenseId(userModel.getLicenseId());
        applicationUser.setPhoneNo(userModel.getPhoneNo());
        applicationUser.setHospitalId(hospitalIdInt);
        applicationUser.setRoleId(roleIdInt);
        applicationUser.setConfirmed(false);

        if (userDao.containsUser(userModel.getUsername())) {
            ResponseError error = new ResponseError("未定","该用户已存在");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        userDao.insertOne(applicationUser);
        return responseBuilder.getJson();
    }
}

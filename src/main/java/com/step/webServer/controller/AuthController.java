package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.model.UserModel;
import com.step.webServer.service.FileService;
import com.step.webServer.util.ResponseError;
import com.step.webServer.util.ResponseErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AuthController extends AbstractController{

    private UserDao userDao;
    private FileService fileService;
    private ResponseErrorFactory responseErrorFactory;
    @Autowired
    HttpServletRequest request;
    @Autowired
    BCryptPasswordEncoder encoder;

    public AuthController(UserDao userDao,FileService fileService, ResponseErrorFactory responseErrorFactory) {
        this.userDao = userDao;
        this.fileService = fileService;
        this.responseErrorFactory = responseErrorFactory;
    }

    @PostMapping("/login")
    public Object login(String username, String password) {
        ApplicationUser appUser = userDao.selectByUsername(username);
        request.getSession().setAttribute("username", username);
        request.getSession().setAttribute("userId", appUser.getUserId());
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
        String path = fileService.savePicture(userModel.getPicture());
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userModel.getUsername());
        applicationUser.setPassword(encoder.encode(userModel.getPassword()));
        applicationUser.setGender(userModel.getGender());
        applicationUser.setAge(userModel.getAge());
        applicationUser.setPrcId(userModel.getPrcId());
        applicationUser.setDepartment(userModel.getDepartment());
        applicationUser.setLicenseId(userModel.getLicenseId());
        applicationUser.setPhoneNo(userModel.getPhoneNo());
        applicationUser.setHospitalId(Integer.valueOf(userModel.getHospitalId()));
        applicationUser.setRoleId(Integer.valueOf(userModel.getRoleId()));
        applicationUser.setConfirmed(false);
        applicationUser.setPicturePath(path);
        if (userDao.containsUser(userModel.getUsername())) {
            ResponseError error = new ResponseError("未定","该用户已存在");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        userDao.insertOne(applicationUser);
        return responseBuilder.getJson();
    }
}

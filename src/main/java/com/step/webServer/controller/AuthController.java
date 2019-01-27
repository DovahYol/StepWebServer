package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.model.SignupModel;
import com.step.webServer.service.FileService;
import com.step.webServer.util.ResponseError;
import com.step.webServer.util.ResponseErrorFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AuthController extends AbstractController{

    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private FileService fileService;
    private ResponseErrorFactory responseErrorFactory;

    public AuthController(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder, FileService fileService, ResponseErrorFactory responseErrorFactory) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.fileService = fileService;
        this.responseErrorFactory = responseErrorFactory;
    }

    @PostMapping("/login?error")
    public Object login(String error) {
        return responseBuilder.getJson();
    }


    @GetMapping("/")
    public Object root() {
        return responseBuilder.getJson();
    }

    @PostMapping("/signup")
    @Transactional
    public Object signup(SignupModel signupModel) throws IOException {
        if (signupModel.getUsername() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "username为null"));
            return responseBuilder.getJson();
        }else if (signupModel.getPassword() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "password为null"));
            return responseBuilder.getJson();
        }else if (signupModel.getHospitalId() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "hospitalId为null"));
            return responseBuilder.getJson();
        }else if (signupModel.getRoleId() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "roleId为null"));
            return responseBuilder.getJson();
        }
        CompletableFuture<String> future = null;
        if(signupModel.getPicture() != null) {
            future = fileService.savePicture(signupModel.getPicture());
        }
        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(signupModel.getUsername());
        applicationUser.setPassword(bCryptPasswordEncoder.encode(signupModel.getPassword()));
        applicationUser.setGender(signupModel.getGender());
        applicationUser.setAge(signupModel.getAge());
        applicationUser.setPrcId(signupModel.getPrcId());
        applicationUser.setDepartment(signupModel.getDepartment());
        applicationUser.setLicenseId(signupModel.getLicenseId());
        applicationUser.setPhoneNo(signupModel.getPhoneNo());
        applicationUser.setHospitalId(Integer.valueOf(signupModel.getHospitalId()));
        applicationUser.setRoleId(Integer.valueOf(signupModel.getRoleId()));
        applicationUser.setConfirmed(0);
        if (future != null) {
            try {
                applicationUser.setPicturePath(future.get());
            } catch (InterruptedException | ExecutionException e) {//TODO
                e.printStackTrace();
            }
        }
        if (userDao.containsUser(signupModel.getUsername())) {
            ResponseError error = new ResponseError("未定","该用户已存在");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        userDao.insertOne(applicationUser);
        return responseBuilder.getJson();
    }
}

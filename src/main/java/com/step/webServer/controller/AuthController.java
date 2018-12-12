package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.model.SignupModel;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping(produces = "application/json;charset=UTF-8")
public class AuthController extends AbstractController{

    private UserDao userDao;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthController(UserDao userDao, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userDao = userDao;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping("/signup")
    public Object signup(SignupModel signupModel) throws IOException {
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

        File directory = new File(System.getProperty("user.home") + File.separator + "webServerPictures");
        if(!directory.exists()){
            directory.mkdir();
        }
        File file = File.createTempFile("user", "", directory);
        signupModel.getPicture().transferTo(file);
        applicationUser.setPicturePath(file.getPath());

        userDao.insertOne(applicationUser);
        return responseBuilder.getJson();
    }

}

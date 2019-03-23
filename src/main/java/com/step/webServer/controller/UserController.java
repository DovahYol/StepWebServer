package com.step.webServer.controller;

import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.model.UserModel;
import com.step.webServer.security.UserPrincipal;
import com.step.webServer.service.FileService;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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

    @GetMapping("/myself")
    public Object myself(Authentication authentication) {
        int userId = getUserId(authentication);
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

    private int getUserId(Authentication authentication) {
        String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
        return userDao.selectByUsername(username).getUserId();
    }

    @GetMapping(value = "/downloadMyPicture", produces = "application/octet-stream")
    public void downloadMyPicture(HttpServletResponse response, Authentication authentication) throws IOException {
        int userId = getUserId(authentication);
        ApplicationUser user = userDao.selectByUserId(userId);
        FileCopyUtils.copy(
                new FileInputStream(new File(user.getPicturePath())),
                response.getOutputStream()
        );
    }

    @PostMapping(value = "/myself")
    @Transactional
    public Object postMyself(UserModel userModel, Authentication authentication) throws IOException {
        int userId = getUserId(authentication);
        CompletableFuture<String> future = null;
        if(userModel.getPicture() != null) {
            future = fileService.savePicture(userModel.getPicture());
        }
        ApplicationUser applicationUser = userDao.selectByUserId(userId);
        applicationUser.setUsername(userModel.getUsername());
        applicationUser.setPassword(bCryptPasswordEncoder.encode(userModel.getPassword()));
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

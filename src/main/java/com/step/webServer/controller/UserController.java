package com.step.webServer.controller;

import com.step.webServer.dao.HospitalDao;
import com.step.webServer.dao.TeamDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.domain.Hospital;
import com.step.webServer.model.UserModel;
import com.step.webServer.service.FileService;
import com.step.webServer.util.MapFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController extends AbstractController{
    private UserDao userDao;
    private TeamDao teamDao;
    private FileService fileService;
    private HospitalDao hospitalDao;
    private final MapFactory<String, Object> mapFactory;
    @Autowired
    HttpServletRequest request;

    @Autowired
    BCryptPasswordEncoder encoder;

    public UserController(UserDao userDao, TeamDao teamDao, FileService fileService, MapFactory<String, Object> mapFactory) {
        this.userDao = userDao;
        this.teamDao = teamDao;
        this.fileService = fileService;
        this.mapFactory = mapFactory;
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
        map.put("teamName", teamDao.teamById(applicationUser.getTeamId()).getTeamName());
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
        ApplicationUser user = userDao.selectByUserId(userId);
        user.setPassword(encoder.encode(userModel.getPassword()));
        user.setPhoneNo(userModel.getPhoneNo());
        userDao.updateOne(user);
        return responseBuilder.getJson();
    }

    @PostMapping(value = "/uploadMyPicture")
    public Object uploadMyPicture(MultipartFile picture) throws IOException {
        int userId = (int) request.getSession().getAttribute("userId");
        ApplicationUser user = userDao.selectByUserId(userId);
        String path = fileService.savePicture(picture);
        user.setPicturePath(path);
        userDao.updateOne(user);
        return responseBuilder.getJson();
    }


    @GetMapping("/unconfirmed")
    public Object unconfirmed() {
        Map<String, Object> map = mapFactory.create();
        map.put("reviews", userDao.getUnconfirmed());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping("/unconfirmed")
    public Object postUnconfirmed(boolean confirmed, int userId) {
        userDao.updateConfirmed(confirmed, userId);
        return responseBuilder.getJson();
    }

    @PostMapping("/createAdminAndHospital")
    @Transactional
    public Object createAdminAndHospital(
            String hospitalName,
            String address,
            String userName,
            String phoneNo,
            String prcId) {

        ApplicationUser applicationUser = new ApplicationUser();
        applicationUser.setUsername(userName);
        applicationUser.setPhoneNo(phoneNo);
        applicationUser.setPrcId(prcId);
        applicationUser.setRoleId(3);
        applicationUser.setConfirmed(true);
        userDao.insertOne(applicationUser);

        Hospital hospital = new Hospital();
        hospital.setHospitalName(hospitalName);
        hospital.setAddress(address);
        hospital.setAdminId(applicationUser.getUserId());
        hospitalDao.insertOne(hospital);

        return responseBuilder.getJson();
    }

}

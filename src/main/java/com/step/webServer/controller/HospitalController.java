package com.step.webServer.controller;

import com.step.webServer.dao.HospitalDao;
import com.step.webServer.dao.PatientDao;
import com.step.webServer.dao.TeamDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.domain.Hospital;
import com.step.webServer.model.HospitalInfoModel;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.ResponseError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/hospital", produces = "application/json;charset=UTF-8")
public class HospitalController extends AbstractController{

    private final HospitalDao hospitalDao;
    private final UserDao userDao;
    private final TeamDao teamDao;
    private final PatientDao patientDao;
    @Autowired
    HttpServletRequest request;

    @Autowired
    MapFactory<String, Object> mapFactory;

    public HospitalController(HospitalDao hospitalDao, UserDao userDao, TeamDao teamDao, PatientDao patientDao) {
        this.hospitalDao = hospitalDao;
        this.userDao = userDao;
        this.teamDao = teamDao;
        this.patientDao = patientDao;
    }

    @GetMapping()
    public Object getAll() {
        Map<String, Object> map = new HashMap<>();
        map.put("hospitals", hospitalDao.selectAll());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping(value = "/insertOne", consumes = "application/json")
    public Object insertOne(@RequestBody Hospital hospital) {
        hospitalDao.insertOne(hospital);
        return responseBuilder.getJson();
    }

    @GetMapping(value = "/mine")
    public Object getMine() {
        int userId = (int)request.getSession().getAttribute("userId");
        Hospital hospital = hospitalDao.selectHospitalByAdminId(userId);
        Map<String, Object> map = new HashMap<>();
        if (hospital != null) {
            map.put("address", hospital.getAddress());
            map.put("hospitalName", hospital.getHospitalName());
            responseBuilder.setMap(map);
        } else {
            responseBuilder.setError(new ResponseError("1", "没有你管理的医院"));
        }
        return responseBuilder.getJson();
    }

    @PostMapping(value = "/mine")
    public Object postMine(Hospital model) {
        int userId = (int)request.getSession().getAttribute("userId");
        Hospital hospital = hospitalDao.selectHospitalByAdminId(userId);
        if (hospital != null) {
            hospital.setAddress(model.getAddress());
            hospital.setHospitalId(model.getHospitalId());
            hospital.setHospitalName(model.getHospitalName());
            hospitalDao.updateHospitalByAdminId(hospital);
        } else {
            model.setAdminId(userId);
            hospitalDao.insertOne(model);
        }
        return responseBuilder.getJson();
    }

    private int getUserId() {
        String username = (String) request.getSession().getAttribute("username");
        return userDao.selectByUsername(username).getUserId();
    }

    @GetMapping("/getHospitalInfo")
    @Transactional
    public Object getHospitalInfo(String hospitalId) {
        int hospitalIdInt;
        try{
            hospitalIdInt = Integer.valueOf(hospitalId);
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "hospitalId不正确");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        Map<String, Object> params = mapFactory.create();
        HospitalInfoModel hospitalInfoModel = new HospitalInfoModel();
        params.put("hospital", hospitalInfoModel);

        Hospital hospital = hospitalDao.selectHospitalByHospitalId(hospitalIdInt);
        if (hospital != null) {
            hospitalInfoModel.setHospitalName(hospital.getHospitalName());
            hospitalInfoModel.setAddress(hospital.getAddress());

            Integer adminId = hospital.getAdminId();
            ApplicationUser user = userDao.selectByUserId(adminId);
            if (user != null) {
                hospitalInfoModel.setUsername(user.getUsername());
                hospitalInfoModel.setPhoneNo(user.getPhoneNo());
                hospitalInfoModel.setPrcId(user.getPrcId());

                int teamNum = teamDao.getTeamNumByAdminId(user.getUserId());
                int patientNum = patientDao.getPatientNumByAdminId(user.getUserId());
                int validNum = 10;
                String validRate = "5%";

                hospitalInfoModel.setTeamsNum(teamNum);
                hospitalInfoModel.setPatientNum(patientNum);
                hospitalInfoModel.setValidNum(validNum);
                hospitalInfoModel.setValidRate(validRate);
            }
        }
        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }
}

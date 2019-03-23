package com.step.webServer.controller;

import com.step.webServer.dao.HospitalDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Hospital;
import com.step.webServer.security.UserPrincipal;
import com.step.webServer.util.ResponseError;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/hospital", produces = "application/json;charset=UTF-8")
public class HospitalController extends AbstractController{

    private HospitalDao hospitalDao;
    private UserDao userDao;

    public HospitalController(HospitalDao hospitalDao) {
        this.hospitalDao = hospitalDao;
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

    @GetMapping(value = "/mine", consumes = "application/json")
    public Object getMine(Authentication authentication) {
        int userId = getUserId(authentication);
        Hospital hospital = hospitalDao.selectHospitalByAdminId(userId);
        Map<String, Object> map = new HashMap<>();
        if (hospital != null) {
            map.put("address", hospital.getAddress());
            map.put("hospitalName", hospital.getHospitalName());
            map.put("hospitalId", hospital.getHospitalId());
            responseBuilder.setMap(map);
        } else {
            responseBuilder.setError(new ResponseError("1", "没有你管理的医院"));
        }
        return responseBuilder.getJson();
    }

    @PutMapping(value = "/mine", consumes = "application/json")
    public Object putMine(Hospital model, Authentication authentication) {
        int userId = getUserId(authentication);
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

    private int getUserId(Authentication authentication) {
        String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
        return userDao.selectByUsername(username).getUserId();
    }
}

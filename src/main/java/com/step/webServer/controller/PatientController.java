package com.step.webServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.step.webServer.dao.PatientDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Patient;
import com.step.webServer.model.PatientAddingModel;
import com.step.webServer.security.UserPrincipal;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.ResponseErrorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/patient", produces = "application/json;charset=UTF-8")
public class PatientController extends AbstractController{
    private final PatientDao patientDao;
    private final UserDao userDao;
    private MapFactory<String, Object> mapFactory;
    private ResponseErrorFactory responseErrorFactory;

    @Autowired
    public void setMapFactory(MapFactory<String, Object> mapFactory) {
        this.mapFactory = mapFactory;
    }

    @Autowired
    public void setResponseErrorFactory(ResponseErrorFactory responseErrorFactory) {
        this.responseErrorFactory = responseErrorFactory;
    }

    public PatientController(PatientDao patientDao, UserDao userDao) {
        this.patientDao = patientDao;
        this.userDao = userDao;
    }

    @GetMapping
    public Object getAllPatients(String keyword, int pageNum, int pageSize, String orderBy, boolean isAsc, Authentication authentication) throws JsonProcessingException {
        String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
        String direction = isAsc? " asc" : " desc";
        if("patientId".equals(orderBy)) {
            orderBy = "patient_id" + direction;
        }else if ("patientName".equals(orderBy)) {
            orderBy = "patient_name" + direction;
        }else if ("gender".equals(orderBy)) {
            orderBy = "gender" + direction;
        }else if ("age".equals(orderBy)) {
            orderBy = "age" + direction;
        }else if("latestHomeBP".equals(orderBy)) {
            orderBy = "latest_home_bp" + direction;
        }else {
            orderBy = null;
        }
        Map<String, Object> map = mapFactory.create();
        map.put("username", username);
        map.put("keyword", keyword);
        List<Map<String, Object>> patients = patientDao.selectAllPatients(pageNum, pageSize, orderBy, map);
        Map<String, Object> responseMap = mapFactory.create();
        responseMap.put("patients", patients);
        responseBuilder.setMap(responseMap);
        return responseBuilder.getJson();
    }

    @PostMapping("/add")
    @Transactional
    public Object addPatient(PatientAddingModel patientAddingModel, Authentication authentication) {
        Patient patient = new Patient();
        if (patientAddingModel.getPatientName() == null) {
            responseBuilder.setError(responseErrorFactory.create("未定", "patientName为null"));
            return responseBuilder.getJson();
        }
        patient.setPatientName(patientAddingModel.getPatientName());
        patient.setCreateDatetime(LocalDateTime.now());
        if (StringUtils.isEmpty(patientAddingModel.getGender())) {
            patient.setGender(null);
        }else {
            patient.setGender(patientAddingModel.getGender());
        }
        patient.setPrcId(patientAddingModel.getPrcId());
        String birthDay = patientAddingModel.getBirthday();
        if (!StringUtils.isEmpty(birthDay)) {
            if (birthDay.length() != 8) {
                responseBuilder.setError(responseErrorFactory.create("未定", "birthDay的长度必须为8"));
                return responseBuilder.getJson();
            }
            int year, month, dayOfMonth;
            try{
                year = Integer.valueOf(birthDay.substring(0, 4));
                month = Integer.valueOf(birthDay.substring(4, 6));
                dayOfMonth = Integer.valueOf(birthDay.substring(6, 8));
            }catch (NumberFormatException ex) {
                responseBuilder.setError(responseErrorFactory.create("未定", "birthDay必须只包含数字"));
                return responseBuilder.getJson();
            }
            try{
                patient.setBirthday(LocalDate.of(year, month, dayOfMonth));
            }catch (DateTimeException ex) {
                responseBuilder.setError(responseErrorFactory.create("未定", "birthDay不是一个合法的时间"));
                return responseBuilder.getJson();
            }
        }
        patient.setAge(patientAddingModel.getAge());
        patient.setMarriage(patientAddingModel.getMarriage());
        patient.setEthnicity(patientAddingModel.getEthnicity());
        patient.setDegreeEducation(patientAddingModel.getDegreeEducation());
        patient.setProfession(patientAddingModel.getProfession());
        patient.setAddress(patientAddingModel.getAddress());
        patient.setPhoneNo(patientAddingModel.getPhoneNo());
        try{
            int temp = Integer.valueOf(patientAddingModel.getMedicalInsuranceTypeId());
            patient.setMedicalInsuranceTypeId(temp);
        }catch (NumberFormatException ex) {
            responseBuilder.setError(responseErrorFactory.create("未定", "medicalInsuranceTypeId必须为数字"));
            return responseBuilder.getJson();
        }
        patient.setEmergContName(patientAddingModel.getEmergContName());
        patient.setEmergContPhoneNo(patientAddingModel.getEmergContPhoneNo());
        patient.setEmergContRelationship(patientAddingModel.getEmergContRelationship());
        patient.setTeamId(userDao.selectTeamIdByUsername(((UserPrincipal)authentication.getPrincipal()).getUsername()));
        if (patientDao.hasPatient(patientAddingModel.getPatientName())) {
            responseBuilder.setError(responseErrorFactory.create("未定", "patientName已存在"));
            return responseBuilder.getJson();
        }
        patientDao.insertPatient(patient);
        return responseBuilder.getJson();
    }
}

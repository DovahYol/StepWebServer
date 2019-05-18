package com.step.webServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.step.webServer.dao.BprecordDao;
import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.PatientDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Followup;
import com.step.webServer.domain.Patient;
import com.step.webServer.model.PatientAddingModel;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.ResponseErrorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
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
    private final BprecordDao bprecordDao;
    private final FollowupDao followupDao;
    private MapFactory<String, Object> mapFactory;
    private ResponseErrorFactory responseErrorFactory;
    @Autowired
    HttpServletRequest request;

    @Autowired
    public void setMapFactory(MapFactory<String, Object> mapFactory) {
        this.mapFactory = mapFactory;
    }

    @Autowired
    public void setResponseErrorFactory(ResponseErrorFactory responseErrorFactory) {
        this.responseErrorFactory = responseErrorFactory;
    }

    public PatientController(PatientDao patientDao, UserDao userDao, BprecordDao bprecordDao, FollowupDao followupDao) {
        this.patientDao = patientDao;
        this.userDao = userDao;
        this.bprecordDao = bprecordDao;
        this.followupDao = followupDao;
    }

    @GetMapping
    public Object getAllPatients(String keyword, int pageNum, int pageSize, String orderBy, boolean isAsc) throws JsonProcessingException {
        String username = (String)request.getSession().getAttribute("username");
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
    public Object addPatient(PatientAddingModel patientAddingModel) {
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
        patient.setTeamId(userDao.selectTeamIdByUsername((String) request.getSession().getAttribute("username")));
        LocalDate localDate = LocalDate.parse(patientAddingModel.getHdpDxDate());
        patient.setHbpDxDate(localDate);
        patient.setMaxSbp(patientAddingModel.getMaxSbp());
        patient.setMaxDbp(patientAddingModel.getMaxDbp());
        patientDao.insertPatient(patient);
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patient.getPatientId());
        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }


    @GetMapping("/meta")
    public Object getMeta(String patientId) {
        responseBuilder.setMap(patientDao.getPatientMeta(patientId));
        return responseBuilder.getJson();
    }

    @GetMapping("/overview")
    public Object getOverview(String patientId) {
        responseBuilder.setMap(patientDao.getPatientOverview());
        return responseBuilder.getJson();
    }

    //未达标次数（numInvalid），达标率（validRate）未实现
    @GetMapping("/bpAnalysis")
    public Object getBpAnalysis(String patientId) {
        Followup fl = followupDao.getPatientsLatestFollowup(patientId);
        double sbpTarget, dbpTarget;
        if (fl == null || fl.getDbpTarget() == null || fl.getSbpTarget() == null) {
            sbpTarget = 140;
            dbpTarget = 90;
        }else {
            sbpTarget = fl.getSbpTarget();
            dbpTarget = fl.getDbpTarget();
        }

        Map<String, Object> map =bprecordDao.bpOverview(patientId, sbpTarget, dbpTarget);

        map.put("bpRecords", bprecordDao.bpRecords(patientId));
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }
}

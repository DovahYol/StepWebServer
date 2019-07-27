package com.step.webServer.controller;

import com.step.webServer.dao.BprecordDao;
import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.PatientDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Bprecord;
import com.step.webServer.domain.Followup;
import com.step.webServer.domain.Patient;
import com.step.webServer.model.AndroidBprecordModel;
import com.step.webServer.util.ResponseError;
import com.step.webServer.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@RestController
@RequestMapping(value = "/api", produces = "application/json;charset=UTF-8")
public class AndroidController extends AbstractController  {
    private final UserDao userDao;
    private final PatientDao patientDao;
    private final FollowupDao followupDao;
    private final BprecordDao bprecordDao;

    @Autowired
    HttpServletRequest request;

    public AndroidController(UserDao userDao, PatientDao patientDao, FollowupDao followupDao, BprecordDao bprecordDao) {
        this.userDao = userDao;
        this.patientDao = patientDao;
        this.followupDao = followupDao;
        this.bprecordDao = bprecordDao;
    }

    @PostMapping("/login")
    public Object login(String password, String prcId) {
        Patient pt = null;
        List<Patient> patientList = patientDao.getPatientByPrcId(prcId);
        if (patientList.size() > 0) {
            pt = patientList.get(0);
            if (password != null && !password.equals(pt.getPassword())) {
                ResponseError error = new ResponseError("待定", "密码错误");
                responseBuilder.setError(error);
                return responseBuilder.getJson();
            }
        }
        if (pt == null) {
            ResponseError error = new ResponseError("待定", "患者不存在");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        request.getSession().setAttribute(SessionUtil.PATIENT_ID, pt.getPatientId());
        request.getSession().setAttribute(SessionUtil.PATIENT_NAME, pt.getPatientName());
        request.getSession().setAttribute(SessionUtil.PATIENT_PRC_ID, pt.getPrcId());
        Map<String, Object> map = new HashMap<>();
        map.put("prcId", pt.getPrcId());
        map.put("username", pt.getPatientName());
        map.put("address", pt.getAddress());
        map.put("phoneNo", pt.getPhoneNo());
        map.put("stepDay", DAYS.between(pt.getCreateDatetime().toLocalDate(), LocalDate.now()));
        map.put("sbpTarget", 140);
        map.put("dbpTarget", 90);

        Followup followup = followupDao.getPatientsLatestFollowup(pt.getPatientId() + "");
        if (followup != null) {
            map.put("sbpTarget", followup.getSbpTarget());
            map.put("dbpTarget", followup.getDbpTarget());
        }
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping("/getBP")
    public Object getBP(String prcId) {
        List<Map<String, Object>> lists = bprecordDao.getBpRecordsByPrcId(prcId);

        Map<String, Object> map = new HashMap<>();
        map.put("data", lists);

        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping("/uploadBP")
    public Object uploadBP(AndroidBprecordModel model) {
        Bprecord bprecord = new Bprecord();

        LocalDateTime localDateTime;
        double dbp, sbp, pulseRate;
        int patientId;

        try {
            localDateTime = LocalDateTime.parse(model.getCreateDatetime());
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "createDatetime形如2019-03-01T17:20:00");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        try {
            dbp = Double.parseDouble(model.getDbp());
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "dbp应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        try {
            sbp = Double.parseDouble(model.getSbp());
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "sbp应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        try {
            pulseRate = Double.parseDouble(model.getPulseRate());
        }catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "pulseRate应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        List<Patient> patients = patientDao.getPatientByPrcId(model.getAddPrcId());
        if (patients.size() < 1) {
            ResponseError error = new ResponseError("待定", "不存在addPrcId对应的病人");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        patientId = patients.get(0).getPatientId();

        double map = (dbp + sbp) / 2;

        bprecord.setCreateDatetime(localDateTime);
        bprecord.setDbp(dbp);
        bprecord.setSbp(sbp);
        bprecord.setMap(map);
        bprecord.setMeasState(model.getMeasState());
        bprecord.setPatientId(patientId);
        bprecord.setPulseRate(pulseRate);
        bprecord.setSymptom(model.getSymptom());

        bprecordDao.insertOne(bprecord);
        return responseBuilder.getJson();
    }

    @PostMapping("/changePw")
    public Object changePw(String prcId, String crtPw, String newPw) {
        List<Patient> patientList = patientDao.getPatientByPrcId(prcId);
        if (patientList.size() > 0) {
            Patient pt = patientList.get(0);
            if (crtPw == null || crtPw.equals(pt.getPassword())) {
                patientDao.updatePw(prcId, crtPw, newPw);
            }else {
                ResponseError error = new ResponseError("待定", "密码不对");
                responseBuilder.setError(error);
            }
        }else {
            ResponseError error = new ResponseError("待定", "当前用户不存在");
            responseBuilder.setError(error);
        }
        return responseBuilder.getJson();
    }
}

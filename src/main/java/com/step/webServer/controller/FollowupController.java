package com.step.webServer.controller;

import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Followup;
import com.step.webServer.domain.PracticerxFollowup;
import com.step.webServer.domain.RiskfactorFollowup;
import com.step.webServer.model.*;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.ResponseError;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping(value = "/followUp", produces = "application/json;charset=UTF-8")
public class FollowupController extends AbstractController {
    private final FollowupDao followupDao;
    private final UserDao userDao;
    private final MapFactory<String, Object> mapFactory;
    @Autowired
    HttpServletRequest request;

    public FollowupController(FollowupDao followupDao, UserDao userDao, MapFactory<String, Object> mapFactory) {
        this.followupDao = followupDao;
        this.userDao = userDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping("/medicalRecord")
    public Object getMedicalRecord(String followupId) {
        Map<String, Object> map = followupDao.medicalRecord(Integer.valueOf(followupId));
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping("/medicalRecord")
    @Transactional
    public Object putMedicalRecord(MedicalRecordModel medicalRecordModel) throws Exception {
        int userId = (int)request.getSession().getAttribute("userId");
        Followup followup = followupDao.followup(Integer.valueOf(medicalRecordModel.getPatientId()), LocalDate.parse(medicalRecordModel.getCreateDate()), userId);
        if (followup != null) {
            copyMedicalRecordModelToFollowup(medicalRecordModel, followup);
            int numUpdate = followupDao.updateFollowup(followup);
            if (numUpdate != 1) throw new Exception("更新条数不为1");
        }else {
            followup = new Followup();
            copyMedicalRecordModelToFollowup(medicalRecordModel, followup);
            followup.setCreateDate(LocalDate.parse(medicalRecordModel.getCreateDate()));
            followup.setPatientId(Integer.valueOf(medicalRecordModel.getPatientId()));
            followup.setUserId(userId);
            int numInsert = followupDao.insertFollowup(followup);
            if (numInsert != 1) throw new Exception("插入条数不为1");
        }
        return responseBuilder.getJson();
    }

    private void copyMedicalRecordModelToFollowup(MedicalRecordModel medicalRecordModel, Followup followup) {
        followup.setComplaint(medicalRecordModel.getComplaint());
        followup.setHistoryPresentIllness(medicalRecordModel.getHistoryPresentIllness());
        followup.setHistoryPastIllness(medicalRecordModel.getHistoryPastIllness());
        followup.setPersonalHistory(medicalRecordModel.getPersonalHistory());
        followup.setAllergicHistory(medicalRecordModel.getAllergicHistory());
        followup.setFamilyHistory(medicalRecordModel.getFamilyHistory());
    }

    @GetMapping("/exam")
    public Object getExam(String followupId){
        responseBuilder.setMap(
                followupDao.exam(Integer.valueOf(followupId))
        );
        return responseBuilder.getJson();
    }

    @PutMapping("/exam")
    @Transactional
    public Object putExam(ExamModel examModel) throws Exception {
        int userId = (int)request.getSession().getAttribute("userId");
        Followup followup = followupDao.followup(Integer.valueOf(examModel.getPatientId()), LocalDate.parse(examModel.getCreateDate()), userId);
        if (followup != null) {
            copyExamModelToFollowup(examModel, followup);
            int numUpdate = followupDao.updateFollowup(followup);
            if (numUpdate != 1) throw new Exception("更新条数不为1");
        }else {
            followup = new Followup();
            copyExamModelToFollowup(examModel, followup);
            followup.setCreateDate(LocalDate.parse(examModel.getCreateDate()));
            followup.setPatientId(Integer.valueOf(examModel.getPatientId()));
            followup.setUserId(userId);
            int numInsert = followupDao.insertFollowup(followup);
            if (numInsert != 1) throw new Exception("插入条数不为1");
        }
        return responseBuilder.getJson();
    }

    private void copyExamModelToFollowup(ExamModel examModel, Followup followup) {
        followup.setHeight(examModel.getHeight());
        followup.setWeight(examModel.getWeight());
        followup.setSbp(Double.valueOf(examModel.getSbp()));
        followup.setDbp(Double.valueOf(examModel.getDbp()));
        followup.setMeanbp(examModel.getMeanbp());
        followup.setHeartRate(examModel.getHeartRate());
        followup.setBodyTemp(examModel.getBodyTemp());
        followup.setBreathe(examModel.getBreathe());
        followup.setBloodSugar(examModel.getBloodSugar());
        followup.setPhysExam(examModel.getPhysExam());
        followup.setLabExam(examModel.getLabExam());
    }

    @GetMapping("/allDateAndFollowupIds")
    public Object allDateAndFollowupIds(String patientId) {
        if (StringUtils.isBlank(patientId)) {
            ResponseError error = new ResponseError("2", "必须输入patientId");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }
        int userId = (int)request.getSession().getAttribute("userId");
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("allDateAndFollowupIds", followupDao.allDateAndFollowupIds(patientId, userId));
        return responseBuilder.getJson();
    }

    @GetMapping("/riskfactorclass")
    public Object riskfactorclass() {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("riskfactorclasses", followupDao.riskfactorclass());
        return responseBuilder.getJson();
    }

    @GetMapping("/riskfactor")
    public Object riskfactor(String followupId, String riskfactorclassId) {
        int temp = Integer.valueOf(followupId);
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("riskfactors", followupDao.riskfactor(riskfactorclassId, temp));
        return responseBuilder.getJson();
    }

    @GetMapping("/riskfactor_new")
    public Object riskfactorNew(String riskfactorclassId) {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("riskfactors", followupDao.riskfactorNew(riskfactorclassId));
        return responseBuilder.getJson();
    }

    @PutMapping("/riskfactor")
    @Transactional
    public Object putRiskfactor(@RequestBody PutRiskfactorModel putRiskfactorModel) {
        String patientId = putRiskfactorModel.getPatientId();
        String createDate = putRiskfactorModel.getCreateDate();
        ArrayList<RiskfactorModel> riskfactorModels = putRiskfactorModel.getRiskfactors();
        int userId = (int)request.getSession().getAttribute("userId");
        int parsedPatientId;
        LocalDate parsedDate;

        try {
            parsedPatientId =  Integer.valueOf(patientId);
        } catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "patientId应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        try {
            parsedDate = LocalDate.parse(createDate);
        } catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "createDate应为yyyy-MM-dd");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        Followup followup = followupDao.followup(parsedPatientId, parsedDate, userId);
        if (followup == null) {
            followup = new Followup();
            followup.setPatientId(parsedPatientId);
            followup.setCreateDate(parsedDate);
            followup.setUserId(userId);
            followupDao.insertFollowup(followup);
        }
        for (RiskfactorModel rc :
                riskfactorModels) {
            RiskfactorFollowup riskfactorFollowup =
                    followupDao.riskfactorFollowups(rc.getRiskfactorId(), followup.getFollowupId());
            if (riskfactorFollowup != null) {
                followupDao.updateRiskfactorFollowup(rc.getRiskfactorId(), followup.getFollowupId(), rc.isChecked());
            }else {
                followupDao.insertRiskfactorFollowup(rc.getRiskfactorId(), followup.getFollowupId(), rc.isChecked());
            }
        }

        Map<String, Object> params = mapFactory.create();
        params.put("followupId", followup.getFollowupId());
        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }

    @GetMapping("/cvRisk")
    public Object cvRisk(String followupId) {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("cvRisk", "OK");
        return responseBuilder.getJson();
    }

    @GetMapping("/mgtPlan")
    public Object mgtPlan(String followupId) {
        responseBuilder.setMap(followupDao.mgtPlan(followupId));
        return responseBuilder.getJson();
    }

    @GetMapping("/practicerx")
    public Object practicerx(String followupId) {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("practiceRxes", followupDao.practicerx(Integer.valueOf(followupId)));
        return responseBuilder.getJson();
    }

    @GetMapping("/practicerx_new")
    public Object practicerxNew() {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("practiceRxes", followupDao.practicerxNew());
        return responseBuilder.getJson();
    }

    @PutMapping("/mgtPlan")
    public Object putMgtPlan(MgtPlanModel mgtPlanModel) throws Exception {
        int userId = (int)request.getSession().getAttribute("userId");
        Followup followup = followupDao.followup(
                Integer.valueOf(mgtPlanModel.getPatientId()),
                LocalDate.parse(mgtPlanModel.getCreateDate()),
                userId);
        if (followup != null) {
            copyMgtPlanModelToFollowup(mgtPlanModel, followup);
            followupDao.updateFollowup(followup);
        }else {
            followup = new Followup();
            copyMgtPlanModelToFollowup(mgtPlanModel, followup);
            followup.setCreateDate(LocalDate.parse(mgtPlanModel.getCreateDate()));
            followup.setPatientId(Integer.valueOf(mgtPlanModel.getPatientId()));
            followup.setUserId(userId);
            followupDao.insertFollowup(followup);
        }

        Map<String, Object> params = mapFactory.create();
        params.put("followupId", followup.getFollowupId());

        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }

    @PutMapping("/practicerx")
    public Object putPracticerx(@RequestBody PutPracticerxModel putPracticerxModel) {
        int userId = (int)request.getSession().getAttribute("userId");
        int parsedPatientId;
        LocalDate parsedDate;

        try {
            parsedPatientId =  Integer.valueOf(putPracticerxModel.getPatientId());
        } catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "patientId应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        try {
            parsedDate = LocalDate.parse(putPracticerxModel.getCreateDate());
        } catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "createDate应为yyyy-MM-dd");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        Followup followup = followupDao.followup(parsedPatientId, parsedDate, userId);

        if (followup == null) {
            followup = new Followup();
            followup.setUserId(userId);
            followup.setPatientId(parsedPatientId);
            followup.setCreateDate(parsedDate);

            followupDao.insertFollowup(followup);
        }

        for (PracticeRxModel rc:
                putPracticerxModel.getPracticeRxes()) {
            PracticerxFollowup practicerxFollowup = new PracticerxFollowup();
            practicerxFollowup.setFollowupId(followup.getFollowupId());
            practicerxFollowup.setPracticerxId(Integer.valueOf(rc.getPracticerxId()));
            practicerxFollowup.setChecked(rc.getChecked());
            practicerxFollowup.setNote(rc.getNote());
            if (followupDao.practicerxFollowup(practicerxFollowup) != null) {
                followupDao.updatePracticerxFollowup(practicerxFollowup);
            }else {
                followupDao.insertPracticerxFollowup(practicerxFollowup);
            }
        }

        Map<String, Object> params = mapFactory.create();
        params.put("followupId", followup.getFollowupId());

        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }

    private void copyMgtPlanModelToFollowup(MgtPlanModel mgtPlanModel, Followup followup) {
        followup.setDzDx(mgtPlanModel.getDzDx());
        followup.setSbpTarget(Double.valueOf(mgtPlanModel.getSbpTarget()));
        followup.setDbpTarget(Double.valueOf(mgtPlanModel.getDbpTarget()));
        followup.setMedicineRx(mgtPlanModel.getMedicineRx());
        followup.setReferral(mgtPlanModel.isReferral());
        followup.setNextDatetime(LocalDateTime.parse(mgtPlanModel.getNextDatetime()));
    }

    private int getUserId() {
        String username = (String) request.getSession().getAttribute("username");
        return userDao.selectByUsername(username).getUserId();
    }

    @GetMapping("/plan")
    public Object plan(String nextDate) {
        Map<String, Object> map = mapFactory.create();
        map.put("followups", followupDao.followupPlan(nextDate));
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping("/getFollowupId")
    public Object getFollowupId(String patientId, String date) {
        int userId = (int)request.getSession().getAttribute("userId");
        int parsedPatientId;
        LocalDate parsedDate;

        try {
            parsedPatientId =  Integer.valueOf(patientId);
        } catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "patientId应为数字");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        try {
            parsedDate = LocalDate.parse(date);
        } catch (Exception ex) {
            ResponseError error = new ResponseError("待定", "createDate应为yyyy-MM-dd");
            responseBuilder.setError(error);
            return responseBuilder.getJson();
        }

        Followup followup = followupDao.followup(parsedPatientId, parsedDate, userId);

        Map<String, Object> params = mapFactory.create();
        if (followup == null) {
            params.put("followupId", null);
        }else {
            params.put("followupId", followup.getFollowupId());
        }

        responseBuilder.setMap(params);

        return responseBuilder.getJson();
    }
}

package com.step.webServer.controller;

import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Followup;
import com.step.webServer.domain.PracticerxFollowup;
import com.step.webServer.domain.RiskfactorFollowup;
import com.step.webServer.model.*;
import com.step.webServer.util.MapFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
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

    @PutMapping("/medicalRecord")
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
        followup.setSbp(examModel.getSbp());
        followup.setDbp(examModel.getDbp());
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

    @PutMapping("/riskfactor")
    @Transactional
    public Object putRiskfactor(String followupId, List<RiskfactorModel> riskfactors) {
        int temp = Integer.valueOf(followupId);
        for (RiskfactorModel rc :
                riskfactors) {
            RiskfactorFollowup riskfactorFollowup =
                    followupDao.riskfactorFollowups(rc.getRiskfactorId(), temp);
            if (riskfactorFollowup != null) {
                followupDao.updateRiskfactorFollowup(rc.getRiskfactorId(), temp, rc.isChecked());
            }else {
                followupDao.insertRiskfactorFollowup(rc.getRiskfactorId(), temp, rc.isChecked());
            }
        }
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
        responseBuilder.setMap(followupDao.mgtPlan(Integer.valueOf(followupId)));
        return responseBuilder.getJson();
    }

    @GetMapping("/practicerx")
    public Object practicerx(String followupId) {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("practiceRxes", followupDao.practicerx(Integer.valueOf(followupId)));
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
            int numUpdate = followupDao.updateFollowup(followup);
            if (numUpdate != 1) throw new Exception("更新条数不为1");
        }else {
            followup = new Followup();
            copyMgtPlanModelToFollowup(mgtPlanModel, followup);
            followup.setCreateDate(LocalDate.parse(mgtPlanModel.getCreateDate()));
            followup.setPatientId(Integer.valueOf(mgtPlanModel.getPatientId()));
            followup.setUserId(userId);
            int numInsert = followupDao.insertFollowup(followup);
            if (numInsert != 1) throw new Exception("插入条数不为1");
        }
        return responseBuilder.getJson();
    }

    @PutMapping("/practicerx")
    public Object putPracticerx(String followupId, List<PracticeRxModel> practiceRxes) {
        int temp = Integer.valueOf(followupId);
        for (PracticeRxModel rc:
                practiceRxes) {
            PracticerxFollowup practicerxFollowup = new PracticerxFollowup();
            practicerxFollowup.setFollowupId(temp);
            practicerxFollowup.setPracticerxId(Integer.valueOf(rc.getPracticerxId()));
            practicerxFollowup.setChecked(rc.getChecked());
            practicerxFollowup.setNote(rc.getNote());
            if (followupDao.practicerxFollowup(practicerxFollowup) != null) {
                followupDao.updatePracticerxFollowup(practicerxFollowup);
            }else {
                followupDao.insertPracticerxFollowup(practicerxFollowup);
            }
        }
        return responseBuilder.getJson();
    }

    private void copyMgtPlanModelToFollowup(MgtPlanModel mgtPlanModel, Followup followup) {
        followup.setSbpTarget(mgtPlanModel.getSbpTarget());
        followup.setDbpTarget(mgtPlanModel.getDbpTarget());
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
}

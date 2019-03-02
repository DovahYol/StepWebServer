package com.step.webServer.controller;

import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Followup;
import com.step.webServer.model.MedicalRecordModel;
import com.step.webServer.security.UserPrincipal;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequestMapping(value = "/followUp", produces = "application/json;charset=UTF-8")
public class FollowupController extends AbstractController {
    private final FollowupDao followupDao;
    private final UserDao userDao;

    public FollowupController(FollowupDao followupDao, UserDao userDao) {
        this.followupDao = followupDao;
        this.userDao = userDao;
    }

    @GetMapping("/medicalRecord")
    public Object getMedicalRecord(String patientId, String createDate, Authentication authentication) {
        String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
        int userId = userDao.selectByUsername(username).getUserId();
        Map<String, Object> map = followupDao.medicalRecord(patientId, createDate, userId);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PutMapping("/medicalRecord")
    @Transactional
    public Object putMedicalRecord(MedicalRecordModel medicalRecordModel, Authentication authentication) throws Exception {
        String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
        int userId = userDao.selectByUsername(username).getUserId();
        Followup followup = followupDao.followup(medicalRecordModel.getPatientId(), medicalRecordModel.getCreateDate(), userId);
        if (followup != null) {
            copyMedicalRecordModelToFollowup(medicalRecordModel, followup);
            int numUpdate = followupDao.updateFollowup(followup);
            if (numUpdate != 1) throw new Exception("更新条数不为1");
        }else {
            followup = new Followup();
            copyMedicalRecordModelToFollowup(medicalRecordModel, followup);
            followup.setCreateDate(LocalDate.parse(medicalRecordModel.getCreateDate()));
            int numInsert = followupDao.insertFollowup(followup);
            if (numInsert != 1) throw new Exception("插入条数不为1");
        }
        return responseBuilder.getJson();
    }

    @GetMapping("/exam")
    public Object getExam(String patientId, String createDate, Authentication authentication){
        String username = ((UserPrincipal)authentication.getPrincipal()).getUsername();
        int userId = userDao.selectByUsername(username).getUserId();
        responseBuilder.setMap(
                followupDao.exam(patientId, createDate, userId)
        );
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
}

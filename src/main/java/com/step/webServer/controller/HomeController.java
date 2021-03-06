package com.step.webServer.controller;

import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.PatientDao;
import com.step.webServer.model.NameValueModel;
import com.step.webServer.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/home", produces = "application/json;charset=UTF-8")
public class HomeController extends AbstractController{

    private final PatientDao patientDao;
    private final FollowupDao followupDao;

    @Autowired
    HttpServletRequest request;

    public HomeController(PatientDao patientDao, FollowupDao followupDao) {
        this.patientDao = patientDao;
        this.followupDao = followupDao;
    }

    @GetMapping()
    @Transactional
    public Object home() {
        String username = (String) request.getSession().getAttribute(SessionUtil.USERNAME);
        int userId = (int) request.getSession().getAttribute(SessionUtil.USER_ID);
        int roleId = (int) request.getSession().getAttribute(SessionUtil.ROLE_ID);

        int numPatients = patientDao.numPatientsByUsername(userId, roleId);
        int numNewPatients = patientDao.numNewPatientsByUsername(userId, roleId);
        int numPatientsWithInvalidBp = patientDao.numPatientsWithInvalidBpByUsername(userId, roleId);
        int numNewPatientsNotTested = patientDao.numNewPatientsNotTestedByUsername(userId, roleId);
        Map<String, Object> map = new HashMap<>();
        map.put("numPt", numPatients);
        map.put("numNewPt", numNewPatients);
        map.put("numInvalidBP", numPatientsWithInvalidBp);
        map.put("numNewPtNotTested", numNewPatientsNotTested);
        List<NameValueModel<String, Long>> tableData = new ArrayList<>();
        Map<String, Object> patientMetric = followupDao.getValidAndInvalidPatients(userId, roleId);
        tableData.add(new NameValueModel<>("已达标", (long)patientMetric.get("numValid")));
        tableData.add(new NameValueModel<>("未达标", (long)patientMetric.get("numInValid")));
        map.put("tableData", tableData);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }
}

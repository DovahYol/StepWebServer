package com.step.webServer.controller;

import com.step.webServer.dao.PatientDao;
import com.step.webServer.model.NameValueModel;
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

    private PatientDao patientDao;
    @Autowired
    HttpServletRequest request;

    public HomeController(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @GetMapping()
    @Transactional
    public Object home() {
        String username = (String) request.getSession().getAttribute("username");
        int numPatients = patientDao.numPatientsByUsername(username);
        int numNewPatients = patientDao.numNewPatientsByUsername(username);
        int numPatientsWithInvalidBp = patientDao.numPatientsWithInvalidBpByUsername(username);
        int numNewPatientsNotTested = patientDao.numNewPatientsNotTestedByUsername(username);
        Map<String, Object> map = new HashMap<>();
        map.put("numPt", numPatients);
        map.put("numNewPt", numNewPatients);
        map.put("numInvalidBP", numPatientsWithInvalidBp);
        map.put("numNewPtNotTested", numNewPatientsNotTested);
        List<NameValueModel<String, Integer>> tableData = new ArrayList<>();
        tableData.add(new NameValueModel<>("已达标", numPatients - numPatientsWithInvalidBp));
        tableData.add(new NameValueModel<>("未达标", numPatientsWithInvalidBp));
        map.put("tableData", tableData);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }
}

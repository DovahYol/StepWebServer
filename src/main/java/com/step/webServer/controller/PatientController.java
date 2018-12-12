package com.step.webServer.controller;

import com.step.webServer.dao.PatientDao;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/patient", produces = "application/json;charset=UTF-8")
public class PatientController {
    private PatientDao patientDao;

    public PatientController(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

//    @GetMapping
//    public Object getAllPatients(String keyword, int pageNum, int pageSize, String orderBy, boolean isAsc){
//        String username = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        String order = isAsc? "asc" : "desc";
//        patientDao.selectAllPatients(username, pageNum, pageSize, );
//    }
}

package com.step.webServer.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.step.webServer.dao.BprecordDao;
import com.step.webServer.dao.FollowupDao;
import com.step.webServer.dao.PatientDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Followup;
import com.step.webServer.domain.Patient;
import com.step.webServer.model.PatientAddingModel;
import com.step.webServer.model.PatientUpdateModel;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.ResponseErrorFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.transaction.annotation.Transactional;
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
    public Object getAllPatients(String keyword, int pageNum, int pageSize, String orderBy, boolean isAsc, String queryType) throws JsonProcessingException {
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
        map.put("queryType", queryType);
        List<Object> ret = patientDao.selectAllPatients(pageNum, pageSize, orderBy, map);
        Map<String, Object> responseMap = mapFactory.create();
        responseMap.put("patients", ret.get(0));
        responseMap.put("count", ret.get(1));
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
            LocalDate localDate = LocalDate.parse(birthDay);
            patient.setBirthday(localDate);
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
        Patient patient = patientDao.getPatientById(patientId);

        Map<String, Object> map = mapFactory.create();

        if (patient != null) {
            double maxSbp = patient.getMaxSbp();
            double maxDbp = patient.getMaxDbp();
            if (maxSbp >= 180 || maxDbp >= 110) {
                map.put("bpLevel", "3级");
            } else if (maxSbp >= 160 || maxDbp >= 100) {
                map.put("bpLevel", "2级");
            } else if (maxSbp >= 140 || maxDbp >= 90) {
                map.put("bpLevel", "1级");
            } else{
                map.put("bpLevel", "正常");
            }

            Followup followup = followupDao.getPatientsLatestFollowup(patientId);
            if (followup != null) {
                if (followup.getDbp() <= followup.getDbpTarget()
                        && followup.getSbp() <= followup.getSbpTarget()) {
                    map.put("isValid", "达标");
                } else {
                    map.put("isValid", "未达标");
                }

                map.put("followUpDatetime", followup.getNextDatetime());

                map.put("currentMgtPlan", followup.getMedicineRx());

                map.put("riskLevel", getRiskLevel(followup, maxSbp, maxDbp));
            }

            LocalDate hbpDxDate = patient.getHbpDxDate();
            if (hbpDxDate != null) {
                long daysBetween = DAYS.between(hbpDxDate, LocalDate.now());
                map.put("bpPeriod", daysBetween + "天");
            }

            LocalDateTime localDateTime = patient.getCreateDatetime();
            if (localDateTime != null) {
                long daysBetween = DAYS.between(localDateTime.toLocalDate(), LocalDate.now());
                map.put("stepPeriod", daysBetween + "天");
            }
        }

        responseBuilder.setMap(map);

        return responseBuilder.getJson();
    }

    public String getRiskLevel(Followup followup, double maxSbp, double maxDbp) {
        String res = "正常";
        List<Map<String, Long>> maps = patientDao.getRiskFactorClassCount(followup.getFollowupId());
        Map<Long, Long> riskFactorClassMap = new HashMap<>();
        for (Map<String, Long> item: maps) {
            riskFactorClassMap.put(item.get("riskfactorclassId"), item.get("num"));
        }

        if (riskFactorClassMap.get(3L) > 0) {
            res = "很高危";
        } else if (riskFactorClassMap.get(2L) > 0){
            if (maxSbp >= 180 || maxDbp >= 110) {
                res = "很高危";
            }else {
                res = "高危";
            }
        } else if (riskFactorClassMap.get(1L) >= 3) {
            if (maxSbp >= 140 || maxDbp >= 90) {
                res = "高危";
            } else if (maxSbp >= 130 || maxDbp >= 85) {
                res = "中危";
            }
        } else if (riskFactorClassMap.get(1L) >= 1) {
            if (maxSbp >= 160 || maxDbp >= 100) {
                res = "高危";
            } else if (maxSbp >= 140 || maxDbp >= 90) {
                res = "中危";
            } else if (maxSbp >= 130 || maxDbp >= 85) {
                res = "低危";
            }
        } else {
            if (maxSbp >= 180 || maxDbp >= 110) {
                res = "高危";
            } else if (maxSbp >= 160 || maxDbp >= 100) {
                res = "中危";
            } else if (maxSbp >= 130 || maxDbp >= 85) {
                res = "低危";
            }
        }

        return res;
    }

    //未达标次数（numInvalid），达标率（validRate）未实现
    @GetMapping("/bpAnalysis")
    public Object getBpAnalysis(String patientId) {
        Followup fl = followupDao.getPatientsLatestFollowup(patientId);
        double sbpTarget, dbpTarget;
        if (fl == null || fl.getDbpTarget() == 0 || fl.getSbpTarget() == 0) {
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

    @GetMapping("/generalInfo")
    public Object generalInfo(String patientId) {
        Patient patient = patientDao.getPatientById(patientId);
        responseBuilder.setMap(beanToMap(patient));
        return responseBuilder.getJson();
    }

    @PostMapping("/updatePatient")
    public Object updatePatient(PatientUpdateModel patient) {
        patientDao.updatePatient(patient);
        return responseBuilder.getJson();
    }

    private static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key+"", beanMap.get(key));
            }
        }
        return map;
    }
}

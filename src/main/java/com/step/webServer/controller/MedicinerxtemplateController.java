package com.step.webServer.controller;


import com.step.webServer.dao.MedicinerxtemplateDao;
import com.step.webServer.domain.Medicinerxtemplate;
import com.step.webServer.util.MapFactory;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/medicinerxtemplate", produces = "application/json;charset=UTF-8")
public class MedicinerxtemplateController extends AbstractController {

    private final MedicinerxtemplateDao medicinerxtemplateDao;
    private final MapFactory<String, Object> mapFactory;

    public MedicinerxtemplateController(MedicinerxtemplateDao medicinerxtemplateDao, MapFactory<String, Object> mapFactory) {
        this.medicinerxtemplateDao = medicinerxtemplateDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping
    public Object allMedicinerxtemplates() {
        Map<String, Object> map = mapFactory.create();
        responseBuilder.setMap(map);
        map.put("contents", medicinerxtemplateDao.allMedicinerxtemplates());
        return responseBuilder.getJson();
    }

    @PostMapping
    public Object putMedicinerxtemplate(Medicinerxtemplate medicinerxtemplate) {
        Integer temp = medicinerxtemplate.getMedicinerxtemplateId();
        if (temp == null) {
            medicinerxtemplateDao.insertMedicinerxtemplate(medicinerxtemplate);
        }else {
            medicinerxtemplateDao.updateMedicinerxtemplate(medicinerxtemplate);
        }
        return responseBuilder.getJson();
    }

    @DeleteMapping
    public Object deleteMedicinerxtemplate(int medicinerxtemplateId) throws Exception {
        medicinerxtemplateDao.deleteMedicinerxtemplate(medicinerxtemplateId);
        return responseBuilder.getJson();
    }
}

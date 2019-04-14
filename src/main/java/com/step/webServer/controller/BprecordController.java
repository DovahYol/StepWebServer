package com.step.webServer.controller;

import com.step.webServer.dao.BprecordDao;
import com.step.webServer.domain.Bprecord;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/bprecord", produces = "application/json;charset=UTF-8")
public class BprecordController extends AbstractController {

    private final BprecordDao bprecordDao;

    public BprecordController(BprecordDao bprecordDao) {
        this.bprecordDao = bprecordDao;
    }


    @PostMapping
    public Object postBprecord(Bprecord bprecord) {
        bprecord.setCreateDatetime(LocalDateTime.now());
        bprecordDao.insertOne(bprecord);
        return responseBuilder.getJson();
    }

}

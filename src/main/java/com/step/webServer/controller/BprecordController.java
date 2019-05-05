package com.step.webServer.controller;

import com.step.webServer.dao.BprecordDao;
import com.step.webServer.domain.Bprecord;
import com.step.webServer.util.ResponseError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/record", produces = "application/json;charset=UTF-8")
public class BprecordController extends AbstractController {

    private final BprecordDao bprecordDao;

    public BprecordController(BprecordDao bprecordDao) {
        this.bprecordDao = bprecordDao;
    }

    @PostMapping
    public Object postBprecord(Bprecord bprecord) {
        LocalDateTime localDateTime;
        try {
            localDateTime = LocalDateTime.parse(bprecord.getMeasureDatetime());
        }catch (Exception ex) {
            ResponseError responseError = new ResponseError("待定", "时间格式应为yyyy-MM-dd'T'HH:mm:ss");
            responseBuilder.setError(responseError);
            return responseBuilder.getJson();
        }
        bprecord.setCreateDatetime(localDateTime);
        bprecordDao.insertOne(bprecord);
        return responseBuilder.getJson();
    }

}

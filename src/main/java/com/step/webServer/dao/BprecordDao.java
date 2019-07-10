package com.step.webServer.dao;

import com.step.webServer.domain.Bprecord;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BprecordDao implements Dao{
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public BprecordDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public Map<String, Object> bpOverview(String patientId, double sbpTarget, double dbpTarget) {
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patientId);
        params.put("sbpTarget", sbpTarget);
        params.put("dbpTarget", dbpTarget);

        return this.sqlSession.selectOne("bpOverview", params);
    }

    public List<Bprecord> bpRecords(String patientId) {
        return this.sqlSession.selectList("bpRecords", patientId);
    }

    public int insertOne(Bprecord bprecord) {
        return this.sqlSession.insert("insertOneBprecord", bprecord);
    }

    public List<Map<String, Object>> getBpRecordsByPrcId(String prcId) {
        return this.sqlSession.selectList("getBpRecordsByPrcId", prcId);
    }

}

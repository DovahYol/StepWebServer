package com.step.webServer.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BprecordDao implements Dao{
    private final SqlSession sqlSession;

    public BprecordDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public Map<String, Object> bpOverview(String patientId) {
        return this.sqlSession.selectOne("bpOverview", patientId);
    }

    public List<Map<String, Object>> bpRecords(String patientId) {
        return this.sqlSession.selectList("bpRecords", patientId);
    }

}

package com.step.webServer.dao;

import com.step.webServer.domain.Followup;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class FollowupDao implements Dao {
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public FollowupDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public Followup followup(String patientId, String createDate, int userId) {
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patientId);
        params.put("createDate", createDate);
        params.put("userId", userId);
        return this.sqlSession.selectOne("followup", params);
    }

    public int updateFollowup(Followup followup) {
        return this.sqlSession.update("updateSingleFollowup", followup);
    }

    public Map<String, Object> medicalRecord(String patientId, String createDate, int userId) {
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patientId);
        params.put("createDate", createDate);
        params.put("userId", userId);
        return this.sqlSession.selectOne("medicalRecord", params);
    }

    public int insertFollowup(Followup followup) {
        return this.sqlSession.insert("insertSingleFollowup", followup);
    }

    public Map<String, Object> exam(String patientId, String createDate, int userId) {
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patientId);
        params.put("createDate", createDate);
        params.put("userId", userId);
        return this.sqlSession.selectOne("exam", params);
    }
}

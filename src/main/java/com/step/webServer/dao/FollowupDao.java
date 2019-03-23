package com.step.webServer.dao;

import com.step.webServer.domain.Followup;
import com.step.webServer.domain.PracticerxFollowup;
import com.step.webServer.domain.RiskfactorFollowup;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class FollowupDao implements Dao {
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public FollowupDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public Followup followup(int patientId, LocalDate createDate, int userId) {
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patientId);
        params.put("createDate", createDate);
        params.put("userId", userId);
        return this.sqlSession.selectOne("followup", params);
    }

    public int updateFollowup(Followup followup) {
        return this.sqlSession.update("updateSingleFollowup", followup);
    }

    public Map<String, Object> medicalRecord(int followupId) {
        return this.sqlSession.selectOne("medicalRecord", followupId);
    }

    public int insertFollowup(Followup followup) {
        return this.sqlSession.insert("insertSingleFollowup", followup);
    }

    public Map<String, Object> exam(int followupId) {
        return this.sqlSession.selectOne("exam", followupId);
    }

    public List<Map<String, Object>> allDateAndFollowupIds(String patientId, int userId) {
        Map<String, Object> params = mapFactory.create();
        params.put("patientId", patientId);
        params.put("userId", userId);
        return this.sqlSession.selectList("allDateAndFollowupIds", params);
    }

    public List<Map> riskfactorclass() {
        return this.sqlSession.selectList("riskfactorclass");
    }

    public List<Map> riskfactor(String riskfactorclassId, int followupId) {
        Map<String, Object> params = mapFactory.create();
        params.put("riskfactorclassId", riskfactorclassId);
        params.put("followupId", followupId);
        return this.sqlSession.selectList("riskfactor", params);
    }

    public RiskfactorFollowup riskfactorFollowups (String riskfactorId, int followupId) {
        Map<String, Object> params = mapFactory.create();
        params.put("riskfactorId", riskfactorId);
        params.put("followupId", followupId);
        return this.sqlSession.selectOne("riskfactorFollowup", params);
    }

    public int insertRiskfactorFollowup (String riskfactorId, int followupId, boolean checked) {
        Map<String, Object> params = mapFactory.create();
        params.put("riskfactorId", riskfactorId);
        params.put("followupId", followupId);
        params.put("checked", checked);
        return this.sqlSession.insert("insertRiskfactorFollowup", params);
    }

    public int updateRiskfactorFollowup (String riskfactorId, int followupId, boolean checked) {
        Map<String, Object> params = mapFactory.create();
        params.put("riskfactorId", riskfactorId);
        params.put("followupId", followupId);
        params.put("checked", checked);
        return this.sqlSession.insert("updateRiskfactorFollowup", params);
    }

    public Map<String, Object> mgtPlan (int followupId) {
        return this.sqlSession.selectOne("mgtPlan", followupId);
    }

    public List<Map<String, Object>> practicerx (int followupId) {
        return this.sqlSession.selectList("practicerx", followupId);
    }

    public PracticerxFollowup practicerxFollowup (PracticerxFollowup practicerxFollowup) {
        return this.sqlSession.selectOne("practicerxFollowup", practicerxFollowup);
    }

    public int updatePracticerxFollowup (PracticerxFollowup practicerxFollowup) {
        return this.sqlSession.update("updatePracticerxFollowup", practicerxFollowup);
    }

    public int insertPracticerxFollowup (PracticerxFollowup practicerxFollowup) {
        return this.sqlSession.insert("insertPracticerxFollowup", practicerxFollowup);
    }

    public List<Map<String, Object>> followupPlan(String nextDate) {
        return this.sqlSession.selectList("followupPlan", nextDate);
    }
}

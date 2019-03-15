package com.step.webServer.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QuestionnaireDao implements Dao{
    private final SqlSession sqlSession;

    public QuestionnaireDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Map<String, Object>> questionnairesByPatientId(Integer patientId) {
        return this.sqlSession
                .selectList("questionnairesByPatientId", patientId);
    }
}

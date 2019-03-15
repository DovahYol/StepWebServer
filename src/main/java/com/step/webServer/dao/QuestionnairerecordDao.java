package com.step.webServer.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class QuestionnairerecordDao implements Dao{
    private final SqlSession sqlSession;

    public QuestionnairerecordDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Map<String, Object>> questionnairerecordsByQuestionnairePatientId
            (int questionnairePatientId) {
        return this.sqlSession.selectList("questionnairerecordsByQuestionnairePatientId", questionnairePatientId);
    }
}

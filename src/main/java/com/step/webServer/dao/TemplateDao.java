package com.step.webServer.dao;

import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TemplateDao implements Dao {
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public TemplateDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public List<Map<String, Object>> templatesByType(String templateType) {
        return this.sqlSession.selectList("templatesByType", templateType);
    }

    public int deleteTemplatesById(String templateId) {
        return this.sqlSession.delete("deleteTemplatesById", templateId);
    }

    public int insertTemplate(String templateContent, String templateType) {
        Map<String, Object> params = mapFactory.create();
        params.put("templateContent", templateContent);
        params.put("templateType", templateType);
        return this.sqlSession.insert("insertTemplate", params);
    }
}

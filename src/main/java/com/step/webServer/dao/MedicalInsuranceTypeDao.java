package com.step.webServer.dao;

import com.step.webServer.domain.MedicalInsuranceType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalInsuranceTypeDao implements Dao{
    private final SqlSession sqlSession;

    public MedicalInsuranceTypeDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<MedicalInsuranceType> getAll() {
        return sqlSession.selectList("selectAllMedicalInsuranceTypes");
    }
}

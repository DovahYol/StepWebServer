package com.step.webServer.dao;

import com.step.webServer.domain.Hospital;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HospitalDao {
    private final SqlSession sqlSession;

    public HospitalDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Hospital> selectAll() {
        return sqlSession.selectList("selectAllHospitals");
    }

    public int insertOne(Hospital hospital) {
        return sqlSession.insert("selectOneHospital", hospital);
    }
}

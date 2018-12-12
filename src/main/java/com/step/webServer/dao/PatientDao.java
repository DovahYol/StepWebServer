package com.step.webServer.dao;

import com.github.pagehelper.PageHelper;
import com.step.webServer.domain.Patient;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PatientDao implements Dao{
    private final SqlSession sqlSession;

    public PatientDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int numPatientsByUsername(String username){
        return (int)sqlSession.selectList("numPatientsByUsername", username).get(0);
    }

    public int numNewPatientsByUsername(String username){
        return (int)sqlSession.selectList("numNewPatientsByUsername", username).get(0);
    }

    public int numPatientsWithInvalidBpByUsername(String username){
        return (int)sqlSession.selectList("numPatientsWithInvalidBpByUsername", username).get(0);
    }

    public int numNewPatientsNotTestedByUsername(String username){
        return (int)sqlSession.selectList("numNewPatientsNotTestedByUsername", username).get(0);
    }

    public List<Patient> selectAllPatients(String username, int pageNum, int pageSize, String orderBy){
        PageHelper.startPage(pageNum, pageSize, orderBy);
        List<Patient> patients = sqlSession.selectList("selectAllPatients", username);
        return patients;
    }


}

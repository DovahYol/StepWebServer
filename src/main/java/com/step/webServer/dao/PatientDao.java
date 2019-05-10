package com.step.webServer.dao;

import com.github.pagehelper.PageHelper;
import com.step.webServer.domain.Patient;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    public List<Map<String, Object>> selectAllPatients(int pageNum, int pageSize, String orderBy, Map<String, Object> parameter){
        if (orderBy != null) {
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        else {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<Map<String, Object>> patients = sqlSession.selectList("selectAllPatients", parameter);
        for (int i = 0; i < patients.size(); i++) {
            patients.get(i).put("no", i + 1);
        }
        return patients;
    }

    public boolean hasPatient(String patientName) {
        return this.sqlSession.<Integer>selectOne("selectPatientId", patientName) != null;
    }

    public int insertPatient(Patient patient) {
        return this.sqlSession.insert("insertPatient", patient);
    }

    public Map<String, Object> getPatientMeta(String patientId) {
        return this.sqlSession.selectOne("patientMeta", patientId);
    }

    public Map<String, Object> getPatientOverview() {
        return this.sqlSession.selectOne("patientOverview");
    }

    public Map<String, Object> courserecord(Integer patientId) {
        return this.sqlSession.selectOne("courserecord", patientId);
    }

    public int getPatientNumByAdminId(Integer adminId) {
        return sqlSession.selectOne("getPatientNumByAdminId", adminId);
    }

    public List<Integer> getPatientIdsByTeamId(Integer teamId) {
        return sqlSession.selectList("getPatientIdsByTeamId", teamId);
    }

}

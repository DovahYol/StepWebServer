package com.step.webServer.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.step.webServer.domain.Patient;
import com.step.webServer.model.PatientUpdateModel;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Arrays;
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

    public List<Object> selectAllPatients(int pageNum, int pageSize, String orderBy, Map<String, Object> parameter){
        Page page;
        if (orderBy != null) {
            page = PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        else {
            page = PageHelper.startPage(pageNum, pageSize);
        }

        List<Map<String, Object>> patients = sqlSession.selectList("selectAllPatients", parameter);
        for (int i = 0; i < patients.size(); i++) {
            patients.get(i).put("no", i + 1);
        }

        PageInfo info = new PageInfo<>(page.getResult());

        return Arrays.asList(patients, info.getTotal());
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

    public Patient getPatientById(String patientId) {
        return sqlSession.selectOne("getPatientById", patientId);
    }

    public List<Map<String, Long>> getRiskFactorClassCount(long followupId){
        return sqlSession.selectList("getRiskFactorClassCount", followupId);
    }

    public int updatePatient(PatientUpdateModel patient) {
        return sqlSession.update("updatePatient", patient);
    }
}

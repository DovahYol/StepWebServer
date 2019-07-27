package com.step.webServer.dao;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.step.webServer.domain.Patient;
import com.step.webServer.model.PatientUpdateModel;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PatientDao implements Dao{
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public PatientDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public int numPatientsByUsername(int userId, int roleId){
        Map<String, Object> map = mapFactory.create();
        map.put("userId", userId);
        map.put("roleId", roleId);

        return (int)sqlSession.selectList("numPatientsByUsername", map).get(0);
    }

    public int numNewPatientsByUsername(int userId, int roleId){
        Map<String, Object> map = mapFactory.create();
        map.put("userId", userId);
        map.put("roleId", roleId);

        return (int)sqlSession.selectList("numNewPatientsByUsername", map).get(0);
    }

    public int numPatientsWithInvalidBpByUsername(int userId, int roleId){
        Map<String, Object> map = mapFactory.create();
        map.put("userId", userId);
        map.put("roleId", roleId);

        return (int)sqlSession.selectList("numPatientsWithInvalidBpByUsername", map).get(0);
    }

    public int numNewPatientsNotTestedByUsername(int userId, int roleId){
        Map<String, Object> map = mapFactory.create();
        map.put("userId", userId);
        map.put("roleId", roleId);

        return (int)sqlSession.selectList("numNewPatientsNotTestedByUsername", map).get(0);
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

    public List<Patient> getPatientByPrcId(String prcId) {
        return sqlSession.selectList("getPatientByPrcId", prcId);
    }

    public List<Map<String, Long>> getRiskFactorClassCount(long followupId){
        return sqlSession.selectList("getRiskFactorClassCount", followupId);
    }

    public int updatePatient(PatientUpdateModel patient) {
        return sqlSession.update("updatePatient", patient);
    }

    public int updatePw(String prcId, String crtPw, String newPw) {
        Map<String, Object> params = new HashMap<>();
        params.put("prcId", prcId);
        params.put("crtPw", crtPw);
        params.put("newPw", newPw);

        return sqlSession.update("updatePw", params);
    }
}

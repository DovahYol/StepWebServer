package com.step.webServer.dao;

import com.step.webServer.domain.Medicinerxtemplate;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicinerxtemplateDao implements Dao{
    private final SqlSession sqlSession;

    public MedicinerxtemplateDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Medicinerxtemplate> allMedicinerxtemplates() {
        return sqlSession.selectList("allMedicinerxtemplates");
    }

    public int updateMedicinerxtemplate(Medicinerxtemplate medicinerxtemplate) {
        return sqlSession.update("updateMedicinerxtemplate", medicinerxtemplate);
    }

    public int insertMedicinerxtemplate(Medicinerxtemplate medicinerxtemplate) {
        return sqlSession.insert("insertMedicinerxtemplate", medicinerxtemplate);
    }

    public int deleteMedicinerxtemplate(int medicinerxtemplateId) {
        return sqlSession.delete("deleteMedicinerxtemplate", medicinerxtemplateId);
    }
}

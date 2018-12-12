package com.step.webServer.dao;

import com.step.webServer.domain.Role;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleDao implements Dao {
    private final SqlSession sqlSession;

    public RoleDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<Role> selectAllEmployees() {
        return this.sqlSession.selectList("selectAllEmployees");
    }

}

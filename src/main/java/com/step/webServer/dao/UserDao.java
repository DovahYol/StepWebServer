package com.step.webServer.dao;

import com.step.webServer.domain.ApplicationUser;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDao implements Dao{
    private final SqlSession sqlSession;

    public UserDao(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<String> getSidebarByUsername(String username) {
        return this.sqlSession.selectList("selectSidebarContents", username);
    }

    public ApplicationUser selectByUsername(String username) {
        return this.sqlSession.selectOne("selectByUsername", username);
    }

    public int insertOne(ApplicationUser user) {
        return this.sqlSession.insert("insertOne", user);
    }
}

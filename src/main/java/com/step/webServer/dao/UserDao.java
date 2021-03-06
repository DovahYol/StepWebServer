package com.step.webServer.dao;

import com.step.webServer.domain.ApplicationUser;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserDao implements Dao{
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public UserDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public List<String> getSidebarByUsername(String username) {
        return this.sqlSession.selectList("selectSidebarContents", username);
    }

    public ApplicationUser selectByUsername(String username) {
        return this.sqlSession.selectOne("selectByUsername", username);
    }

    public ApplicationUser selectByPrcId(String prcId) {
        return this.sqlSession.selectOne("selectByPrcId", prcId);
    }

    public int insertOne(ApplicationUser user) {
        return this.sqlSession.insert("insertOne", user);
    }

    public boolean containsUser(String username) {
        return this.sqlSession.selectList("selectUserIdByUsername", username).size() != 0;
    }

    public Integer selectTeamIdByUsername(String username) {
        return this.sqlSession.selectOne("selectTeamIdByUsername", username);
    }

    public ApplicationUser selectByUserId(int userId) {
        return this.sqlSession.selectOne("selectByUserId", userId);
    }

    public int updateOne(ApplicationUser applicationUser) {
        return this.sqlSession.update("updateOne", applicationUser);
    }

    public List<Map<String, Object>> doctorsAvailable(int userId, int roleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", roleId);
        return this.sqlSession.selectList("doctorsAvailable", params);
    }

    public List<Map<String, Object>> nursesAvailable(int userId, int roleId) {
        Map<String, Object> params = new HashMap<>();
        params.put("userId", userId);
        params.put("roleId", roleId);
        return this.sqlSession.selectList("nursesAvailable", params);
    }

    public List<Map<String, Object>> getUnconfirmed(Integer userId) {
        return this.sqlSession.selectList("getUnconfirmed", userId);
    }

    public int updateConfirmed(boolean confirmed, int userId) {
        Map<String, Object> params = mapFactory.create();
        params.put("confirmed", confirmed);
        params.put("userId", userId);
        return this.sqlSession.update("updateConfirmed", params);
    }

    public int insertOrUpdateOne(ApplicationUser user) {
        return this.sqlSession.update("insertOrUpdateOneUser", user);
    }

    public Integer getAdminIdByUserId(int userId) {
        return this.sqlSession.selectOne("getAdminIdByUserId", userId);
    }
}

package com.step.webServer.dao;

import com.github.pagehelper.PageHelper;
import com.step.webServer.domain.Team;
import com.step.webServer.util.MapFactory;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class TeamDao implements Dao  {
    private final SqlSession sqlSession;
    private final MapFactory<String, Object> mapFactory;

    public TeamDao(SqlSession sqlSession, MapFactory<String, Object> mapFactory) {
        this.sqlSession = sqlSession;
        this.mapFactory = mapFactory;
    }

    public Team teamById(int teamId) {
        return this.sqlSession.selectOne("teamById", teamId);
    }

    public List<Map<String, Object>> membersByTeamId(int teamId) {
        return this.sqlSession.selectList("membersByTeamId", teamId);
    }

    public List<Map<String, Object>> teams(int pageNum, int pageSize, String orderBy, String keyword) {
        if (orderBy != null) {
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
        else {
            PageHelper.startPage(pageNum, pageSize);
        }

        List<Map<String, Object>> teams = sqlSession.selectList("teams", keyword);
        for (int i = 0; i < teams.size(); i++) {
            Map<String , Object> team = teams.get(i);
            int teamId = (int) teams.get(i).get("teamId");
            team.put("no", i + 1);
            team.put("doctorNames", doctors(teamId));
            team.put("nurseNames", nurses(teamId));
        }
        return teams;
    }

    public List<String> doctors(int teamId) {
        return sqlSession.selectList("doctorsByTeamId", teamId);
    }

    public List<String> nurses(int teamId) {
        return sqlSession.selectList("nursesByTeamId", teamId);
    }

    public int addMember(int teamId, int userId) {
        Map<String, Object> map = mapFactory.create();
        map.put("teamId", teamId);
        map.put("userId", userId);
        return sqlSession.update("addMember", map);
    }

    public int deleteMember(int userId) {
        return sqlSession.update("addMember", userId);
    }

    public int insertTeam(String teamName, int adminId) {
        Map<String, Object> params = mapFactory.create();
        params.put("teamName", teamName);
        params.put("adminId", adminId);
        return sqlSession.insert("insertTeam", params);
    }

    public int deleteTeam(int teamId) {
        Map<String, Object> params = mapFactory.create();
        params.put("teamId", teamId);
        return sqlSession.insert("deleteTeam", params);
    }
}

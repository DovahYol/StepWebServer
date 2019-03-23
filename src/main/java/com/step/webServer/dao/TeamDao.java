package com.step.webServer.dao;

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
}

package com.step.webServer.controller;

import com.step.webServer.dao.TeamDao;
import com.step.webServer.domain.Team;
import com.step.webServer.util.MapFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(value = "/team", produces = "application/json;charset=UTF-8")
public class TeamController extends AbstractController  {
    private final TeamDao teamDao;
    private final MapFactory<String, Object> mapFactory;

    public TeamController(TeamDao teamDao, MapFactory<String, Object> mapFactory) {
        this.teamDao = teamDao;
        this.mapFactory = mapFactory;
    }

    @GetMapping("/name")
    public Object teamById(String teamId) {
        Team team = teamDao.teamById(Integer.valueOf(teamId));
        Map<String, Object> map = mapFactory.create();
        map.put("teamName", team.getTeamName());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping("/members")
    public Object membersByTeamId(String teamId) {
        Map<String, Object> map = mapFactory.create();
        map.put("members", teamDao.membersByTeamId(Integer.valueOf(teamId)));
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }
}

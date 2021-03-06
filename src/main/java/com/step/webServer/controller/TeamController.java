package com.step.webServer.controller;

import com.step.webServer.dao.PatientDao;
import com.step.webServer.dao.TeamDao;
import com.step.webServer.dao.UserDao;
import com.step.webServer.domain.Team;
import com.step.webServer.model.TeamAddMemberModel;
import com.step.webServer.model.TeamCreateModel;
import com.step.webServer.util.MapFactory;
import com.step.webServer.util.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/team", produces = "application/json;charset=UTF-8")
public class TeamController extends AbstractController  {
    private final TeamDao teamDao;
    private final UserDao userDao;
    private final MapFactory<String, Object> mapFactory;
    private final PatientDao patientDao;

    @Autowired
    HttpServletRequest request;

    public TeamController(TeamDao teamDao, UserDao userDao, MapFactory<String, Object> mapFactory, PatientDao patientDao) {
        this.teamDao = teamDao;
        this.userDao = userDao;
        this.mapFactory = mapFactory;
        this.patientDao = patientDao;
    }

    @GetMapping
    public Object teams(String keyword, int pageNum, int pageSize, String orderBy, boolean isAsc) {
        String direction = isAsc? " asc" : " desc";
        if("teamName".equals(orderBy)) {
            orderBy = "team_name" + direction;
        }else if ("createDatetime".equals(orderBy)) {
            orderBy = "create_datetime" + direction;
        }else if ("numMgtPatient".equals(orderBy)) {
            orderBy = "num_mgt_patient" + direction;
        }else {
            orderBy = null;
        }
        int userId = (int) request.getSession().getAttribute(SessionUtil.USER_ID);
        int roleId = (int) request.getSession().getAttribute(SessionUtil.ROLE_ID);

        List<Map<String, Object>> teams = teamDao.teams(pageNum, pageSize, orderBy, keyword, userId, roleId);
        Map<String, Object> responseMap = mapFactory.create();
        responseMap.put("teams", teams);
        responseMap.put("count", teamDao.teamTotalNum(keyword, userId, roleId));
        responseBuilder.setMap(responseMap);
        return responseBuilder.getJson();
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
        List<Map<String, Object>> teams = teamDao.membersByTeamId(Integer.valueOf(teamId));
        List<Map<String, Object>> sims = new ArrayList<>();
        for (Map<String, Object> team: teams) {
            Map<String, Object> sim = mapFactory.create();
            sim.put("username", team.get("username"));
            sim.put("roleName", team.get("roleName"));
            sims.add(sim);
        }
        map.put("members", sims);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping("/detail")
    public Object detailByTeamId(String teamId) {
        Map<String, Object> map = mapFactory.create();
        Team team = teamDao.teamById(Integer.valueOf(teamId));
        map.put("teamName", team.getTeamName());
        map.put("createDatetime", team.getCreateDatetime());
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @GetMapping("/overview")
    public Object overview(String teamId) {
        List<Map<String, Object>> teams = teamDao.membersByTeamId(Integer.valueOf(teamId));
        int count = 1;
        for (Map<String, Object> team: teams) {
            team.put("no", count++);
        }
        Map<String, Object> map = mapFactory.create();
        map.put("members", teams);
        responseBuilder.setMap(map);
        return responseBuilder.getJson();
    }

    @PostMapping("/addMember")
    public Object addMember(@RequestBody TeamAddMemberModel model) {
        for (String userId: model.getUserIds()) {
            teamDao.addMember(model.getTeamId(), userId);
        }
        return responseBuilder.getJson();
    }

    @PostMapping("/deleteMember")
    public Object deleteMember(String userId) {
        teamDao.deleteMember(userId);
        return responseBuilder.getJson();
    }

    @PostMapping("/create")
    public Object create(String teamName) {
        Map<String, Object> params = createTeam(teamName);

        responseBuilder.setMap(params);

        return responseBuilder.getJson();
    }

    @PostMapping("/delete")
    public Object delete(String teamId) {
        int intTeamId = Integer.valueOf(teamId);
        teamDao.deleteTeam(intTeamId);
        return responseBuilder.getJson();
    }

    @GetMapping("/usersAvailable")
    public Object usersAvailable(String type) {
        int userId = (int) request.getSession().getAttribute(SessionUtil.USER_ID);
        int roleId = (int) request.getSession().getAttribute(SessionUtil.ROLE_ID);
        Map<String, Object> params = mapFactory.create();
        if ("doctor".equals(type)) {
            List<Map<String, Object>> local = userDao.doctorsAvailable(userId, roleId);
            params.put("users", local);
        } else if ("nurse".equals(type)) {
            List<Map<String, Object>> local = userDao.nursesAvailable(userId, roleId);
            params.put("users", local);
        }
        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }

    @GetMapping("/allPatientsByTeamId")
    public Object allPatientsByTeamId(String teamId) {
        int teamIdInt = Integer.valueOf(teamId);
        List<Integer> patientIds = patientDao.getPatientIdsByTeamId(teamIdInt);
        List<String> patientIdsStr = new ArrayList<>();
        for (Integer patientId :
                patientIds) {
            patientIdsStr.add(patientId.toString());
        }
        Map<String, Object> params = mapFactory.create();
        params.put("patientIds", patientIdsStr);
        responseBuilder.setMap(params);
        return responseBuilder.getJson();
    }

    @PostMapping("/createAndAddMember")
    @Transactional
    public Object createAndAddMember(@RequestBody TeamCreateModel model) {
        Map<String, Object> params = createTeam(model.getTeamName());

        String teamIdStr = params.get("teamId") + "";

        for (String memberId:
             model.getUserIds()) {
            teamDao.addMember(teamIdStr, memberId);
        }

        responseBuilder.setMap(params);

        return responseBuilder.getJson();
    }

    private Map<String, Object> createTeam(String teamName) {
        int userId = (int) request.getSession().getAttribute("userId");
        Map<String, Object> params = mapFactory.create();
        params.put("teamName", teamName);
        params.put("adminId", request.getSession().getAttribute(SessionUtil.ADMIN_ID));
        params.put("teamId", null);
        teamDao.insertTeam(params);
        params.remove("teamName");
        params.remove("adminId");

        return params;
    }

    @PostMapping("/deleteTeammember")
    public Object deleteTeammember(String teamId, String userId) {
        return deleteMember(userId);
    }
}

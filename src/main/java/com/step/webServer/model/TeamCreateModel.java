package com.step.webServer.model;

import java.util.ArrayList;

public class TeamCreateModel {
    private String teamName;
    private ArrayList<String> userIds;

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public ArrayList<String> getUserIds() {
        return userIds;
    }

    public void setUserIds(ArrayList<String> userIds) {
        this.userIds = userIds;
    }
}

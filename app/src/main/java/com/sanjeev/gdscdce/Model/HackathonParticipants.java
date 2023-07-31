package com.sanjeev.gdscdce.Model;

import java.util.ArrayList;
import java.util.Map;

public class HackathonParticipants {

    private String TeamLead;
    private String TeamName;
    private int TeamSize;
    private ArrayList<Map<String,Boolean>> AttendStatus;
    private ArrayList<Map<String, String>> TeamDetails;

    public HackathonParticipants(){}

    public HackathonParticipants(String teamLead, String teamName, int teamSize, ArrayList<Map<String, Boolean>> attendStatus, ArrayList<Map<String, String>> teamDetails) {
        TeamLead = teamLead;
        TeamName = teamName;
        TeamSize = teamSize;
        AttendStatus = attendStatus;
        TeamDetails = teamDetails;
    }

    public String getTeamLead() {
        return TeamLead;
    }

    public void setTeamLead(String teamLead) {
        TeamLead = teamLead;
    }

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public int getTeamSize() {
        return TeamSize;
    }

    public void setTeamSize(int teamSize) {
        TeamSize = teamSize;
    }

    public ArrayList<Map<String, Boolean>> getAttendStatus() {
        return AttendStatus;
    }

    public void setAttendStatus(ArrayList<Map<String, Boolean>> attendStatus) {
        AttendStatus = attendStatus;
    }

    public ArrayList<Map<String, String>> getTeamDetails() {
        return TeamDetails;
    }

    public void setTeamDetails(ArrayList<Map<String, String>> teamDetails) {
        TeamDetails = teamDetails;
    }

    @Override
    public String toString() {
        return "HackathonParticipants{" +
                "TeamLead='" + TeamLead + '\'' +
                ", TeamName='" + TeamName + '\'' +
                ", TeamSize=" + TeamSize +
                ", AttendStatus=" + AttendStatus +
                ", TeamDetails=" + TeamDetails +
                '}';
    }

}

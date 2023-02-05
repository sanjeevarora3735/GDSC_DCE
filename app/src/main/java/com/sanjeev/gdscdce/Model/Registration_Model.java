package com.sanjeev.gdscdce.Model;

public class Registration_Model {
    private String Username;
    private String Contact_Number;
    private String InviteCode;
    private String Branch;
    private String Semester;

    public String getBranch() {
        return Branch;
    }

    public void setBranch(String branch) {
        Branch = branch;
    }

    public String getSemester() {
        return Semester;
    }

    public void setSemester(String semester) {
        Semester = semester;
    }

    public String getAboutMe() {
        return AboutMe;
    }

    public void setAboutMe(String aboutMe) {
        AboutMe = aboutMe;
    }

    private String AboutMe;

    public Registration_Model(){}

    public Registration_Model(String username, String contact_Number, String inviteCode, String branch, String semester, String aboutMe) {
        Username = username;
        Contact_Number = contact_Number;
        InviteCode = inviteCode;
        Branch = branch;
        Semester = semester;
        AboutMe = aboutMe;
    }

    public String getUsername() {
        return Username;
    }

    @Override
    public String toString() {
        return "Registration_Model{" +
                "Username='" + Username + '\'' +
                ", Contact_Number='" + Contact_Number + '\'' +
                ", InviteCode='" + InviteCode + '\'' +
                '}';
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getContact_Number() {
        return Contact_Number;
    }

    public void setContact_Number(String contact_Number) {
        Contact_Number = contact_Number;
    }

    public String getInviteCode() {
        return InviteCode;
    }

    public void setInviteCode(String inviteCode) {
        InviteCode = inviteCode;
    }
}

package com.sanjeev.gdscdce.Model;

public class Registration_Model {
    private String Username, Contact_Number, InviteCode;

    public Registration_Model(){}
    public Registration_Model(String username, String contact_Number, String inviteCode) {
        Username = username;
        Contact_Number = contact_Number;
        InviteCode = inviteCode;
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

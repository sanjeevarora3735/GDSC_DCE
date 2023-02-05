package com.sanjeev.gdscdce.Model;

public class AllProjects {
    String ProjectName;
    String ProjectID;
    String ProjectSourceUrl;
    String ProjectDescriptionBody;
    String ProfileImageUrl;
    String ProjectTags;
    String ProjectOwner;
    String ProjectPosterImageUrl;
    String ProjectSubmittingDate;
    String ProjectApprovalDate;
    String ProjectApprovalMentor;
    Boolean isShowcase;

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public String getProjectID() {
        return ProjectID;
    }

    public void setProjectID(String projectID) {
        ProjectID = projectID;
    }

    public String getProjectSourceUrl() {
        return ProjectSourceUrl;
    }

    public void setProjectSourceUrl(String ProjectSourceUrl) {
        this.ProjectSourceUrl = ProjectSourceUrl;
    }

    public String getProjectDescriptionBody() {
        return ProjectDescriptionBody;
    }

    public void setProjectDescriptionBody(String projectDescriptionBody) {
        ProjectDescriptionBody = projectDescriptionBody;
    }

    public String getProfileImageUrl() {
        return ProfileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        ProfileImageUrl = profileImageUrl;
    }

    public String getProjectTags() {
        return ProjectTags;
    }

    public void setProjectTags(String projectTags) {
        ProjectTags = projectTags;
    }

    public String getProjectOwner() {
        return ProjectOwner;
    }

    public void setProjectOwner(String projectOwner) {
        ProjectOwner = projectOwner;
    }

    public String getProjectPosterImageUrl() {
        return ProjectPosterImageUrl;
    }

    public void setProjectPosterImageUrl(String projectPosterImageUrl) {
        ProjectPosterImageUrl = projectPosterImageUrl;
    }

    public String getProjectSubmittingDate() {
        return ProjectSubmittingDate;
    }

    public void setProjectSubmittingDate(String projectSubmittingDate) {
        ProjectSubmittingDate = projectSubmittingDate;
    }

    public String getProjectApprovalDate() {
        return ProjectApprovalDate;
    }

    public void setProjectApprovalDate(String projectApprovalDate) {
        ProjectApprovalDate = projectApprovalDate;
    }

    public String getProjectApprovalMentor() {
        return ProjectApprovalMentor;
    }

    public void setProjectApprovalMentor(String projectApprovalMentor) {
        ProjectApprovalMentor = projectApprovalMentor;
    }

    public Boolean getisShowcase() {
        return isShowcase;
    }

    public void setisShowcase(Boolean showcase) {
        isShowcase = showcase;
    }

    public AllProjects() {
    }

    public AllProjects(String projectName, String projectID, String ProjectSourceUrl, String projectDescriptionBody, String profileImageUrl, String projectTags, String projectOwner, String projectPosterImageUrl, String projectSubmittingDate, String projectApprovalDate, String projectApprovalMentor, Boolean isShowcase) {
        ProjectName = projectName;
        ProjectID = projectID;
        this.ProjectSourceUrl = ProjectSourceUrl;
        ProjectDescriptionBody = projectDescriptionBody;
        ProfileImageUrl = profileImageUrl;
        ProjectTags = projectTags;
        ProjectOwner = projectOwner;
        ProjectPosterImageUrl = projectPosterImageUrl;
        ProjectSubmittingDate = projectSubmittingDate;
        ProjectApprovalDate = projectApprovalDate;
        ProjectApprovalMentor = projectApprovalMentor;
        this.isShowcase = isShowcase;
    }
}

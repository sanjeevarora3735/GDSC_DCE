package com.sanjeev.gdscdce.Model;

import com.google.firebase.firestore.PropertyName;

import java.util.ArrayList;

public class HackORelayModel {
    private String hackathonAbout;
    private String mainPosterImage;
    private String FirstPrize, SecondPrize, ThirdPrize;
    private ArrayList<String> switcherImages;
    private ArrayList<String> ThemesImages;
    private ArrayList<String> TimelineHeading;
    private ArrayList<String> TimelineDescriptions;

    public HackORelayModel() {
    }

    public HackORelayModel(String hackathonAbout, String mainPosterImage, String firstPrize, String secondPrize, String thirdPrize, ArrayList<String> switcherImages, ArrayList<String> themesImages, ArrayList<String> timelineHeading, ArrayList<String> timelineDescriptions) {
        this.hackathonAbout = hackathonAbout;
        this.mainPosterImage = mainPosterImage;
        FirstPrize = firstPrize;
        SecondPrize = secondPrize;
        ThirdPrize = thirdPrize;
        this.switcherImages = switcherImages;
        ThemesImages = themesImages;
        TimelineHeading = timelineHeading;
        TimelineDescriptions = timelineDescriptions;
    }

    public String getFirstPrize() {
        return FirstPrize;
    }

    public void setFirstPrize(String firstPrize) {
        FirstPrize = firstPrize;
    }

    public String getSecondPrize() {
        return SecondPrize;
    }

    public void setSecondPrize(String secondPrize) {
        SecondPrize = secondPrize;
    }

    public String getThirdPrize() {
        return ThirdPrize;
    }

    public void setThirdPrize(String thirdPrize) {
        ThirdPrize = thirdPrize;
    }

    @PropertyName("Hackathon_About")
    public String getHackathonAbout() {
        return hackathonAbout;
    }

    @PropertyName("Hackathon_About")
    public void setHackathonAbout(String hackathonAbout) {
        this.hackathonAbout = hackathonAbout;
    }

    @PropertyName("MainPosterImage")
    public String getMainPosterImage() {
        return mainPosterImage;
    }

    @PropertyName("MainPosterImage")
    public void setMainPosterImage(String mainPosterImage) {
        this.mainPosterImage = mainPosterImage;
    }

    @PropertyName("SwitcherImages")
    public ArrayList<String> getSwitcherImages() {
        return switcherImages;
    }

    @PropertyName("SwitcherImages")
    public void setSwitcherImages(ArrayList<String> switcherImages) {
        this.switcherImages = switcherImages;
    }

    @PropertyName("ThemesImages")
    public ArrayList<String> getThemesImages() {
        return ThemesImages;
    }

    @PropertyName("ThemesImages")
    public void setThemesImages(ArrayList<String> themesImages) {
        this.ThemesImages = themesImages;
    }

    @PropertyName("TimelineHeading")
    public ArrayList<String> getTimelineHeading() {
        return TimelineHeading;
    }

    @PropertyName("TimelineHeading")
    public void setTimelineHeading(ArrayList<String> timelineHeading) {
        this.TimelineHeading = timelineHeading;
    }

    @Override
    public String toString() {
        return "HackORelayModel{" +
                "hackathonAbout='" + hackathonAbout + '\'' +
                ", mainPosterImage='" + mainPosterImage + '\'' +
                ", switcherImages=" + switcherImages +
                ", ThemesImages=" + ThemesImages +
                ", TimelineHeading=" + TimelineHeading +
                ", TimelineDescriptions=" + TimelineDescriptions +
                '}';
    }

    @PropertyName("TimelineDescriptions")
    public ArrayList<String> getTimelineDescriptions() {
        return TimelineDescriptions;
    }

    @PropertyName("TimelineDescriptions")
    public void setTimelineDescriptions(ArrayList<String> timelineDescriptions) {
        this.TimelineDescriptions = timelineDescriptions;
    }
}

package com.sanjeev.gdscdce.Model;

import java.util.Map;

public class PastEvents {
    private String AccessedUrl, EventTitle, EventCollegeName, EventDescription, EventTimings, Youtube, EventTags;

    @Override
    public String toString() {
        return "PastEvents{" +
                "AccessedUrl='" + AccessedUrl + '\'' +
                ", EventTitle='" + EventTitle + '\'' +
                ", EventCollegeName='" + EventCollegeName + '\'' +
                ", EventDescription='" + EventDescription + '\'' +
                ", EventTimings='" + EventTimings + '\'' +
                ", Youtube='" + Youtube + '\'' +
                ", EventTags=" + EventTags +
                '}';
    }


    public PastEvents() {
    }


    public PastEvents(String accessedUrl, String eventTitle, String eventCollegeName, String eventDescription, String eventTimings, String youtube, String eventTags) {
        AccessedUrl = accessedUrl;
        EventTitle = eventTitle;
        EventCollegeName = eventCollegeName;
        EventDescription = eventDescription;
        EventTimings = eventTimings;
        Youtube = youtube;
        EventTags = eventTags;
    }

    public String getAccessedUrl() {
        return AccessedUrl;
    }

    public void setAccessedUrl(String accessedUrl) {
        AccessedUrl = accessedUrl;
    }

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getEventCollegeName() {
        return EventCollegeName;
    }

    public void setEventCollegeName(String eventCollegeName) {
        EventCollegeName = eventCollegeName;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public String getEventTimings() {
        return EventTimings;
    }

    public void setEventTimings(String eventTimings) {
        EventTimings = eventTimings;
    }

    public String getYoutube() {
        return Youtube;
    }

    public void setYoutube(String youtube) {
        Youtube = youtube;
    }

    public String getEventTags() {
        return EventTags;
    }

    public void setEventTags(String eventTags) {
        EventTags = eventTags;
    }
}

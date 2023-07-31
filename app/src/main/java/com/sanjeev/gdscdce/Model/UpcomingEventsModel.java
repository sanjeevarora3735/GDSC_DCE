package com.sanjeev.gdscdce.Model;

public class UpcomingEventsModel {
    private String RSVP_URL, PosterImageUrl;

    public UpcomingEventsModel(String RSVP_URL, String posterImageUrl) {
        this.RSVP_URL = RSVP_URL;
        PosterImageUrl = posterImageUrl;
    }

    public String getRSVP_URL() {
        return RSVP_URL;
    }

    public void setRSVP_URL(String RSVP_URL) {
        this.RSVP_URL = RSVP_URL;
    }

    public String getPosterImageUrl() {
        return PosterImageUrl;
    }

    public void setPosterImageUrl(String posterImageUrl) {
        PosterImageUrl = posterImageUrl;
    }
}

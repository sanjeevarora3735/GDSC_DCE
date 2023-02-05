package com.sanjeev.gdscdce.Model;

public class CTMembers {

    String Name , ImageSrc, Title;

    public CTMembers() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getImageSrc() {
        return ImageSrc;
    }

    public void setImageSrc(String ImageSrc) {
        ImageSrc = ImageSrc;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public CTMembers(String name, String ImageSrc, String title) {
        Name = name;
        ImageSrc = ImageSrc;
        Title = title;
    }
}

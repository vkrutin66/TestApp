package ua.vladkrutin.testapp;

public class VideoButton {

    private String name;
    private String link;

    public VideoButton() {
        name = "Youtube Video";
        link = "https://www.youtube.com/embed/eXvBjCO19QY";
    }

    public VideoButton(String name, String link) {
        this.name = name;
        this.link = link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLink() {
        return link;
    }

}

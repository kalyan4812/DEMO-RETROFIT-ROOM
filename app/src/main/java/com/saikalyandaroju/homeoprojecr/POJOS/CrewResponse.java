package com.saikalyandaroju.homeoprojecr.POJOS;

import java.util.List;

public class CrewResponse {
    public String name;
    public String agency;
    public String image;
    public String wikipedia;
    public List<String> launches;
    public String status;
    public String id;

    public CrewResponse() {

    }

    public CrewResponse(String name, String agency, String image, String wikipedia, String status) {
        this.name = name;
        this.agency = agency;
        this.image = image;
        this.wikipedia = wikipedia;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getAgency() {
        return agency;
    }

    public String getImage() {
        return image;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public List<String> getLaunches() {
        return launches;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}

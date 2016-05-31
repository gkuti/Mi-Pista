package com.andela.gkuti.mipista.model;

/**
 * Location class
 */
public class Location {
    private String location;
    private String hits;

    public Location(String location, String hits) {
        this.location = location;
        this.hits = hits;
    }

    public String getLocation() {
        return location;
    }

    public String getHits() {
        return hits;
    }
}

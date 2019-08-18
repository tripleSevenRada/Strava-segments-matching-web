package com.matching.segmentsmatching.resources;

public class Location {

    private double lat;
    private double lon;

    public Location(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public Location(){}

    public double getLat() { return lat; }
    public double getLon() {
        return lon;
    }

    public void setLat(double lat) { this.lat = lat; }
    public void setLon(double lon) {
        this.lon = lon;
    }

}

package com.matching.segmentsmatching.resources;

public class SegmentDetected {

    private String name;
    private double latStart;
    private double lonStart;
    private double latFinish;
    private double lonFinish;

    public SegmentDetected(String name, double latStart, double lonStart, double latFinish, double lonFinish) {
        this.name = name;
        this.latStart = latStart;
        this.lonStart = lonStart;
        this.latFinish = latFinish;
        this.lonFinish = lonFinish;
    }

    public SegmentDetected() {}

    public String getName() {
        return name;
    }

    public double getLatStart() {
        return latStart;
    }

    public double getLonStart() {
        return lonStart;
    }

    public double getLatFinish() {
        return latFinish;
    }

    public double getLonFinish() {
        return lonFinish;
    }

    public void setName(String name) { this.name = name; }

    public void setLatStart(double latStart) { this.latStart = latStart; }

    public void setLonStart(double lonStart) { this.lonStart = lonStart; }

    public void setLatFinish(double latFinish) { this.latFinish = latFinish; }

    public void setLonFinish(double lonFinish) { this.lonFinish = lonFinish; }

}

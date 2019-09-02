package com.matching.segmentsmatching.resources;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class SegmentDetected {

    @NotBlank(message = "name must not be blank")
    private String name;
    @Min(-90) @Max(90)
    private double latStart;
    @Min(-180) @Max(180)
    private double lonStart;
    @Min(-90) @Max(90)
    private double latFinish;
    @Min(-180) @Max(180)
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

    @Override
    public String toString() {
        return "SegmentDetected{" +
                "name='" + name + '\'' +
                ",latS=" + latStart +
                ",lonS=" + lonStart +
                ",latF=" + latFinish +
                ",lonF=" + lonFinish +
                '}';
    }
}

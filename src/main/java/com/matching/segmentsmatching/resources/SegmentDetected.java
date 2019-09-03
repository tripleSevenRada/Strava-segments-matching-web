package com.matching.segmentsmatching.resources;

import org.jetbrains.annotations.NotNull;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

public class SegmentDetected implements Comparable<SegmentDetected>{

    @NotBlank(message = "name must not be blank")
    private String name;
    @Min(-90) @Max(90)
    private double latitudeStart;
    @Min(-180) @Max(180)
    private double longitudeStart;
    @Min(-90) @Max(90)
    private double latitudeFinish;

    private int startIndexInOriginalRoute;
    private int finishIndexInOriginalRoute;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SegmentDetected that = (SegmentDetected) o;
        return Double.compare(that.latitudeStart, latitudeStart) == 0 &&
                Double.compare(that.longitudeStart, longitudeStart) == 0 &&
                Double.compare(that.latitudeFinish, latitudeFinish) == 0 &&
                Double.compare(that.longitudeFinish, longitudeFinish) == 0 &&
                name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitudeStart, longitudeStart, latitudeFinish, longitudeFinish);
    }

    @Min(-180) @Max(180)
    private double longitudeFinish;

    public SegmentDetected(String name, double latitudeStart, double longitudeStart,
                           double latitudeFinish, double longitudeFinish,
                           int startIndexInOriginalRoute, int finishIndexInOriginalRoute) {
        this.name = name;
        this.latitudeStart = latitudeStart;
        this.longitudeStart = longitudeStart;
        this.latitudeFinish = latitudeFinish;
        this.longitudeFinish = longitudeFinish;
        this.startIndexInOriginalRoute = startIndexInOriginalRoute;
        this.finishIndexInOriginalRoute = finishIndexInOriginalRoute;
    }

    @Override
    public int compareTo(@NotNull SegmentDetected other) {
        return Integer.compare(this.startIndexInOriginalRoute, other.startIndexInOriginalRoute);
    }

    public SegmentDetected() {}

    public String getName() { return name; }

    public double getLatitudeStart() { return latitudeStart; }

    public double getLongitudeStart() { return longitudeStart; }

    public double getLatitudeFinish() { return latitudeFinish; }

    public double getLongitudeFinish() { return longitudeFinish; }

    public int getStartIndexInOriginalRoute() { return startIndexInOriginalRoute; }

    public int getFinishIndexInOriginalRoute() { return finishIndexInOriginalRoute; }

    public void setName(String name) { this.name = name; }

    public void setLatitudeStart(double latitudeStart) { this.latitudeStart = latitudeStart; }

    public void setLongitudeStart(double longitudeStart) { this.longitudeStart = longitudeStart; }

    public void setLatitudeFinish(double latitudeFinish) { this.latitudeFinish = latitudeFinish; }

    public void setLongitudeFinish(double longitudeFinish) { this.longitudeFinish = longitudeFinish; }

    public void setStartIndexInOriginalRoute(int startIndexInOriginalRoute) {
        this.startIndexInOriginalRoute = startIndexInOriginalRoute;
    }

    public void setFinishIndexInOriginalRoute(int finishIndexInOriginalRoute) {
        this.finishIndexInOriginalRoute = finishIndexInOriginalRoute;
    }

    @Override
    public String toString() {
        return "SegmentDetected{" +
                "startIndexInOriginalRoute=" + startIndexInOriginalRoute +
                ", finishIndexInOriginalRoute=" + finishIndexInOriginalRoute +
                '}';
    }
}

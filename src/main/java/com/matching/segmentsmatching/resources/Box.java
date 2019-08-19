package com.matching.segmentsmatching.resources;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class Box {

    @Min(-90) @Max(90)
    private double latS;
    @Min(-90) @Max(90)
    private double latN;
    @Min(-180) @Max(180)
    private double lonW;
    @Min(-180) @Max(180)
    private double lonE;

    public Box(double latS, double latN, double lonW, double lonE) {
        this.latS = latS;
        this.latN = latN;
        this.lonW = lonW;
        this.lonE = lonE;
    }

    public Box(){}

    public double getLatS() {
        return latS;
    }

    public double getLatN() {
        return latN;
    }

    public double getLonW() {
        return lonW;
    }

    public double getLonE() {
        return lonE;
    }

    public void setLatS(double latS) {
        this.latS = latS;
    }

    public void setLatN(double latN) {
        this.latN = latN;
    }

    public void setLonW(double lonW) {
        this.lonW = lonW;
    }

    public void setLonE(double lonE) {
        this.lonE = lonE;
    }

}

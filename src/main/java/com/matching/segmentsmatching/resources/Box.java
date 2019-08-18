package com.matching.segmentsmatching.resources;

public class Box {

    private double latS;
    private double latN;
    private double lonW;
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

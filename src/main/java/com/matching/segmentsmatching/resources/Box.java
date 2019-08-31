package com.matching.segmentsmatching.resources;

import com.matching.segmentsmatching.validators.SmallEnough;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@SmallEnough.List({
        @SmallEnough(
                field1 = "latN",
                field2 = "latS",
                message = "lat diff too large"
        ),
        @SmallEnough(
                field1 = "lonW",
                field2 = "lonE",
                message = "lon diff too large"
        )
})
public class Box {

    @Min(-90) @Max(90)
    private double latN;
    @Min(-90) @Max(90)
    private double latS;
    @Min(-180) @Max(180)
    private double lonW;
    @Min(-180) @Max(180)
    private double lonE;

    public Box(double latN, double latS, double lonW, double lonE) {
        this.latN = latN;
        this.latS = latS;
        this.lonW = lonW;
        this.lonE = lonE;
    }

    public double getLatN() {
        return latN;
    }

    public double getLatS() {
        return latS;
    }

    public double getLonW() {
        return lonW;
    }

    public double getLonE() {
        return lonE;
    }
}

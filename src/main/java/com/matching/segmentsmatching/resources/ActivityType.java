package com.matching.segmentsmatching.resources;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ActivityType {
    // in web server: act t = case t of "ride" -> Riding; "run" -> Running; _ -> error "unknown activity type"
    RIDE("ride"),
    RUN("run");
    public final String label;
    private ActivityType(String label) {
        this.label = label;
    }
    @Override
    public String toString(){
        return label;
    }
}

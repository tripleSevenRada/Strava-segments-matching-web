package com.matching.segmentsmatching.resources;

public enum ActivityType {
    // in web server: act t = case t of "ride" -> Riding; "run" -> Running; _ -> error "unknown activity type"
    RIDE("ride"),
    RUN("run");
    public final String label;
    private ActivityType(String label) {
        this.label = label;
    }
}

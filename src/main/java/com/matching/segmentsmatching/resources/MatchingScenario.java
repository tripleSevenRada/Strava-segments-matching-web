package com.matching.segmentsmatching.resources;

public enum MatchingScenario {
    // allow more "loose" matching for recorded tracks and for testing
    ROUTE("route"),
    RECORDED("recorded"),
    LOOSE("loose");
    public final String label;
    private MatchingScenario(String label) {
        this.label = label;
    }
}

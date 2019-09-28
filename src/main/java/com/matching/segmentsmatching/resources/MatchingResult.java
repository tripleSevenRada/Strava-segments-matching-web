package com.matching.segmentsmatching.resources;

import java.util.*;

public class MatchingResult {

    private List<SegmentDetected> segmentsDetected = new ArrayList<>();

    public List<SegmentDetected> getSegmentsDetected() {
        return segmentsDetected;
    }

    public void add(SegmentDetected segmentDetected) {
        segmentsDetected.add(segmentDetected);
    }

    public MatchingResult organize(){
        removeDuplicities();
        Collections.sort(segmentsDetected);
        return this;
    }

    private void removeDuplicities() {
        List<SegmentDetected> uniqueList = new ArrayList<>();
        Set<SegmentDetected> uniqueSet = new HashSet<>();
        segmentsDetected.forEach(segment ->
                {
                    if (!uniqueSet.contains(segment)) {
                        uniqueList.add(segment);
                        uniqueSet.add(segment);
                    }
                }
        );
        segmentsDetected = uniqueList;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ", segments detected: " + segmentsDetected.size();
    }
}

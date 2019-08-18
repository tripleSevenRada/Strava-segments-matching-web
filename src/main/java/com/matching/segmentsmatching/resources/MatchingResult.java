package com.matching.segmentsmatching.resources;

import java.util.LinkedList;
import java.util.List;

public class MatchingResult {

    private List <SegmentDetected> segmentDetectedList = new LinkedList<>();

    public List<SegmentDetected> getSegmentDetectedList() {
        return segmentDetectedList;
    }

    public void add(SegmentDetected segmentDetected){
        segmentDetectedList.add(segmentDetected);
    }
}

package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.Box;

public interface SegmentService {

    String getSegments(Box box, ActivityType type, String token);

}

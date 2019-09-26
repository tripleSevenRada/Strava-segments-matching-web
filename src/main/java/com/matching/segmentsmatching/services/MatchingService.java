package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.*;
import dataClasses.Box;
import dataClasses.Location;
import dataClasses.LocationIndex;
import geospatial.MatchingCandidates;
import geospatial.Route;
import geospatial.Segment;
import matching.MatcherWrapper;
import matching.MatchingConfig;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    private final Map<MatchingScenario, MatchingConfig> scenarioToConfig =
            new HashMap<MatchingScenario, MatchingConfig>() {
                {
                    put(MatchingScenario.ROUTE, new MatchingConfig(0.94, 14.0));
                    put(MatchingScenario.RECORDED, new MatchingConfig(0.90, 18.0));
                    put(MatchingScenario.LOOSE, new MatchingConfig(0.86, 26.0));
                }
            };

    public LatLonBox getBox(RequestedRoute requestedRoute) throws RuntimeException {

        //build a mock segment to use its box
        List<Location> forSegmentMock = requestedRoute.getLocations()
                .stream()
                .map(latLon -> new Location(latLon.getLat(), latLon.getLon()))
                .collect(Collectors.toList());
        Box box;
        // throws RuntimeException
        box = new Segment(forSegmentMock, "mock").getBox();
        if (box instanceof dataClasses.Valid) {
            return new LatLonBox(
                    ((dataClasses.Valid) box).getMaxLat(),
                    ((dataClasses.Valid) box).getMinLat(),
                    ((dataClasses.Valid) box).getMinLon(),
                    ((dataClasses.Valid) box).getMaxLon());
        } else throw new RuntimeException("box is invalid");
    }

    public MatchingResult getMatchingResult(
            Route originalRoute,
            Route discretizedRoute,
            List<Segment> discretizedSegments,
            MatchingScenario matchingScenario) {

        MatchingConfig config = scenarioToConfig.get(matchingScenario);
        MatchingResult result = new MatchingResult();

        for (Segment segment : discretizedSegments) {
            if (segment.getElements().isEmpty()) continue;
            List<LocationIndex> rawLocationsForCandidates = discretizedRoute.getPointsWithinBox(segment.getBox());
            MatchingCandidates candidates = discretizedRoute.getMatchingCandidates(rawLocationsForCandidates);
            for (int j = 0; j < candidates.getCandidates().size(); j++) {
                List<LocationIndex> candidate = candidates.getCandidates().get(j);
                MatcherWrapper wrapper = new MatcherWrapper();
                if (wrapper.matchParallel(candidate, segment, config)) {
                    // prepare helpers
                    Location segStartLoc = segment.getElements().get(0);
                    int segLastInd = segment.getElements().size() - 1;
                    Location segFinishLoc = segment.getElements().get(segLastInd);
                    // start and finish indices in the original route
                    Location start = new Location(segStartLoc.getLat(), segStartLoc.getLon());
                    int indexStart = wrapper.getClosestIndexInRoute(originalRoute, start);
                    Location finish = new Location(segFinishLoc.getLat(), segFinishLoc.getLon());
                    int indexFinish = wrapper.getClosestIndexInRoute(originalRoute, finish);
                    result.add(new SegmentDetected(
                            segment.getName(),
                            segStartLoc.getLat(),
                            segStartLoc.getLon(),
                            segFinishLoc.getLat(),
                            segFinishLoc.getLon(),
                            indexStart,
                            indexFinish
                    ));
                    // if route goes in rounds (laps) then only on first round the segment is detected
                    break;
                }
            }
        }
        return result.organize();
    }
}

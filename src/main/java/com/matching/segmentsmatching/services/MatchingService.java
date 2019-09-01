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
                    put(MatchingScenario.ROUTE, new MatchingConfig(0.94, 10.0));
                    put(MatchingScenario.RECORDED, new MatchingConfig(0.90, 16.0));
                    put(MatchingScenario.LOOSE, new MatchingConfig(0.7, 32.0));
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
                    result.add(new SegmentDetected(
                            segment.getName(),
                            segment.getElements().get(0).getLat(),
                            segment.getElements().get(0).getLon(),
                            segment.getElements().get(segment.getElements().size() - 1).getLat(),
                            segment.getElements().get(segment.getElements().size() - 1).getLon()
                    ));
                    // if route goes in rounds (laps) then only first round is segment detected
                    break;
                }
            }
        }
        return result;
    }
}

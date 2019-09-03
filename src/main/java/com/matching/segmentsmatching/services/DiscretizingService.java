package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.RequestedRoute;
import com.matching.segmentsmatching.resources.SegmentParsed;
import dataClasses.Location;
import geospatial.DiscretizerWrapper;
import geospatial.Route;
import geospatial.Segment;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DiscretizingService {

    private final double discretizeDistance = 3.0;

    public List<Segment> segmentsDiscretizeParallel(List<Segment> inputSegments) {
        List<Segment> outputSegments = new ArrayList<>();
        inputSegments.forEach(discretizable ->
                {
                    DiscretizerWrapper wrapper = new DiscretizerWrapper();
                    outputSegments.add(
                            new Segment(wrapper.discretizeParallel(discretizable, discretizeDistance),
                                    discretizable.getName()));
                }
        );
        return outputSegments;
    }

    public Route routeDiscretizeParallel(Route inputRoute) {
        DiscretizerWrapper wrapper = new DiscretizerWrapper();
        return new Route(wrapper.discretizeParallel(inputRoute, discretizeDistance));
    }

    public List<Segment> copySegments(List<SegmentParsed> input) {
        List<Segment> copies = new ArrayList<>();
        input.forEach(s -> {
            List<Location> locations = new ArrayList<>();
            // copySegments locations
            s.getLocations().forEach(l -> {
                locations.add(new Location(l.getLat(), l.getLon()));
            });
            copies.add(new Segment(locations, s.getName()));
        });
        return copies;
    }

    public Route copyRoute(RequestedRoute requestedRoute) {
        List<Location> locations = new ArrayList<>();
        requestedRoute.getLocations().forEach(l ->
                locations.add(new Location(l.getLat(), l.getLon())));
        return new Route(locations);
    }
}

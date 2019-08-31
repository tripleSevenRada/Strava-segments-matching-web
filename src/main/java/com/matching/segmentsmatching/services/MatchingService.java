package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.LatLonBox;
import com.matching.segmentsmatching.resources.RequestedRoute;
import dataClasses.Box;
import dataClasses.Location;
import geospatial.Segment;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MatchingService {

    public LatLonBox getBox(RequestedRoute requestedRoute) throws RuntimeException {

        //build a mock segment to use its box
        List<Location> forSegmentMock = requestedRoute.getLocations()
                .stream()
                .map(latLon -> new Location(latLon.getLat(), latLon.getLon()))
                .collect(Collectors.toList());
        Box box;
        // throws RuntimeException
        box = new Segment(forSegmentMock,"mock").getBox();
        if (box instanceof dataClasses.Valid) {
            return new LatLonBox(
                    ((dataClasses.Valid) box).getMaxLat(),
                    ((dataClasses.Valid) box).getMinLat(),
                    ((dataClasses.Valid) box).getMinLon(),
                    ((dataClasses.Valid) box).getMaxLon());
        } else throw new RuntimeException("box is invalid");
    }

    //  TODO API

}

package com.matching.segmentsmatching.controllers;

import com.matching.segmentsmatching.resources.*;
import com.matching.segmentsmatching.services.MatchingService;
import com.matching.segmentsmatching.services.SegmentService;
import com.matching.segmentsmatching.services.XMLNomiResponseParser;
import dataClasses.Box;
import dataClasses.Location;
import geospatial.Segment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class MatchingController {

    private static final Logger LOG = LoggerFactory.getLogger(MatchingController.class);

    @Autowired
    private SegmentService segmentService;

    @Autowired
    private MatchingService matchingService;

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public MatchingResult match(@Valid @RequestBody(required = true) RequestedRoute requestedRoute) {

        String token = requestedRoute.getToken();
        ActivityType type = requestedRoute.getType();

        //build a mock segment to use its computeBox function
        List<Location> locationsToBuildSegmentMock = getDeepCopyLoc(requestedRoute);
        Box box = null;
        try {
            Segment segment = new Segment(locationsToBuildSegmentMock);
            box = segment.getBox();
        } catch (RuntimeException re) {
            //TODO throw, handle in @ControllerAdvice
        }
        String segmentsRaw = null;
        if (box instanceof dataClasses.Valid) {
            LatLonBox latLonBox = new LatLonBox(
                    ((dataClasses.Valid) box).getMaxLat(),
                    ((dataClasses.Valid) box).getMinLat(),
                    ((dataClasses.Valid) box).getMinLon(),
                    ((dataClasses.Valid) box).getMaxLon());
            try {
                segmentsRaw = segmentService.getSegments(latLonBox, type, token);
            } catch (ConstraintViolationException cve) {
                //TODO throw, handle in @ControllerAdvice
            } catch (Exception e) {
                //TODO throw, handle in @ControllerAdvice
            }
            if (segmentsRaw == null) {
                //TODO throw, handle in @ControllerAdvice
            }
            List<SegmentParsed> segments = null;
            try {
                XMLNomiResponseParser parser = new XMLNomiResponseParser();
                segments = parser.parseXMLNomiResponse(segmentsRaw);
            } catch (Exception e) {
                //TODO throw, handle in @ControllerAdvice
            }
            if (segments == null) {
                //TODO throw, handle in @ControllerAdvice
            }

            LOG.info("on LatLonBox: " + latLonBox.toString());
            LOG.info("segments.size(): " + segments.size());
            segments.forEach(s -> LOG.info(s.toString()));






        } else {
            //TODO throw, handle in @ControllerAdvice
        }

        // TODO
        return new MatchingResult();
    }

    private List<Location> getDeepCopyLoc(RequestedRoute requestedRoute) {
        List<Location> copy = new ArrayList<>();
        requestedRoute.getLocations()
                .forEach(l -> copy.add(new Location(l.getLat(), l.getLon())));
        return copy;
    }

}

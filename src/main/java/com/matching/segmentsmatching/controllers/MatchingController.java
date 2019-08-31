package com.matching.segmentsmatching.controllers;

import com.matching.segmentsmatching.exceptions.MatchingValidityException;
import com.matching.segmentsmatching.resources.*;
import com.matching.segmentsmatching.services.DiscretizingService;
import com.matching.segmentsmatching.services.MatchingService;
import com.matching.segmentsmatching.services.SegmentService;
import com.matching.segmentsmatching.services.XMLNomiResponseParser;
import geospatial.Route;
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
import java.util.List;

@RestController
public class MatchingController {

    private static final Logger LOG = LoggerFactory.getLogger(MatchingController.class);

    @Autowired
    private SegmentService segmentService;
    @Autowired
    private MatchingService matchingService;
    @Autowired
    private DiscretizingService discretizingService;

    private boolean verbose = true;

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public MatchingResult match(@Valid @RequestBody(required = true) RequestedRoute requestedRoute) {

        String token = requestedRoute.getToken();
        ActivityType type = requestedRoute.getType();

        LatLonBox latLonBox = null;
        try {
            latLonBox = matchingService.getBox(requestedRoute);
        } catch (RuntimeException re) { throwMVE(re.getMessage(),1); }
        if(latLonBox == null)throwMVE("null",2);
        String segmentsRaw = null;
        try {
            segmentsRaw = segmentService.getSegments(latLonBox, type, token);
        } catch (ConstraintViolationException cve) {
            throwMVE(cve.getMessage(), 3);
        } catch (Exception e) {
            throwMVE(e.getMessage(),4);
        }
        if (segmentsRaw == null) throwMVE("null",5);
        List<SegmentParsed> segments = null;
        try {
            XMLNomiResponseParser parser = new XMLNomiResponseParser();
            segments = parser.parseXMLNomiResponse(segmentsRaw);
        } catch (Exception e) {
            throwMVE(e.getMessage(), 6);
        }
        if (segments == null) throwMVE("null",7);

        if (verbose) LOG.info("LatLonBox: " + latLonBox.toString());
        if (verbose) segments.forEach(s -> LOG.info(s.toString()));
        List<Segment> inputSegmentsForDiscretization = discretizingService.copySegments(segments);

        // discretizedSegments
        List<Segment> discretizedSegments = discretizingService
                .segmentsDiscretizeParallel(inputSegmentsForDiscretization);

        if(verbose) discretizedSegments.forEach(s -> LOG.info(s.toString()));
        Route inputRouteForDiscretization =
                discretizingService.copyRoute(requestedRoute);
        LOG.info("input: " + inputRouteForDiscretization.toString());

        // discretizedRoute
        Route discretizedRoute =
                discretizingService.routeDiscretizeParallel(inputRouteForDiscretization);

        LOG.info("discretized: " + discretizedRoute.toString());

        return new MatchingResult();
    }

    private void throwMVE(String message, long id){
        throw new MatchingValidityException(message + " " + id);
    }
}

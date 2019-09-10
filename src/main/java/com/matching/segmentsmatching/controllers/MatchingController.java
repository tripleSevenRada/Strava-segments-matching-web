package com.matching.segmentsmatching.controllers;

import com.matching.segmentsmatching.exceptions.MatchingValidityException;
import com.matching.segmentsmatching.resources.*;
import com.matching.segmentsmatching.services.*;
import geospatial.Route;
import geospatial.Segment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
public class MatchingController {

    private static final Logger LOG = LoggerFactory.getLogger(MatchingController.class);

    private SegmentService segmentService; //TestSegmentService
    private MatchingService matchingService;
    private DiscretizingService discretizingService;

    @Autowired
    public MatchingController(SegmentService segmentService,
                              MatchingService matchingService,
                              DiscretizingService discretizingService) {
        this.segmentService = segmentService;
        this.matchingService = matchingService;
        this.discretizingService = discretizingService;
    }

    private boolean verbose = true;

    @SuppressWarnings("unchecked")
    public <T extends Enum> T getEnum(Function<String, String> letterCase, String validatedString, Class<T> type) {
        Object[] enums = type.getEnumConstants();
        for (Object e : enums) {
            if (e.toString().equals(letterCase.apply(validatedString))) {
                return (T) e;
            }
        }
        return null;
    }

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public MatchingResult match(@Valid @RequestBody(required = true) RequestedRoute requestedRoute) {
        LOG.info("---------requested: " + requestedRoute.toString());
        String token = requestedRoute.getToken();

        // convert validated strings to their enums;
        ActivityType type = getEnum(String::toLowerCase, requestedRoute.getType(), ActivityType.class);
        MatchingScenario scenario = getEnum(String::toLowerCase, requestedRoute.getMatchingScenario(), MatchingScenario.class);
        if (type == null || scenario == null)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR); // should never happen

        LatLonBox latLonBox = null;
        try {
            latLonBox = matchingService.getBox(requestedRoute);
        } catch (RuntimeException re) {
            throwMVE(re.getMessage(), 1);
        }
        if (latLonBox == null)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR); // should never happen
        String segmentsRaw = null;
        try {
            segmentsRaw = segmentService.getSegments(latLonBox, type, token);
        } catch (ConstraintViolationException cve) {
            throwMVE(cve.getMessage(), 2);
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (segmentsRaw == null)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR); // should never happen
        List<SegmentParsed> segments;
        try {
            XMLNomiResponseParser parser = new XMLNomiResponseParser();
            segments = parser.parseXMLNomiResponse(segmentsRaw);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (segments == null)
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR); // should never happen

        // In case we have found no segments in the above specified box:
        // Short circuit this controller and return empty result with OK status
        if (segments.isEmpty()) return new MatchingResult();

        if (verbose) {
            LOG.info("LatLonBox: " + latLonBox.toString());
        }
        List<Segment> inputSegmentsForDiscretization = discretizingService.copySegments(segments);
        LOG.info("inputSegmentsForDiscretization.size(): " + inputSegmentsForDiscretization.size());

        // discretizedSegments
        List<Segment> discretizedSegments = discretizingService
                .segmentsDiscretizeParallel(inputSegmentsForDiscretization);

        if (verbose) discretizedSegments.forEach(s -> LOG.info(s.toString()));
        Route inputRouteForDiscretization =
                discretizingService.copyRoute(requestedRoute);
        LOG.info("input Route: " + inputRouteForDiscretization.toString());

        // discretizedRoute
        Route discretizedRoute =
                discretizingService.routeDiscretizeParallel(inputRouteForDiscretization);

        LOG.info("discretized Route: " + discretizedRoute.toString());
        if (verbose) LOG.info("invoking matching service now");

        MatchingResult result;
        try {
            result = matchingService.getMatchingResult(inputRouteForDiscretization, discretizedRoute,
                    discretizedSegments, scenario);
        } catch (Exception e) {
            throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (result == null) throw new HttpServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR);

        if (verbose) LOG.info("matching service returned now");

        LOG.info(result.toString());
        if (verbose) {
            result.getSegmentDetectedList().forEach(detected ->
                    LOG.info(detected.toString())
            );
        }
        return result;
    }

    private void throwMVE(String message, long id) {
        throw new MatchingValidityException(message + " " + id);
    }
}
package com.matching.segmentsmatching.controllers;

import com.matching.segmentsmatching.resources.MatchingResult;
import com.matching.segmentsmatching.resources.RequestedRoute;
import com.matching.segmentsmatching.services.SegmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class MatchingController{

    @Autowired
    private SegmentService segmentService;

    @RequestMapping(value = "/match", method = RequestMethod.POST)
    public MatchingResult match(@Valid @RequestBody(required = true) RequestedRoute requestedRoute) {
        String token = requestedRoute.getToken();
        // TODO
        return new MatchingResult();
    }

}

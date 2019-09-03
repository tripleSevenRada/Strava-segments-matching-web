package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.Box;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Service
@Validated
public class SegmentService {

    // https://www.baeldung.com/spring-5-webclient

    public String getSegments(@Valid Box box, ActivityType type, String token)
    throws ConstraintViolationException {
        String baseUrl = "https://nomi.cz/strava/segmentsView.kml";
        WebClient webClient = WebClient.create(baseUrl);
        return null;
    }
}

package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.Box;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class SegmentServiceImpl implements SegmentService{

    // https://www.baeldung.com/spring-5-webclient

    @Override
    public String getSegments(Box box, ActivityType type, String token) {
        String baseUrl = "https://nomi.cz/strava/segmentsView.kml";
        WebClient webClient = WebClient.create(baseUrl);
        return null;
    }
}

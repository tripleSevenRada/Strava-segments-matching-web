package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.LatLonBox;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@Service
@Validated
public class SegmentService {

    // https://www.baeldung.com/spring-5-webclient

    public String getSegments(@Valid LatLonBox box, ActivityType type, String token)
            throws ConstraintViolationException, WebClientException {
        String decimalFormat = "%.4f";

        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("nomi.cz")
                .path("/strava/segmentsView.kml");
        builder.queryParam("south", String.format(decimalFormat, box.getLatS()));
        builder.queryParam("north", String.format(decimalFormat, box.getLatN()));
        builder.queryParam("west", String.format(decimalFormat, box.getLonW()));
        builder.queryParam("east", String.format(decimalFormat, box.getLonE()));
        builder.queryParam("token", token);
        builder.queryParam("type", type.label);
        String uri = builder.build().encode().toUriString();

        WebClient webClient = WebClient.create(uri);
        Mono<String> resultMono = webClient
                .get()
                .retrieve()
                .bodyToMono(String.class);
        return resultMono.block();
    }
}

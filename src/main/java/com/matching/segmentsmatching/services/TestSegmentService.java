package com.matching.segmentsmatching.services;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.LatLonBox;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.reactive.function.client.WebClientException;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

@Service
@Validated
public class TestSegmentService {

    // paths
    // /home/radim/IdeaProjects/segments-matching/samples/sampleSegmentsStringFormError.xml
    // /home/radim/IdeaProjects/segments-matching/samples/sampleSegmentsString.xml

    public String getSegments(@Valid LatLonBox box, ActivityType type, String token)
            throws ConstraintViolationException, WebClientException, InterruptedException {

        Charset encoding = StandardCharsets.UTF_8;
        String path = "/home/radim/IdeaProjects/segments-matching/samples/sampleSegmentsString.xml";
        String fromFile = null;
        try {
            fromFile = readFile(path, encoding);
        } catch (Exception e){
            throw new RuntimeException("TestSegmentService");
        }

        Random r = new Random();
        long l = (long) r.nextInt(2000);

        Thread.sleep(l);

        return fromFile;
    }

    private String readFile(String path, Charset encoding)
            throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }

}

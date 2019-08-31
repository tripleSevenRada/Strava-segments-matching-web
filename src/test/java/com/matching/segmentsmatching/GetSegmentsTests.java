package com.matching.segmentsmatching;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.LatLonBox;
import com.matching.segmentsmatching.resources.LatLonPair;
import com.matching.segmentsmatching.resources.SegmentParsed;
import com.matching.segmentsmatching.services.SegmentService;
import com.matching.segmentsmatching.services.XMLNomiResponseParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.management.modelmbean.XMLParseException;
import javax.validation.ConstraintViolationException;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GetSegmentsTests {

    private static final Logger LOG = LoggerFactory.getLogger(GetSegmentsTests.class);
    private final String tokenValid = "mock";
    private final String tokenInvalid = "12er34";

    @Autowired
    private SegmentService service;

    //@Test
    public void printSegmentsAsSeparateListsOfLocations() {
        LatLonBox box = new LatLonBox(50.025, 49.999, 14.01, 14.288);
        try {
            String segments = service.getSegments(box, ActivityType.RIDE, tokenValid);
            XMLNomiResponseParser parser = new XMLNomiResponseParser();
            List<SegmentParsed> segmentsList = parser.parseXMLNomiResponse(segments);
            LOG.info("segmentsList.size(): " + segmentsList.size());
            for (SegmentParsed segmentParsed : segmentsList) {
                LOG.info("segment: " + segmentParsed.getName());
                for (LatLonPair l : segmentParsed.getLocations()) {
                    LOG.info(l.toString());
                }
            }
        } catch (Exception e) {
            LOG.error(e.getMessage());
            e.printStackTrace();
        }
    }

    //VALID
    private static final String LOCS_18 = "<?xml version='1.0' ?>" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">" +
            "<name>Banik</name><coordinates>14.00276,50.00263,0\n" +
            "14.00254,50.003150000000005,0\n" +
            "14.002419999999999,50.00359,0\n" +
            "14.00221,50.00387,0\n" +
            "14.00179,50.00425,0\n" +
            "14.00155,50.0045,0\n" +
            "14.00141,50.00484,0\n" +
            "14.00134,50.00508,0\n" +
            "14.000910000000001,50.00555,0\n" +
            "14.00065,50.0058,0\n" +
            "14.00036,50.00621,0\n" +
            "14.00055,50.00649,0\n" +
            "14.001290000000001,50.00679,0\n" +
            "14.001550000000002,50.00697,0\n" +
            "14.001680000000002,50.007200000000005,0\n" +
            "14.001660000000003,50.007450000000006,0\n" +
            "14.001430000000003,50.00829,0\n" +
            "14.001110000000002,50.00976,0\n" +
            "</coordinates>" +
            "</kml>";

    // INVALID
    private static final String LOCS_18_NAME_TWICE = "<?xml version='1.0' ?>" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">" +
            "<name>Sparta</name>" +
            "<name>Banik</name><coordinates>14.00276,50.00263,0\n" +
            "14.00254,50.003150000000005,0\n" +
            "14.002419999999999,50.00359,0\n" +
            "14.00221,50.00387,0\n" +
            "14.00179,50.00425,0\n" +
            "14.00155,50.0045,0\n" +
            "14.00141,50.00484,0\n" +
            "14.00134,50.00508,0\n" +
            "14.000910000000001,50.00555,0\n" +
            "14.00065,50.0058,0\n" +
            "14.00036,50.00621,0\n" +
            "14.00055,50.00649,0\n" +
            "14.001290000000001,50.00679,0\n" +
            "14.001550000000002,50.00697,0\n" +
            "14.001680000000002,50.007200000000005,0\n" +
            "14.001660000000003,50.007450000000006,0\n" +
            "14.001430000000003,50.00829,0\n" +
            "14.001110000000002,50.00976,0\n" +
            "</coordinates>" +
            "</kml>";

    // INVALID
    private static final String LOCS_18_NO_NAME = "<?xml version='1.0' ?>" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">" +
            "<coordinates>14.00276,50.00263,0\n" +
            "14.00254,50.003150000000005,0\n" +
            "14.002419999999999,50.00359,0\n" +
            "14.00221,50.00387,0\n" +
            "14.00179,50.00425,0\n" +
            "14.00155,50.0045,0\n" +
            "14.00141,50.00484,0\n" +
            "14.00134,50.00508,0\n" +
            "14.000910000000001,50.00555,0\n" +
            "14.00065,50.0058,0\n" +
            "14.00036,50.00621,0\n" +
            "14.00055,50.00649,0\n" +
            "14.001290000000001,50.00679,0\n" +
            "14.001550000000002,50.00697,0\n" +
            "14.001680000000002,50.007200000000005,0\n" +
            "14.001660000000003,50.007450000000006,0\n" +
            "14.001430000000003,50.00829,0\n" +
            "14.001110000000002,50.00976,0\n" +
            "</coordinates>" +
            "</kml>";

    // INVALID
    private static final String EMPTY_LOCS = "<?xml version='1.0' ?>" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">" +
            "<coordinates></coordinates>" +
            "</kml>";

    // INVALID
    private static final String EMPTY_LOCS_NAME = "<?xml version='1.0' ?>" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">" +
            "<name>Slavia</name><coordinates></coordinates>" +
            "</kml>";

    // INVALID
    private static final String PARSE_ERROR = "<?xml version='1.0' ?>" +
            "<kml xmlns=\"http://www.opengis.net/kml/2.2\">" +
            "<name>Slavia</name><coordinates>alpha, beta, gama</coordinates>" +
            "</kml>";

    @Test
    public void parse18() throws Exception {
        XMLNomiResponseParser parser = new XMLNomiResponseParser();
        List<SegmentParsed> segments = parser.parseXMLNomiResponse(LOCS_18);
        assertEquals(1, segments.size());
        assertEquals(18, segments.get(0).getLocations().size());
        assertEquals("Banik", segments.get(0).getName());
    }

    @Test(expected = XMLParseException.class)
    public void parse18NameTwice() throws Exception {
        XMLNomiResponseParser parser = new XMLNomiResponseParser();
        List<SegmentParsed> segments = parser.parseXMLNomiResponse(LOCS_18_NAME_TWICE);
    }

    @Test(expected = XMLParseException.class)
    public void parse18NoName() throws Exception {
        XMLNomiResponseParser parser = new XMLNomiResponseParser();
        List<SegmentParsed> segments = parser.parseXMLNomiResponse(LOCS_18_NO_NAME);
    }

    @Test(expected = XMLParseException.class)
    public void parseEmptyLocs() throws Exception {
        XMLNomiResponseParser parser = new XMLNomiResponseParser();
        List<SegmentParsed> segments = parser.parseXMLNomiResponse(EMPTY_LOCS);
    }

    @Test(expected = XMLParseException.class)
    public void parseEmptyCoords() throws Exception {
        XMLNomiResponseParser parser = new XMLNomiResponseParser();
        List<SegmentParsed> segments = parser.parseXMLNomiResponse(EMPTY_LOCS_NAME);
    }

    @Test(expected = XMLParseException.class)
    public void parseError() throws Exception {
        XMLNomiResponseParser parser = new XMLNomiResponseParser();
        List<SegmentParsed> segments = parser.parseXMLNomiResponse(PARSE_ERROR);
    }

    @Test
    public void testBuildQuery() {
        String expected = "https://nomi.cz/strava/segmentsView.kml?south=50.00&north=50.03&west=14.00&east=14.03&token=" + tokenValid + "&type=ride";
        String decimalFormat = "%.2f";
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme("https")
                .host("nomi.cz")
                .path("/strava/segmentsView.kml");
        builder.queryParam("south", String.format(decimalFormat, 50.0));
        builder.queryParam("north", String.format(decimalFormat, 50.03001));
        builder.queryParam("west", String.format(decimalFormat, 14.00));
        builder.queryParam("east", String.format(decimalFormat, 14.0349));
        builder.queryParam("token", tokenValid);
        builder.queryParam("type", ActivityType.RIDE.label);

        String uri = builder.build().encode().toUriString();
        assertEquals(expected, uri);
        LOG.info(uri);
    }

    @Test(expected = ConstraintViolationException.class)
    public void testResponseInvalidBox() throws Exception {
        LatLonBox invalidBox = new LatLonBox(190, 190, 190, 190);
        String rawResponse = service.getSegments(invalidBox, ActivityType.RIDE, tokenValid);
    }

    @Test
    public void testResponseInvalidLargeBox() {
        double[] N = new double[]{10.0, 15.0, 10.0, 15.0};
        double[] S = new double[]{8.0, 10.0, 10.0, 10.0};
        double[] W = new double[]{9.0, 10.0, 10.0, 10.0};
        double[] E = new double[]{11.0, 10.0, 15.0, 15.0};
        boolean[] B = new boolean[]{false, true, true, true};
        for (int i = 0; i < 4; i++) {
            try {
                LatLonBox invalidBox = new LatLonBox(N[i], S[i], W[i], E[i]);
                String rawResponse = service.getSegments(invalidBox, ActivityType.RIDE, tokenValid);
            } catch (Exception e) {
                if (e instanceof ConstraintViolationException) {
                    LOG.error("CONSTRAINT");
                    LOG.error(e.getMessage());
                    LOG.error(e.toString());
                    assert (B[i]);
                } else {
                    LOG.error("ELSE");
                    LOG.error(e.getMessage());
                    LOG.error(e.getClass().getTypeName());
                    assertFalse(B[i]);
                }
            }
        }
    }

    @Test(expected = WebClientException.class)
    public void testResponseInvalidToken() throws Exception {
        LatLonBox validBox = new LatLonBox(50.00, 50.03, 14.00, 14.03);
        String rawResponse = service.getSegments (validBox, ActivityType.RIDE, tokenInvalid);
    }

    @Test
    public void testPrintWebClientException() throws Exception{
        LatLonBox validBox = new LatLonBox(50.00, 50.03, 14.00, 14.03);
        try {
            String rawResponse = service.getSegments(validBox, ActivityType.RIDE, tokenInvalid);
        } catch (WebClientException e) {
            LOG.error(e.getLocalizedMessage());
        }
    }
}

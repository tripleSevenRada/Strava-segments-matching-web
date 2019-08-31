package com.matching.segmentsmatching;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.LatLonPair;
import com.matching.segmentsmatching.resources.RequestedRoute;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RequestedRouteTests {

    // https://medium.com/@tbrouwer/testing-a-spring-boot-restful-service-c61b981cac61
    // https://phauer.com/2016/testing-restful-services-java-best-practices/
    // https://www.tutorialspoint.com/spring_boot/spring_boot_rest_controller_unit_test.htm
    // https://en.wikipedia.org/wiki/List_of_HTTP_status_codes

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private ObjectMapper mapper = new ObjectMapper();
    private String uri = "/match";

    private void setUpMvc() {
        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private String mapToJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    private <T> T mapFromJson(String json, Class<T> clazz)
            throws JsonParseException, IOException {
        return mapper.readValue(json, clazz);
    }

    private List<LatLonPair> getValidLocations(){
        List<LatLonPair> locations = new LinkedList<>();
        locations.add(new LatLonPair(1.0, 2.0));
        locations.add(new LatLonPair(3.0, 4.0));
        return locations;
    }

    private List<LatLonPair> getLargeRandomizedLocationList(int size){
        List<LatLonPair> locations = new LinkedList<>();
        Random rnd = new Random();
        for(int i = 0; i < size; i++){
            locations.add(new LatLonPair(rnd.nextDouble(), rnd.nextDouble()));
        }
        return locations;
    }

    private List<LatLonPair> getEmptyLocations(){
        return new LinkedList<LatLonPair>();
    }

    private class Setup{
        String token;
        List<LatLonPair> locations;
        int expectedStatusCode;
        MediaType mediaType;

        Setup(String token, List<LatLonPair> locations, MediaType mediaType, int expectedStatusCode) {
            this.token = token;
            this.locations = locations;
            this.mediaType = mediaType;
            this.expectedStatusCode = expectedStatusCode;
        }
    }

    private List<List<LatLonPair>> locations = new ArrayList<List<LatLonPair>>();
    private void populateLocations(){
        locations.add(getEmptyLocations());
        locations.add(getValidLocations());
        locations.add(getValidLocations());
        locations.add(null);
        locations.add(getEmptyLocations());
        locations.add(null);
        locations.add(getValidLocations());
        locations.add(getValidLocations());
        locations.add(getValidLocations());
        locations.add(getValidLocations());
        locations.add(getLargeRandomizedLocationList(100001));
    }

    private String [] tokens = new String[] {
            "b0d77cdd6000365506e7149b77283eb064f36982",
            "",
            "b0d77cdd6000365506e7149b77283eb064f36982",
            null,
            null,
            "",
            "x",
            "xx",
            "xxx",
            "b0d77cdd6000365506e7149b77283eb064f36982",
            "b0d77cdd6000365506e7149b77283eb064f36982"
    };

    private MediaType[] types = new MediaType[]{
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_FORM_URLENCODED,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8,
            MediaType.APPLICATION_JSON_UTF8
    };

    private int[] statuses = new int[]{415, 415, 415, 400, 400, 400, 400, 400, 400, 200, 400};

    private Setup getSetup(int i){
        if(locations.isEmpty()) populateLocations();
        return new Setup(tokens[i], locations.get(i), types[i], statuses[i]);
    }

    @Test
    public void testJSONResponseFromMatchingController() throws Exception {
        for (int i = 0; i < tokens.length; i++) {
            setUpMvc();
            Setup setup = getSetup(i);
            RequestedRoute r = new RequestedRoute(setup.locations, ActivityType.RIDE, setup.token);
            String inputJson = mapToJson(r);
            MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
                    .contentType(setup.mediaType).content(inputJson)).andReturn();
            assertEquals(setup.expectedStatusCode, mvcResult.getResponse().getStatus());
            String content = mvcResult.getResponse().getContentAsString();
            //TODO
            System.out.println("_index_" + i);
            if(inputJson.length() < 10000) System.out.println("_request content_" + inputJson);
            System.out.println("_response content_" + content);
        }
    }

}

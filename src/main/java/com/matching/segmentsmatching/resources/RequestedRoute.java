package com.matching.segmentsmatching.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;


public class RequestedRoute {

    // https://www.baeldung.com/javax-validation

    @NotEmpty(message = "locations must not be empty")
    @Size(max = 100000, message = "locations size must be less or equal than 100000")
    private List<Location> locations;
    private ActivityType type;
    @NotBlank(message = "token must not be blank")
    @Size(min = 40, max = 40, message = "token size must be 40") // TODO
    private String token;

    public RequestedRoute(List<Location> locations, ActivityType type, String token) {
        this.locations = locations;
        this.token = token;
        this.type = type;
    }

    // in web service
    // act t = case t of "ride" -> Riding; "run" -> Running; _ -> error "unknown activity type"

    public RequestedRoute(){}

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }
    public void setType(ActivityType type) { this.type = type; }
    public void setToken(String token) {
        this.token = token;
    }

    public List<Location> getLocations() {
        return locations;
    }
    public ActivityType getType() { return type; }
    public String getToken(){ return token; }

}

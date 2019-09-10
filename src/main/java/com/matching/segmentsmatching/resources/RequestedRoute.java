package com.matching.segmentsmatching.resources;

import com.matching.segmentsmatching.validators.EnumValid;
import org.springframework.lang.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class RequestedRoute {

    // https://www.baeldung.com/javax-validation

    @NonNull
    @NotEmpty(message = "locations must not be empty")
    @Size(max = 100000, message = "locations size must be less or equal than 100000")
    private List<LatLonPair> locations;
    @EnumValid(enumClass=ActivityType.class, ignoreCase=true, message = "only 'RIDE' or 'RUN' allowed, case ignored")
    @NotBlank
    private String type;
    @EnumValid(enumClass=MatchingScenario.class, ignoreCase=true, message = "only 'ROUTE', 'RECORDED' or 'LOOSE' allowed, case ignored")
    @NotBlank
    private String matchingScenario;
    @NotBlank(message = "token must not be blank")
    @Size(min = 40, max = 40, message = "token size must be 40")
    private String token;

    public RequestedRoute(List<LatLonPair> locations,
                          String type,
                          String matchingScenario,
                          String token) {
        this.locations = locations;
        this.type = type;
        this.matchingScenario = matchingScenario;
        this.token = token;
    }

    // in web service
    // act t = case t of "ride" -> Riding; "run" -> Running; _ -> error "unknown activity type"

    public RequestedRoute(){}

    public void setLocations(List<LatLonPair> locations) { this.locations = locations; }
    public void setType(String type) { this.type = type; }
    public void setMatchingScenario(String matchingScenario) { this.matchingScenario = matchingScenario; }
    public void setToken(String token) { this.token = token; }

    public List<LatLonPair> getLocations() {
        return locations;
    }
    public String getType() { return type; }
    public String getMatchingScenario() { return matchingScenario; }
    public String getToken(){ return token; }

    @Override
    public String toString() {
        return "RequestedRoute{" +
                "type=" + type +
                ", matchingScenario=" + matchingScenario +
                '}';
    }
}

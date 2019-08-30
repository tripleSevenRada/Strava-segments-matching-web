package com.matching.segmentsmatching.resources;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

public class SegmentParsed {

    @NotEmpty(message = "locations must not be empty")
    @Size(max = 100000, message = "locations size must be less or equal than 100000")
    private List<LatLonPair> locations;
    @NotBlank(message = "name must not be blank")
    private String name;

    public SegmentParsed(List<LatLonPair> locations, String name) {
        this.locations = locations;
        this.name = name;
    }

    public List<LatLonPair> getLocations() {
        return locations;
    }

    public String getName() {
        return name;
    }
}

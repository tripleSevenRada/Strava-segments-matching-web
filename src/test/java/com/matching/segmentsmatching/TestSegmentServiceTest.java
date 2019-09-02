package com.matching.segmentsmatching;

import com.matching.segmentsmatching.resources.ActivityType;
import com.matching.segmentsmatching.resources.LatLonBox;
import com.matching.segmentsmatching.services.TestSegmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSegmentServiceTest {

    @Autowired
    private TestSegmentService service;

    @Test
    public void testResponseFromTestSegmentService() throws Exception{
        int c = 0;
        while (c < 10){
            String response = service.getSegments(
                    new LatLonBox(50.0,49.0,14.0, 15.0),
                    ActivityType.RIDE,
                    "");
            assert(!response.isEmpty());
            assertEquals(17182, response.length());
            c++;
        }
    }

}

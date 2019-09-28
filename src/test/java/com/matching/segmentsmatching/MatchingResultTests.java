package com.matching.segmentsmatching;

import com.matching.segmentsmatching.resources.MatchingResult;
import com.matching.segmentsmatching.resources.SegmentDetected;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MatchingResultTests {

    @Test
    public void testOrganize(){
        SegmentDetected s1 = new SegmentDetected("name1",12.0,13.0,14.0,15.0,1,0);
        SegmentDetected s2 = new SegmentDetected("name1",12.0,13.0,14.0,15.0,1,0);
        SegmentDetected s3 = new SegmentDetected("name2",12.0,13.0,14.0,15.0,0,0);
        SegmentDetected s4 = new SegmentDetected("name2",12.0,13.0,14.0,15.0,0,0);

        MatchingResult result = new MatchingResult();
        result.add(s1);
        result.add(s2);
        result.add(s3);
        result.add(s4);

        result.organize();

        assertEquals(2, result.getSegmentsDetected().size());
        assertEquals("name2", result.getSegmentsDetected().get(0).getName());
        assertEquals("name1", result.getSegmentsDetected().get(1).getName());
    }
}

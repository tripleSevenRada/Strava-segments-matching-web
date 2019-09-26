package com.matching.segmentsmatching;

import com.matching.segmentsmatching.controllers.MatchingController;
import com.matching.segmentsmatching.resources.ActivityType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGetEnumGeneric {

    @Test
    public void testGenericConversionFromStringsToEnums(){
        String [] s = {"ride", "RIDE", "RiDe", "run"};
        Object [] t = {ActivityType.RIDE, ActivityType.RIDE, ActivityType.RIDE, ActivityType.RUN};
        MatchingController controller = new MatchingController(null, null, null);
        for (String value : s) {
            Object converted = controller.getEnum(
                    String::toLowerCase,
                    value,
                    ActivityType.class
            );
            assert (converted != null);
        }
    }

}

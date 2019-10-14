package Test;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

import Postcode.*;

/**
 * Unit tests for the Postcode.PostcodeService class.
 */
public class PostcodeServiceTest {
    private static final String [] postcodeArray = {"CB3 0FA", "CB11 3QW"};

    @Test
    /**
     * Test for validate method, input is a basic array of valid postcodes
     */
    public void testValidate() throws IOException, InvalidPostcodeException {
        for(String postcode : postcodeArray){
            assertTrue(PostcodeService.validate(postcode));
        }
    }

    @Test
    /**
     * Test for the lookup command, input is a basic array of valid postcodes in the East of England
     */
    public void testLookup() throws InvalidPostcodeException, IOException {
       for(String postcode : postcodeArray){
           PostcodeData data = PostcodeService.lookup(postcode);
           assertNotNull(data);
           Assert.assertEquals(data.getPostcode(), postcode);
           Assert.assertEquals(data.getCountry(), "England");
           Assert.assertEquals(data.getRegion(), "East of England");
        }
    }

    @Test
    /**
     * Test for the nearby command, input is a basic array of valid postcodes in the East of England
     */
    public void testNearest() throws InvalidPostcodeException, IOException {
        for(String postcode : postcodeArray){
            PostcodeData[] data = PostcodeService.nearest(postcode);
            assertNotNull(data);
            for(PostcodeData nearest : data) {
                //All postcodes should be in East of England to work.
                Assert.assertEquals(nearest.getCountry(), "England");
                Assert.assertEquals(nearest.getRegion(), "East of England");
            }
        }
    }

    @Test
    /**
     * Test for the lookup command, this postcode returns region:null in the API response
     */
    public void testNullRegion() throws InvalidPostcodeException, IOException {
        PostcodeData data = PostcodeService.lookup("CF3 3DB");
        Assert.assertEquals(data.getRegion(), "n/a");
    }


}
import org.junit.Test;

import static org.junit.Assert.*;

public class PostcodeServiceTest {
    private static final String [] postcodeArray = {"CB3 0FA", "CB11 3QW"};

    @Test
    public void testValidate(){
        for(String postcode : postcodeArray){
            assertTrue(PostcodeService.validate(postcode));
        }
    }

    @Test
    public void testLookup(){
       for(String postcode : postcodeArray){
           PostcodeData data = PostcodeService.lookup(postcode);
           assertNotNull(data);
           assertEquals(data.getPostcode(), postcode);
           assertEquals(data.getCountry(), "England");
           assertEquals(data.getRegion(), "East of England");
        }
    }

    @Test
    public void testNearest(){
        for(String postcode : postcodeArray){
            PostcodeData [] data = PostcodeService.nearest(postcode);
            assertNotNull(data);
            for(PostcodeData nearest : data) {
                //All postcodes should be in East of England to work.
                assertEquals(nearest.getCountry(), "England");
                assertEquals(nearest.getRegion(), "East of England");
            }
        }
    }


}
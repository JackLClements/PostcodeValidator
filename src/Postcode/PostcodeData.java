package Postcode;

import com.google.gson.JsonObject;

/**
 * Class abstraction of postcode metadata. Includes JSON parsing code.
 * Note - Due to time constraints it's better to just grab the available data rather than waste CPU cycles w/ a GSON obj.
 */
public class PostcodeData {
    private String postcode;
    private String country;
    private String region;

    /**
     * Values-based constructor
     */
    public PostcodeData(String postcode, String country, String region) {
        this.postcode = postcode;
        this.country = country;
        this.region = region;
    }

    //Accessor Methods
    public String getPostcode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public String getRegion() {
        return region;
    }

    //Mutator Methods

    /**
     * Create a new Postcode.PostcodeData object from a JsonObject response.
     * Assumption - The API is not returning malformed/unexpected data
     * Assumption - We only need the fields specified in the provided challenge document
     * @param obj JsonObject response from postcode lookup commands
     * @return new isntance of Postcode.PostcodeData
     */
    public static PostcodeData fromJSON(JsonObject obj) {
        String region = "n/a"; //can be null
        String postcode = obj.get("postcode").getAsString();
        String country = obj.get("country").getAsString();
        if (!obj.get("region").isJsonNull())
            region = obj.get("region").getAsString();
        return new PostcodeData(postcode, country, region);
    }
}

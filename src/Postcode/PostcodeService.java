package Postcode;

import Misc.HttpRequester;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.regex.Pattern;

/**
 * Set of methods in pseudo-wrapper code to access postcode.io API functionality
 */
public class PostcodeService {
    private static final Pattern pattern = Pattern.compile("[A-Za-z0-9 ]*");

    /**
     * Wraps the postcodes.io "Validate" command.
     * @param postcode String, postcode to validate
     * @return true if a valid postcode, otherwise false
     * @throws IOException If the HTTP Requests encounters any errors
     * @throws InvalidPostcodeException If the postcode supplied by the method call is null/empty
     */
    public static boolean validate(String postcode) throws IOException, InvalidPostcodeException {
        if(postcode == null || postcode.isEmpty())
            throw new InvalidPostcodeException("Postcode not supplied.");
        if(!pattern.matcher(postcode).matches())
            throw new InvalidPostcodeException(postcode);
        //shouldn't be any need for internal validation (e.g. regex)
        HttpRequester request = new HttpRequester("http://api.postcodes.io/postcodes/" + postcode + "/validate", "GET", null);
        JsonObject obj = wrapToJson(request.send());
        if (obj.has("result") && obj.get("result").getAsBoolean()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Wraps the postcodes.io basic lookup command
     * @param postcode String, postcode to lookup
     * @return Postcode.PostcodeData object
     * @throws InvalidPostcodeException If the postcode provided could not be validated
     * @throws IOException If the postcode supplied by the method call is null/empty
     */
    public static PostcodeData lookup(String postcode) throws InvalidPostcodeException, IOException {
        if (!validate(postcode))
            throw new InvalidPostcodeException(postcode);
        HttpRequester request = new HttpRequester("http://api.postcodes.io/postcodes/" + postcode, "GET", null);
        JsonObject obj = wrapToJson(request.send());
        return PostcodeData.fromJSON(obj.getAsJsonObject("result"));
    }

    /**
     * Wraps the postcodes.io "nearest" command
     * @param postcode String, postcode to lookup
     * @return Array of n PostocodeData results, where n is the initial postcode + no. of nearest postcodes.
     * @throws InvalidPostcodeException If the postcode provided could not be validated
     * @throws IOException If the postcode supplied by the method call is null/empty
     */
    public static PostcodeData[] nearest(String postcode) throws InvalidPostcodeException, IOException {
        if (!validate(postcode))
            throw new InvalidPostcodeException(postcode);
        //We could also use the response from the method, I've just chosen to do it this way
        HttpRequester request = new HttpRequester("http://api.postcodes.io/postcodes/" + postcode + "/nearest", "GET", null);
        JsonObject obj = wrapToJson(request.send());
        System.out.println(obj);
        JsonArray results = obj.getAsJsonArray("result");
        PostcodeData[] data = new PostcodeData[results.size()];
        for (int i = 0; i < results.size(); i++) {
            data[i] = PostcodeData.fromJSON(results.get(i).getAsJsonObject());
        }
        return data;
    }

    /**
     * Wraps a String query into a JsonObject
     * @param query query to wrap
     * @return JsonObject representation of String
     */
    private static JsonObject wrapToJson(String query) {
        return JsonParser.parseString(query).getAsJsonObject();
    }
}

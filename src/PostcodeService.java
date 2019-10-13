import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.SocketTimeoutException;

/**
 * Set of methods in pseudo-wrapper code to access postcode.io API functionality
 */
public class PostcodeService {
    //TODO - Check when you send an empty str, it gives you two exceptions, if we throw 2 types it should be one
    public static boolean validate(String postcode) {
        //shouldn't be any need for internal validation (e.g. regex)
        try {
            HttpRequester request = new HttpRequester("http://api.postcodes.io/postcodes/" + postcode + "/validate", "GET", null);
            JsonObject obj = wrapToJson(request.send());
            if (obj.get("result").getAsBoolean()) {
                return true;
            } else {
                return false;
            }
        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out. Please retry or check your connection settings.");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace(); //Ideally in all these catch blocks we'd trigger some kind of graceful fallover state
            return false;
        }
    }

    public static PostcodeData lookup(String postcode) {
        try {
            if (!validate(postcode))
                throw new InvalidPostcodeException(postcode);
            HttpRequester request = new HttpRequester("http://api.postcodes.io/postcodes/" + postcode, "GET", null);
            JsonObject obj = wrapToJson(request.send());
            return PostcodeData.fromJSON(obj.getAsJsonObject("result"));
        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out. Please retry or check your connection settings.");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static PostcodeData[] nearest(String postcode) {
        try {
            if (!validate(postcode))
                throw new InvalidPostcodeException(postcode);
            //We could also use the response from the method, I've just chosen to do it this way
            HttpRequester request = new HttpRequester("http://api.postcodes.io/postcodes/" + postcode + "/nearest", "GET", null);
            JsonObject obj = wrapToJson(request.send());
            JsonArray results = obj.getAsJsonArray("result");
            PostcodeData[] data = new PostcodeData[results.size()];
            for (int i = 0; i < results.size(); i++) {
                data[i] = PostcodeData.fromJSON(results.get(i).getAsJsonObject());
            }
            return data;
        } catch (SocketTimeoutException e) {
            System.out.println("Connection timed out. Please retry or check your connection settings.");
            e.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static JsonObject wrapToJson(String query) {
        return JsonParser.parseString(query).getAsJsonObject();
    }
}

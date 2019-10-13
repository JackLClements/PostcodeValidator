/**
 * Exception thrown when a given postcode isn't valid.
 */
public class InvalidPostcodeException extends Exception {
    private String postcode;

    public InvalidPostcodeException(String postcode){
        super("Invalid postcode: " + postcode);
    }

}

package Postcode;

/**
 * Exception thrown when a given postcode isn't valid.
 */
public class InvalidPostcodeException extends Exception {
    public InvalidPostcodeException(String postcode){
        super("Invalid postcode: " + postcode);
    }
}

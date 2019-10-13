import java.util.Scanner;

//Basic Design Notes
/*
We want to try and abstract all the URL stuff away from the end-programmer as much as possible.
Think the AdyenService classes.
It also makes sense to remove instances of classes away from method calls
- We could use a singleton w/ getInstance
- Or we could use a group of static methods loosely contained within a priv. class

We should also wrap up response data + use a class to parse it.
Do we want to use a JSON Library? - Yes, use GSON. Rather than muck around trying to parse entire blobs of JSON.

Before we decide on a design pattern let's think about how we'd want the input/output to look.

Input - a String, alphanumeric, generally fitting the valid postcode format
Output - An object, containing all the necessary information in a formatted, accessible manner,
from which further commands could potentially be called if need-be (or used to grab output for input)
Exceptions - InvalidPostcodeException "Postcode does not fit the accepted format."
Preprocessing for input? - We could use regex, but given the API already has a command
Security risks? - Potential URL invalidity wrt string building, look into that for the URL before the call
Other considerations - we potentially want to write a little HTTP wrapper just to make request construction less... painful

Java URL classes appear to encode " " to %20 automatically, so no need to parse those
 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Please enter a postcode: ");
        Scanner scan = new Scanner(System.in);
        PostcodeData[] data = PostcodeService.nearest(scan.nextLine());
        if(data == null){
            System.out.println("Could not retrieve postcode data. Please check stack trace for more info.");
            return;
        }
        for (int i = 0; i < data.length; i++) {
            if(i == 0)
                System.out.println("Your Postcode:");
            else
                System.out.println("Nearby Postcode:");
            System.out.println("Postcode: " + data[i].getPostcode());
            System.out.println("Country: " + data[i].getCountry());
            System.out.println("Region: " + data[i].getRegion());
        }
    }
}

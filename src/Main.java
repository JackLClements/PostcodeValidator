import Postcode.PostcodeData;
import Postcode.PostcodeService;

import java.util.Scanner;

//Basic Design Notes
/*
Initial Notes:
API Input - a String, alphanumeric, generally fitting the valid postcode format
API Output - JSON object, containing all the necessary information in a formatted, accessible manner,
from which further commands could potentially be called if need-be
Exceptions - Postcode.InvalidPostcodeException "Postcode does not fit the accepted format."
Preprocessing - need to ensure it's just alphanumeric, command can handle the rest

Assumptions:
- Only provided one postcode
- Connection is stable
- No need to use limited/shared resources
- Java URL class should always encode " " to %20 automatically

Extensions:
- Implement more commands
- Extend the Misc.HttpRequester class to be more robust w/ different requests
- Create a URL Builder enum with specific methods for creating and building a new API URL given just URL params (postcode)

Libraries:
- GSON
- JUnit

 */

public class Main {
    public static void main(String[] args) {
        System.out.println("Please enter a postcode: ");
        Scanner scan = new Scanner(System.in);
        try {
            PostcodeData[] data = PostcodeService.nearest(scan.nextLine());
            if (data == null) {
                System.out.println("Could not retrieve postcode data. Please check stack trace for more info.");
                return;
            }
            for (int i = 0; i < data.length; i++) {
                if (i == 0)
                    System.out.println("Your Postcode:");
                else
                    System.out.println("Nearby Postcode:");
                System.out.println("Postcode: " + data[i].getPostcode());
                System.out.println("Country: " + data[i].getCountry());
                System.out.println("Region: " + data[i].getRegion());
            }
        } catch (Exception e) {
            e.printStackTrace(); //Ideally we'd exit gracefully, but since this is a simple prototype, this will do.
        }

    }
}

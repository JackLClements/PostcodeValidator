import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Quick and dirty URL wrapper to abstract all the complexities of a http request away from the main code
 */
public class HttpRequester { //TODO - give this a better name/structure, maybve make into a builder
    private static final int TIMEOUT_MILLIS = 5000;
    private URL url;
    private String method;
    private String body;
    //TODO - headers

    public HttpRequester(String url, String method, String body) throws MalformedURLException { //handle exception in try/catch?
        this.url = new URL(url);
        this.method = method;
        this.body = body;
    }

    public String send() throws IOException { //TODO - something w/ the response
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod(method);
        //for header in headers...
        //request.setProperty("content-type", etc.
        if(body != null){
            OutputStream os = request.getOutputStream();
            os.write(body.getBytes()); //TODO - char format? UTF-8?
            os.close();
        }
        request.setConnectTimeout(TIMEOUT_MILLIS); //Timeout in ms.
        //send
        request.connect(); //you can also set other params this way, might be worth wrapping all this in a service to streamline

        return new BufferedReader(new InputStreamReader(request.getInputStream())).readLine(); //TODO - determine format (UTF-8)?
    }
}

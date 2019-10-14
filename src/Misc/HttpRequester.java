package Misc;

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
public class HttpRequester {
    private static final int TIMEOUT_MILLIS = 5000;
    private URL url;
    private String method;
    private String body;

    /**
     * Basic value based constructor
     * @param url    URL of request
     * @param method method (e.g. GET, POST), no validation due to time constraints
     * @param body   body of request, if any. Null if GET request.
     * @throws MalformedURLException if the URL is invalid.
     */
    public HttpRequester(String url, String method, String body) throws MalformedURLException { //handle exception in try/catch?
        this.url = new URL(url);
        this.method = method;
        this.body = body;
    }

    /**
     * Setup connection w/ relevant headers/body/etc. and send, return response
     * @return String response
     * @throws IOException
     */
    public String send() throws IOException {
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.setRequestMethod(method);
        if (method.equals("POST")) {
            request.setRequestProperty("Content-Type", "application/json");
        }
        if (body != null) {
            OutputStream os = request.getOutputStream();
            os.write(body.getBytes()); //Note, we're not concerned with character format (UTF-8, etc.) for now.
            os.close();
        }
        request.setConnectTimeout(TIMEOUT_MILLIS); //Timeout in ms.
        request.connect();
        return new BufferedReader(new InputStreamReader(request.getInputStream())).readLine();
    }
}

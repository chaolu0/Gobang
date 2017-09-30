package network.undirectional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class HttpService {
    private static final String BASEURL = "http://183.175.12.183:8080/";
    public static String get(String url, String params) throws IOException {
        url =BASEURL + url  + params;
        System.out.println("url = " + url);
        URL mUrl = null;
        try {
            mUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection conn = mUrl.openConnection();

        conn.connect();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = "",line;
        while((line = reader.readLine())!=null){
            res += line;
        }
        return res;
    }
}

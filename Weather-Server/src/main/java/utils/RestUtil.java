package utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestUtil {

    public static String httpGet(String urlStr) {

        String response;

        try {
            URL url = new URL(urlStr);
            HttpURLConnection conn =
                    (HttpURLConnection) url.openConnection();

            conn.setConnectTimeout(5000);

            if (conn.getResponseCode() != 200) {
                throw new IOException(String.valueOf(conn.getResponseCode()));
            }

            // Buffer the result into a string
            StringBuilder sb = new StringBuilder();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));

            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            response = sb.toString();

        } catch (IOException e) {
            response = "Error " + e.getMessage();
        }

        return response;
    }
}

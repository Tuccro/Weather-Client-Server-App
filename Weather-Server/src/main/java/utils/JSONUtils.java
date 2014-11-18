package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {

    public static final String CITY_NOT_FOUND = "City not found";

    public static boolean isJSONValid(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public static String openWeatherJSONParser(JSONObject json) {

        if (json.has("cod") && json.getInt("cod") != 200) {
            return CITY_NOT_FOUND;
        }

        StringBuilder sb = new StringBuilder();

        try {
            sb.append("Now in " + json.getString("name") + "\n");
            sb.append("temperature: " +
                            Utils.tempKelvinToCelsius(json.getJSONObject("main").getDouble("temp")) +
                            "°С \n"
            );
            sb.append("wind: " + json.getJSONObject("wind").getInt("speed") + " mps\n");
            sb.append("pressure: " + json.getJSONObject("main").getInt("pressure") + " hPa");

        } catch (JSONException e) {

        }

        return sb.toString();
    }
}

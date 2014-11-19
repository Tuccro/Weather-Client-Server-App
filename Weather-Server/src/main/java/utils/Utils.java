package utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

    public static double tempKelvinToCelsius(double temperature) {
        return temperature - 273.15;
    }

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

}

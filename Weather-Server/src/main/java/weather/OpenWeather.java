package weather;

import org.json.JSONException;
import org.json.JSONObject;
import utils.RestUtil;
import utils.Utils;

public class OpenWeather {

    public static final int RENEWAL_PERIOD = 60000;
    private String serviceLink = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String city = "Dnipropetrovsk";
    private JSONObject json = null;
    private long lastUpdateTime;

    private int temperature;
    private int wind;
    private int pressure;

    boolean trueInfo = false;

    public OpenWeather(String city) {
        this.city = city;
    }

    public OpenWeather() {
    }

    public String getWeather() {

        if (System.currentTimeMillis() - lastUpdateTime > RENEWAL_PERIOD || !trueInfo) {
            update();
            if (json != null) parseJSON();
            lastUpdateTime = System.currentTimeMillis();
        }

        if (pressure == 0) {
            return "Sorry. No information about the weather.";
        }


        return ("Now in " + city + "\n") +
                "temperature: " + temperature + "°С \n" +
                "wind: " + wind + " mps\n" +
                "pressure: " + pressure + " hPa";
    }

    public void update() {
        String requestURL = serviceLink.concat(city);
        String responseFromServer = RestUtil.httpGet(requestURL);

        if (!Utils.isJSONValid(responseFromServer)) {
            System.out.println("Error: " + responseFromServer);
        } else {
            json = new JSONObject(responseFromServer);
            lastUpdateTime = System.currentTimeMillis();
        }
    }

    private void parseJSON() {

        try {
            temperature = (int) Utils.tempKelvinToCelsius(json.getJSONObject("main").getDouble("temp"));
            wind = json.getJSONObject("wind").getInt("speed");
            pressure = json.getJSONObject("main").getInt("pressure");
            trueInfo = true;
        } catch (JSONException e) {
            trueInfo = false;
            System.out.println(e.getMessage());
        }
    }

}

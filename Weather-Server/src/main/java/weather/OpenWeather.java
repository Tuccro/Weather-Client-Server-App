package weather;

import org.json.JSONObject;
import utils.JSONUtils;
import utils.RestUtil;

public class OpenWeather {

    private String serviceLink = "http://api.openweathermap.org/data/2.5/weather?q=";
    private String city = "Dnipropetrovsk";

    public OpenWeather(String city) {
        this.city = city;
    }

    public OpenWeather() {
    }

    public String getWeather() {
        String requestURL = serviceLink.concat(city);
        String responseFromServer = RestUtil.httpGet(requestURL);

        if(!JSONUtils.isJSONValid(responseFromServer)) {
            return responseFromServer;
        }

        JSONObject json = new JSONObject(responseFromServer);

        return JSONUtils.openWeatherJSONParser(json);
        //return json.toString();
    }
}

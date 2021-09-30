package bot.necessities.util;

import bot.necessities.main.Main;

import java.net.MalformedURLException;
import java.net.URL;

public class WeatherApi {

    public static URL createUrl(String city, Boolean aqi) {

        String stringAqi;

        StringBuilder callUrl = new StringBuilder("http://api.weatherapi.com/v1");

        callUrl.append("/current.json");
        callUrl.append("?key=").append(Main.KEY);
        callUrl.append("&q=").append(city);


        if (aqi) {
            stringAqi = "yes";
        } else {
            stringAqi = "no";
        }

        callUrl.append("&aqi=").append(stringAqi);

        try {
            return new URL(callUrl.toString().replace(" ", "+"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }

    }

}

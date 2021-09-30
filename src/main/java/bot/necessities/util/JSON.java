package bot.necessities.util;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class JSON {

    public static JSONObject fromUrl(URL url) throws Exception {

        HttpURLConnection con = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
        con.setRequestMethod("GET");

        if (con.getResponseCode() == 400) {
            return null;
        }else {
            StringBuilder inline = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()) {
                inline.append(scanner.nextLine());
            }
            scanner.close();

            JSONParser parser = new JSONParser();
            JSONObject data = (JSONObject) parser.parse(inline.toString());
            return data;
        }
    }
}

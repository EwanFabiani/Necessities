package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.weather.WeatherApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.awt.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

public class WeatherCommand extends Command {
    @Override
    public String getAlias() {
        return "weather";
    }

    @Override
    public String getDescription() {
        return "Get the Weather in a specified city";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "weather [city]";
    }

    @Override
    public Category getCategory() {
        return Category.WEATHER;
    }

    public String city = null;

    @SuppressWarnings("StringConcatenationInLoop")
    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        if (!args[0].isBlank()) {

            city = "";

            for (String s : args) {
                city = city + " " + s;
            }

            URL url = WeatherApi.createUrl(city, false);

            HttpURLConnection con = (HttpURLConnection) Objects.requireNonNull(url).openConnection();
            con.setRequestMethod("GET");

            if (con.getResponseCode() == 400) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setTitle("Invalid Request!");
            }else {
                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                while(scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }
                scanner.close();

                JSONParser parser = new JSONParser();
                JSONObject data = (JSONObject) parser.parse(inline.toString());

                JSONObject location = (JSONObject) data.get("location");
                JSONObject current = (JSONObject) data.get("current");
                JSONObject condition = (JSONObject) current.get("condition");

                EmbedBuilder eb = new EmbedBuilder();
                if (Float.parseFloat(current.get("temp_c").toString()) <= 5) { eb.setColor(Color.WHITE); }
                if (Float.parseFloat(current.get("temp_c").toString()) > 5 && Float.parseFloat(current.get("temp_c").toString()) <= 25 ) { eb.setColor(Color.CYAN); }
                if (Float.parseFloat(current.get("temp_c").toString()) > 25 && Float.parseFloat(current.get("temp_c").toString()) <= 30) { eb.setColor(Color.YELLOW); }
                if (Float.parseFloat(current.get("temp_c").toString()) > 30 && Float.parseFloat(current.get("temp_c").toString()) <= 35) { eb.setColor(Color.ORANGE); }
                if (Float.parseFloat(current.get("temp_c").toString()) > 35 && Float.parseFloat(current.get("temp_c").toString()) <= 45) { eb.setColor(Color.RED); }
                if (Float.parseFloat(current.get("temp_c").toString()) > 45) { eb.setColor(Color.BLACK); }

                String icon = condition.get("icon").toString();
                icon = "https:" + icon;

                eb.setTitle("Weather & Temperature - " + location.get("name") + ", " + location.get("country"));
                eb.setDescription(condition.get("text").toString() + " " + icon);
                eb.addField("Temperature: ", current.get("temp_c") + "°C / " + current.get("temp_f") + "°F", false);

                msg.reply(eb.build()).queue();
            }



        }else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("No Arguments!");
            msg.reply(eb.build()).queue();
        }
    }
}

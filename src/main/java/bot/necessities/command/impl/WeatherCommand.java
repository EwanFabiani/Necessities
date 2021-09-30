package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.util.JSON;
import bot.necessities.util.WeatherApi;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;

import java.awt.*;
import java.net.URL;

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
        return Category.INFORMATION;
    }

    public String city = null;

    @SuppressWarnings("StringConcatenationInLoop")
    @Override
    public void onCommand(String command, String[] args, Message msg)  throws Exception {
        if (!args[0].isBlank()) {

            String city = "";

            for (String s : args) {
                city = city + " " + s;
            }

            URL url = WeatherApi.createUrl(city, false);

            JSONObject data = JSON.fromUrl(url);

            if (data == null) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setAuthor("Invalid Request!");
                eb.setDescription("An error occurred! The request was invalid!");
                msg.reply(eb.build()).queue();
                return;
            }


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
            eb.setDescription(condition.get("text").toString());
            eb.addField("Temperature: ", current.get("temp_c") + "°C / " + current.get("temp_f") + "°F", false);
            eb.setThumbnail(icon);
            eb.setFooter("Powered by WeatherApi");

            msg.reply(eb.build()).queue();

        }else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.RED);
            eb.setTitle("No Arguments!");
            msg.reply(eb.build()).queue();
        }
    }
}

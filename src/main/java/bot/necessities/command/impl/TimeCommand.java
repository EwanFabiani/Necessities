package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.util.JSON;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;

import java.awt.*;
import java.net.URL;

public class TimeCommand extends Command {

    @Override
    public String getAlias() {
        return "time";
    }

    @Override
    public String getDescription() {
        return "Get Time Around the World";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "time";
    }

    @Override
    public Category getCategory() {
        return Category.INFORMATION;
    }

    @Override
    public Permission requiredPermission() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        EmbedBuilder eb = new EmbedBuilder();

        URL EST = new URL("http://worldclockapi.com/api/json/est/now");
        URL UTC = new URL("http://worldclockapi.com/api/json/utc/now");

        JSONObject est =  JSON.fromUrl(EST);
        JSONObject utc =  JSON.fromUrl(UTC);

        String esttime = est.get("currentDateTime").toString().split("T")[1].split("-")[0];
        String utctime = utc.get("currentDateTime").toString().split("T")[1].split("Z")[0];
        String[] esttimeh = esttime.split(":");
        String[] utctimeh = utctime.split(":");

        eb.setAuthor("Time Around the World");
        eb.addField("EST", esttime + " / " + (Integer.parseInt(esttimeh[0]) > 12 ? Integer.parseInt(esttimeh[0]) - 12 + ":" + esttimeh[1] + "am" : esttimeh[0] + ":" + esttimeh[1] + " am"), false);
        eb.addField("UTC", utctime + " / " + (Integer.parseInt(utctimeh[0]) > 12 ? Integer.parseInt(utctimeh[0]) - 12 + ":" + utctimeh[1] + "am" : utctimeh[0] + ":" + utctimeh[1] + " am"), false);
        eb.setColor(Color.blue);
        eb.setFooter("Powered by WorldClockApi");

        msg.reply(eb.build()).queue();


    }
}

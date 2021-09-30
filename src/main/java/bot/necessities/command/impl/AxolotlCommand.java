package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.util.JSON;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import org.json.simple.JSONObject;

import java.awt.*;
import java.net.URL;

public class AxolotlCommand extends Command {

    @Override
    public String getAlias() {
        return "axolotl";
    }

    @Override
    public String getDescription() {
        return "Get Axolotl Pictures!";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "axolotl";
    }

    @Override
    public Category getCategory() {
        return Category.FUN;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        URL url = new URL("https://axoltlapi.herokuapp.com/");
        JSONObject data = JSON.fromUrl(url);
        String urlimg = ((String) data.get("url"));


        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Here is your Axolotl Picture!");
        eb.setImage(urlimg);
        eb.setDescription((CharSequence) data.get("facts"));
        eb.setColor(Color.green);
        eb.setFooter("Powered by TheAxolotlApi");

        msg.reply(eb.build()).queue();

    }
}

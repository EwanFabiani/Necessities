package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;

public class AboutCommand extends Command {
    @Override
    public String getAlias() {
        return "about";
    }

    @Override
    public String getDescription() {
        return "Get some information about Necessities";
    }

    @Override
    public String getSyntax() {
        return "-about";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.blue);
        eb.setAuthor("About Necessities " + Main.VERSION);
        eb.setDescription("Made by <@541558942095114251>");
        eb.addField("Discord API used: ", "Java Discord API", false);
        eb.addField("If you need any help join our discord server!", "Use -discord to get the link!", false);
        eb.setFooter("Requested by " + msg.getAuthor().getName());
        eb.setTimestamp(Instant.now());
        msg.getChannel().sendMessage(eb.build()).queue();
    }
}

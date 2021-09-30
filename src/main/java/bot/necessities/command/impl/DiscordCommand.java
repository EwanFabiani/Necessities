package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class DiscordCommand extends Command {
    @Override
    public String getAlias() {
        return "discord";
    }

    @Override
    public String getDescription() {
        return "Sends a link to the bots discord server";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "discord";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(128,0,128));
        eb.setAuthor("Click me to join the Necessities Discord Server!", "https://scam.com"); // https://discord.gg/KKQdtFfvMj
        eb.setFooter("Necessities Bot", Main.api.getSelfUser().getEffectiveAvatarUrl());
        msg.getChannel().sendMessage(eb.build()).queue();

    }
}
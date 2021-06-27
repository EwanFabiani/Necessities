package bot.necessities.command.impl;

import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;

public class InviteCommand extends Command {
    @Override
    public String getAlias() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Sends an invite link for the bot";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "invite";
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(128,0,128));
        eb.setAuthor("Click me to invite the bot!", "https://discord.com/api/oauth2/authorize?client_id=708697252410949693&permissions=8&scope=bot");
        eb.setFooter("Necessities Bot", Main.api.getSelfUser().getEffectiveAvatarUrl());
        msg.getChannel().sendMessage(eb.build()).queue();

    }
}

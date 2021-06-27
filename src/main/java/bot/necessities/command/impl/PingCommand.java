package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.time.Instant;

public class PingCommand extends Command {
    @Override
    public String getAlias() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Gets the Ping of the Bot";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "ping";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        long ping = Main.api.getGatewayPing();
        MessageChannel msgch = msg.getChannel();
        EmbedBuilder eb = new EmbedBuilder();
        if (ping < 175) {
            eb.setColor(Color.green);
        }else if (ping < 250) {
            eb.setColor(Color.yellow);
        }else {
            eb.setColor(Color.red);
        }
        eb.setTitle("Ping");
        eb.addField("Bot Ping", ping + "ms", false);
        eb.setFooter("Requested by " + msg.getAuthor().getName());
        eb.setTimestamp(Instant.now());

        msgch.sendMessage(eb.build()).queue();
    }
}
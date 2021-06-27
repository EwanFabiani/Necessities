package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;

public class ServerInfoCommand extends Command {
    @Override
    public String getAlias() {
        return "serverinfo";
    }

    @Override
    public String getDescription() {
        return "Get the info of the Server!";
    }

    @Override
    public String getSyntax() {
        return "-serverinfo";
    }

    @Override
    public Category getCategory() {
        return Category.INFORMATION;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        System.out.println("Arrived here");
        Guild server = msg.getGuild();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.blue);
        eb.setAuthor("Server Info - " + server.getName());
        eb.setThumbnail(server.getIconUrl());
        eb.setFooter("Requested by " + msg.getAuthor().getName());
        eb.setTimestamp(Instant.now());
        eb.addField("Members: ", String.valueOf(server.getMemberCount()), false);
        eb.addField("Owner:", Objects.requireNonNull(server.getOwner()).getUser().getName() + "#" + server.getOwner().getUser().getDiscriminator(), false);
        eb.addField("ID: ", server.getId(), false);
        eb.addField("Boosts: ", String.valueOf(server.getBoostCount()), false);
        msg.getChannel().sendMessage(eb.build()).queue();

    }
}

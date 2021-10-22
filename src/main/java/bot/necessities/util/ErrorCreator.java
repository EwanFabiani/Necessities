package bot.necessities.util;

import bot.necessities.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;

public class ErrorCreator {

    public static void permissionError(Message msg, Permission perm) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setAuthor("Missing Permission!");
        eb.setDescription("You need the permission " + perm.getName() + " to use this command!");
        eb.setTimestamp(Instant.now());
        msg.reply(eb.build()).queue();

    }

    public static void missingArgumentsError(Message msg, Command cmd, String missingArgument) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setAuthor("Missing Arguments!");
        eb.setDescription(missingArgument);
        eb.addField("Syntax: ", cmd.getSyntax(), true);
        eb.setTimestamp(Instant.now());
        msg.reply(eb.build()).queue();

    }

    public static void invalidRequestError(Message msg) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setAuthor("Invalid Request Error!");
        eb.setDescription("There was an unexpected error while trying to perform your request!");
        eb.setFooter("Sorry for the inconvenience");
        eb.setTimestamp(Instant.now());
        msg.reply(eb.build()).queue();

    }

    public static void invalidArgumentError(Message msg, String str) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.RED);
        eb.setAuthor("Invalid Argument");
        eb.setDescription(str);
        eb.setTimestamp(Instant.now());
        msg.reply(eb.build()).queue();

    }

}

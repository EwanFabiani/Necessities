package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.command.CommandManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;
import java.util.Locale;

public class HelpCommand extends Command {
    @Override
    public String getAlias() {
        return "help";
    }

    @Override
    public String getDescription() {
        return "Show the bot Commands";
    }

    @Override
    public String getSyntax() {
        return "-help [category/command]";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (args[0].isBlank()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.white);
            eb.setAuthor("Help - Necessities");
            eb.setDescription("Do -help [category] for category help");
            eb.addField("Moderation", "Commands to help you moderate your server!", false);
            eb.addField("Information", "Get Information through commands!", false);
            eb.addField("Fun", "Fun Commands!", false);
            eb.addField("Bot", "Necessities related Commands!", false);
            eb.setFooter("Requested by " + msg.getAuthor().getName());
            eb.setTimestamp(Instant.now());
            msg.getChannel().sendMessage(eb.build()).queue();
        }else {
            switch(args[0].toLowerCase()) {
                case "moderation":
                    sendCategoryMessage(Category.MODERATION, msg);
                    break;
                case "information": case "info":
                    sendCategoryMessage(Category.INFORMATION, msg);
                    break;
                case "fun":
                    sendCategoryMessage(Category.FUN, msg);
                    break;
                case "bot":
                    sendCategoryMessage(Category.BOT, msg);
                    break;
                default:
                    checkForCommand(args[0].toLowerCase(), msg);
                    break;
            }
         }

    }

    public void sendCategoryMessage(Category category, Message msg) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.white);
        eb.setAuthor("Help - " + category.toString());
        eb.setDescription("Do -help [command] to get the commands Syntax");
        for (Command c : CommandManager.getCommands()) {
            if (c.getCategory().equals(category)) {
                eb.addField(c.getAlias(), c.getDescription(), false);
            }
        }
        eb.setFooter("Requested by " + msg.getAuthor().getName());
        eb.setTimestamp(Instant.now());
        msg.getChannel().sendMessage(eb.build()).queue();
    }

    public void checkForCommand(String str, Message msg) {
        for (Command c : CommandManager.getCommands()) {
            if (c.getAlias().equals(str)) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.white);
                eb.setAuthor("Help - " + c.getAlias());
                eb.setDescription(c.getDescription());
                eb.addField("Category: ", c.getCategory().toString(), false);
                eb.addField("Syntax: ", c.getSyntax(), false);
                msg.getChannel().sendMessage(eb.build()).queue();
            }
        }
    }

}

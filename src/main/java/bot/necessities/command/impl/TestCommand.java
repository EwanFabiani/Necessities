package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import bot.necessities.main.Main;

import java.awt.*;

public class TestCommand extends Command {
    @Override
    public String getAlias() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Test Command for test Purposes only";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "test";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (!(msg.getAuthor().getIdLong() == 541558942095114251L)) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setAuthor("You cannot use this command");
            eb.setDescription("This command was made only to be used by the bot owner");
            msg.reply(eb.build()).queue();
        }else {

            System.out.println(Main.api.getGuildById(890983550713749554L).getMembers());
        }

    }
}

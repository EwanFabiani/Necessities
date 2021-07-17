package bot.necessities.comfirmcommands.impl;

import bot.necessities.comfirmcommands.ConfirmCommand;
import bot.necessities.comfirmcommands.ConfirmCommandManager;
import bot.necessities.comfirmcommands.ConfirmCommandType;
import bot.necessities.command.Category;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;

public class BugCommand extends ConfirmCommand {

    @Override
    public String getAlias() {
        return "bug";
    }

    @Override
    public String getDescription() {
        return "Report Bugs";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "bug";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public MessageEmbed onCommand(String command, String[] args, Message msg) throws Exception {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Test");
        eb.setColor(Color.yellow);

        return eb.build();
    }

    @Override
    public void onConfirm(ConfirmCommandType command) throws Exception {

        System.out.println("This method is getting called!");

    }
}

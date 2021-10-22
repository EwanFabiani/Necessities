package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.util.ErrorCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ClearCommand extends Command {

    @Override
    public String getAlias() {
        return "clear";
    }

    @Override
    public String getDescription() {
        return "Clears Messages from a channel";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "clear [MessageCount]";
    }

    @Override
    public Category getCategory() {
        return Category.MODERATION;
    }

    @Override
    public Permission requiredPermission() {
        return Permission.MESSAGE_MANAGE;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (args[0].equals("")) {
            ErrorCreator.missingArgumentsError(msg, this, "You need to input how many messages you want to clear!");
        }else{
            try {
                int clear = Integer.parseInt(args[0]);
                MessageChannel msgch = msg.getChannel();
                if (clear < 1 || clear > 99) {
                    ErrorCreator.invalidArgumentError(msg, "You used \"" + args[0] + "\" instead of a number from 1-99!");
                }else {
                    clear++;
                    List<Message> messages = msgch.getHistory().retrievePast(clear).complete();
                    msgch.purgeMessages(messages);
                    Thread.sleep(200);
                    clear--;
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(Color.blue);
                    eb.setAuthor("Successfully deleted " + clear + " Messages!");
                    eb.setFooter("Cleared by " + msg.getAuthor().getName());
                    eb.setTimestamp(Instant.now());
                    msgch.sendMessage(eb.build()).queue((result) -> result.delete().queueAfter(2, TimeUnit.SECONDS));

                }
            }catch (Exception e) {
                ErrorCreator.invalidArgumentError(msg, "You used \"" + args[0] + "\" instead of a number from 1-99!");
            }
        }
    }
}

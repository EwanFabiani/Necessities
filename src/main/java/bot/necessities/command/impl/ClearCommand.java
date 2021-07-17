package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.exceptions.ContextException;

import java.awt.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
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
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        if (Objects.requireNonNull(msg.getMember()).hasPermission(Permission.MESSAGE_MANAGE)) {
            if (args[0].equals("")) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(new Color(173,216,230));
                eb.setAuthor("You need to input how many messages you want to clear!");
                eb.setDescription("Syntax: " + this.getSyntax());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Error by " + msg.getAuthor().getName());
                msg.getChannel().sendMessage(eb.build()).queue((result) -> {
                    result.delete().queueAfter(10, TimeUnit.SECONDS);
                    msg.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            }else{
                try {
                    int clear = Integer.parseInt(args[0]);
                    MessageChannel msgch = msg.getChannel();
                    if (clear < 1 || clear > 100) {
                        EmbedBuilder eb = new EmbedBuilder();
                        eb.setColor(new Color(173,216,230));
                        eb.setAuthor("You need to use a Number for Messages to clear");
                        eb.setDescription("You used \"" + args[0] + "\" instead of a number from 1-99!");
                        eb.setTimestamp(Instant.now());
                        eb.setFooter("Error by " + msg.getAuthor().getName());
                        msg.getChannel().sendMessage(eb.build()).queue();
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
                        msgch.sendMessage(eb.build()).queue((result) -> result.delete().queueAfter(3, TimeUnit.SECONDS));

                    }
                }catch (Exception e) {
                    EmbedBuilder eb = new EmbedBuilder();
                    eb.setColor(new Color(173,216,230));
                    eb.setAuthor("You need to use a Number for Messages to clear");
                    eb.setDescription("You used \"" + args[0] + "\" instead of a number from 1-99!");
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Error by " + msg.getAuthor().getName());
                    msg.getChannel().sendMessage(eb.build()).queue();
                    e.printStackTrace();
                }
            }
        }else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setAuthor("Missing Permissions!");
            eb.setDescription("You don't have the Permission to use this Command");
            eb.addField("Missing Permission:", "MESSAGE_MANAGE", false);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Permission Error by " + msg.getAuthor().getName());
            msg.getChannel().sendMessage(eb.build()).queue((result) -> {
                result.delete().queueAfter(10, TimeUnit.SECONDS);
                msg.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }
}

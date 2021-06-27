package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;

public class BugCommand extends Command {
    @Override
    public String getAlias() {
        return "bug";
    }

    @Override
    public String getDescription() {
        return "Command to Manage Bugs";
    }

    @Override
    public String getSyntax() {
        return "-bug {report} {bug}";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        String report = "";
        if (args[0].isBlank()) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.yellow);
            eb.setAuthor("Welcome To Bug Reports!");
            eb.setDescription("Since the bot is still under heavy development, there can be some unnoticed bugs! If you found an error on the Necessities bot, report it! In your bug report you should tell us what command caused an error and what error it was so we can fix it as quickly as possible. Errors appear most frequently with Error Messages. Errors can also happen when the bot is online but doesn't respond to a specific command! To report a bug, simply type \"-bug report [message]\". If you think the bug is very important, you can also contact me directly **TechCrafter_#1179** by joining the discord server (-discord) and messaging me! Any abuse of this command will be punished by permanent bot ban!");
            eb.setFooter("Requested by " + msg.getAuthor().getName());
            eb.setTimestamp(Instant.now());
            msg.getChannel().sendMessage(eb.build()).queue();
        }else if(args[0].equalsIgnoreCase("report")) {
            for (int i = 1; i < args.length; i++) {
                report = report + " " + args[i];
            }
            sendBugMessage(msg, report);
        }

    }

    public void sendBugMessage(Message msg, String report) {
        TextChannel reportchannel = Main.api.getTextChannelById(858715436144132126L);
        User reporter = msg.getAuthor();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Bug Report");
        eb.setColor(Color.YELLOW);
        eb.setDescription("Bug Report from " + reporter.getName() + "#" + reporter.getDiscriminator() + " ID: " + reporter.getId());
        eb.addField("Report Message: ", report, false);
        eb.setTimestamp(Instant.now());
        reportchannel.sendMessage(eb.build()).queue();

        EmbedBuilder eb2 = new EmbedBuilder();
        eb2.setAuthor("Bug Report Successfully sent");
        eb2.setColor(Color.YELLOW);
        eb2.setDescription("You Successfully sent your bug report!");
        eb2.addField("Bug Report Message: ", report, false);
        eb2.setFooter("Any abuse of bug reports will result in a permanent Bot ban!");
        eb2.setTimestamp(Instant.now());
        msg.getChannel().sendMessage(eb2.build()).queue();
    }
}

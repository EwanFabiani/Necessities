package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.util.ErrorCreator;
import bot.necessities.util.MiscFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;

public class KickCommand extends Command {
    @Override
    public String getAlias() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick someone out of your server";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "kick [user] [reason]";
    }

    @Override
    public Category getCategory() {
        return Category.MODERATION;
    }

    @Override
    public Permission requiredPermission() {
        return Permission.KICK_MEMBERS;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (args[0].isBlank()) {
            ErrorCreator.missingArgumentsError(msg, this, "You didn't specify who you want to kick!");
        }else {
            Member m = MiscFunctions.getUserFromString(args[0], msg);
            if (m == null) ErrorCreator.invalidArgumentError(msg, "The User \"" + args[0] + "\" isn't a valid user!");
            else checkWithMemberResult(m, msg, args);
        }
    }

    public void kickMember (Member m, String[] args, Message msg) {
        StringBuilder reason = new StringBuilder();

        if (args == null) {
            m.kick().queue();
            System.out.println("Kicked " + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " from the Server: " + m.getGuild().getName() + " - ID: " + m.getGuild().getId() + " for the Reason: null");
            sendKickMessage(msg, m, "None");
        }else {

            for (int i = 1; i < args.length; i++) {
                reason.append(" ").append(args[i]);
            }
            reason = new StringBuilder(reason.substring(1));

            m.kick(reason.toString()).queue();

            System.out.println("Kicked " + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " from the Server: " + m.getGuild().getName() + " - ID: " + m.getGuild().getId() + " for the Reason: " + reason);
            sendKickMessage(msg, m, reason.toString());

        }

    }

    public void checkWithMemberResult(Member m, Message msg, String[] args) {
        if (args.length == 1) {
            kickMember(m, null, msg);
        }else {
            kickMember(m, args, msg);
        }

    }

    public void sendKickMessage(Message msg, Member kicked, String reason) {
        User kick = kicked.getUser();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.YELLOW);
        eb.setAuthor("Successfully kicked " + kick.getName() + "#" + kick.getDiscriminator());
        eb.setDescription("Reason: " + reason);
        eb.setFooter("Kicked by " + msg.getAuthor().getName());
        eb.setTimestamp(Instant.now());
        msg.getChannel().sendMessage(eb.build()).queue();
    }

}

package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

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
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        if (Objects.requireNonNull(msg.getMember()).hasPermission(Permission.KICK_MEMBERS)) {
            if (args[0].isBlank()) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.red);
                eb.setAuthor("Wrong Arguments!");
                eb.setDescription("You didn't specify who you want to kick!");
                eb.addField("Syntax: ", this.getSyntax(), false);
                eb.setTimestamp(Instant.now());
                eb.setFooter("Argument Error by " + msg.getAuthor().getName());
                msg.getChannel().sendMessage(eb.build()).queue((result) -> {
                    result.delete().queueAfter(10, TimeUnit.SECONDS);
                    msg.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            }else {
                String mentionid = args[0];
                //MENTION
                if (args[0].startsWith("<") && args[0].endsWith(">")) {
                    mentionid = mentionid.substring(3, mentionid.length()-1);

                    if (mentionid.matches("^[0-9]*$")) {
                        Member membernamemention = msg.getGuild().getMemberById(mentionid);
                        if (membernamemention == null) {
                            sendInvalidUserError(msg, args[0]);
                        }else {
                            checkWithMemberResult(membernamemention, msg, args);
                        }
                    }

                }else {
                    if (args[0].matches("^[0-9]*$")) {
                        Member membernameid = msg.getGuild().getMemberById(args[0]);
                        if (membernameid == null) {
                            sendInvalidUserError(msg, args[0]);
                        }else {
                            checkWithMemberResult(membernameid, msg, args);
                        }
                        //ELSE
                    }else {
                        sendInvalidUserError(msg, args[0]);
                    }
                }
            }

        }else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setAuthor("Missing Permissions!");
            eb.setDescription("You don't have the Permission to use this Command");
            eb.addField("Missing Permission:", "KICK_MEMBERS", false);
            eb.setTimestamp(Instant.now());
            eb.setFooter("Permission Error by " + msg.getAuthor().getName());
            msg.getChannel().sendMessage(eb.build()).queue((result) -> {
                result.delete().queueAfter(10, TimeUnit.SECONDS);
                msg.delete().queueAfter(10, TimeUnit.SECONDS);
            });
        }
    }

    public void sendInvalidUserError(Message msg, String str) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.red);
        eb.setAuthor("Invalid User!");
        eb.setDescription("The User \"" + str + "\" isn't a valid user!");
        eb.setTimestamp(Instant.now());
        eb.setFooter("Unknown User Error by " + msg.getAuthor().getName());
        msg.getChannel().sendMessage(eb.build()).queue((result) -> {
            result.delete().queueAfter(10, TimeUnit.SECONDS);
            msg.delete().queueAfter(10, TimeUnit.SECONDS);
        });
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

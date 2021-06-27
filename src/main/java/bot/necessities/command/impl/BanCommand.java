package bot.necessities.command.impl;

import bot.necessities.command.Command;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class BanCommand extends Command {
    @Override
    public String getAlias() {
        return "ban";
    }

    @Override
    public String getDescription() {
        return "Ban someone out of your server";
    }

    @Override
    public String getSyntax() {
        return "-ban [user] [reason]";
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {
        if (msg.getMember().hasPermission(Permission.BAN_MEMBERS)) {
            String reason = "";
            if (args.length == 0) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.red);
                eb.setAuthor("Wrong Arguments!");
                eb.setDescription("You didnt specify who you want to ban!");
                eb.addField("Syntax: ", this.getSyntax(), false);
                eb.setTimestamp(Instant.now());
                eb.setFooter("Argument Error by " + msg.getAuthor().getName());
                msg.getChannel().sendMessage(eb.build()).queue((result) -> {
                    result.delete().queueAfter(10, TimeUnit.SECONDS);
                    msg.delete().queueAfter(10, TimeUnit.SECONDS);
                });
            }else {
                String mentionid = args[0];
                final String finalReason = reason;
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
            eb.addField("Missing Permission:", "BAN_MEMBERS", false);
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

    public void banMember(Member m, String[] args, Message msg) {
        String reason = "";

        if (args == null) {
            m.ban(0, null).queue();
            System.out.println("Banned " + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " from the Server: " + m.getGuild().getName() + " - ID: " + m.getGuild().getId() + " for the Reason: null");
            sendBanMessage(msg, m, "None");
        }else {

            for (int i = 1; i < args.length; i++) {
                reason = reason + " " + args[i];
            }
            reason = reason.substring(1);

            m.ban(0, reason).queue();

            System.out.println("Banned " + m.getUser().getName() + "#" + m.getUser().getDiscriminator() + " from the Server: " + m.getGuild().getName() + " - ID: " + m.getGuild().getId() + " for the Reason: " + reason);
            sendBanMessage(msg, m, reason);

        }

    }

    public void checkWithMemberResult(Member m, Message msg, String[] args) {
        if (args.length == 1) {
            banMember(m, null, msg);
        }else {
            banMember(m, args, msg);
        }

    }

    public void sendBanMessage(Message msg, Member banned, String reason) {
        User ban = banned.getUser();
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.YELLOW);
        eb.setAuthor("Successfully Banned! " + ban.getName() + "#" + ban.getDiscriminator());
        eb.setDescription("Reason: " + reason);
        eb.setFooter("Banned by " + msg.getAuthor().getName());
        eb.setTimestamp(Instant.now());
        msg.getChannel().sendMessage(eb.build()).queue();
    }

}

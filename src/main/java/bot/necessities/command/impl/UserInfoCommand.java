package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.concurrent.TimeUnit;

public class UserInfoCommand extends Command {
    @Override
    public String getAlias() {
        return "userinfo";
    }

    @Override
    public String getDescription() {
        return "Gets the Info of a User";
    }

    @Override
    public String getSyntax() {
        return "-userinfo [user]";
    }

    @Override
    public Category getCategory() {
        return Category.INFORMATION;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (args.length == 0) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setAuthor("Wrong Arguments!");
            eb.setDescription("You didn't specify what User you want to get info from!");
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
                mentionid = mentionid.substring(3, mentionid.length() - 1);

                if (mentionid.matches("^[0-9]*$")) {
                    Member membernamemention = msg.getGuild().getMemberById(mentionid);
                    if (membernamemention == null) {
                        sendInvalidUserError(msg, args[0]);
                    } else {
                        sendResult(membernamemention, msg);
                    }
                }

            } else {
                if (args[0].matches("^[0-9]*$")) {
                    Member membernameid = msg.getGuild().getMemberById(args[0]);
                    if (membernameid == null) {
                        sendInvalidUserError(msg, args[0]);
                    } else {
                        sendResult(membernameid, msg);
                    }
                }
            }
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

    public void sendResult(Member m, Message msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String roles = "";
        if (m.getRoles().isEmpty() == false) {
            for(Role r : m.getRoles()) {
                roles = roles + " " + r.getAsMention();
            }
            roles = roles.substring(1);
        }else {
            roles = "None!";
        }


        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(m.getColor());
        if (m.isOwner()) {
            eb.setAuthor("Server Owner Information - " + m.getUser().getName() + "#" + m.getUser().getDiscriminator());
        }else if (m.getId().equals("708697252410949693")){
            eb.setAuthor("Necessities Bot Information - " + m.getUser().getName() + "#" + m.getUser().getDiscriminator());
        }else {
            eb.setAuthor("User Information - " + m.getUser().getName() + "#" + m.getUser().getDiscriminator());
        }
        eb.setThumbnail(m.getUser().getAvatarUrl());
        eb.addField("ID: ", String.valueOf(m.getUser().getId()), false);
        eb.addField("Created at: ", m.getTimeCreated().format(formatter) + " UTC", false);
        eb.addField("Joined at: ", m.getTimeJoined().format(formatter) + " UTC", false);
        eb.addField("Roles: ", String.valueOf(roles), false);
        eb.addField("Necessities Rank: ", m.getId().equals("708697252410949693") ? "The Bot itself!" : Main.isStaffUser(m.getUser()) ? "Staff" : Main.isPremiumUser(m.getUser()) ? "VIP" : "Normal User", false);
        eb.setTimestamp(Instant.now());
        eb.setFooter("Requested by " + msg.getAuthor().getName());
        msg.getChannel().sendMessage(eb.build()).queue();


    }
}

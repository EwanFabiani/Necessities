package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.sql.JDBC;
import bot.necessities.util.ErrorCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;

import java.awt.*;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
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
    public Permission requiredPermission() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (args[0].isBlank()) {
            sendResult(Objects.requireNonNull(msg.getMember()), msg);
            msg.getChannel().sendMessage("Since you didn't specify the user, here is your user information!").queue((result) -> result.delete().queueAfter(3, TimeUnit.SECONDS));

        }else {
            String mentionid = args[0];
            //MENTION
            if (args[0].startsWith("<") && args[0].endsWith(">")) {
                mentionid = mentionid.substring(3, mentionid.length() - 1);

                if (mentionid.matches("^[0-9]*$")) {
                    Member membernamemention = msg.getGuild().getMemberById(mentionid);
                    if (membernamemention == null) {
                        ErrorCreator.invalidArgumentError(msg, "The User \"" + args[0] + "\" isn't a valid user!");
                    } else {
                        sendResult(membernamemention, msg);
                    }
                }

            } else {
                if (args[0].matches("^[0-9]*$")) {
                    Member membernameid = msg.getGuild().getMemberById(args[0]);
                    if (membernameid == null) {
                        ErrorCreator.invalidArgumentError(msg, "The User \"" + args[0] + "\" isn't a valid user!");
                    } else {
                        sendResult(membernameid, msg);
                    }
                }else {
                    ErrorCreator.invalidArgumentError(msg, "The User \"" + args[0] + "\" isn't a valid user!");
                }
            }
        }

    }

    public void sendResult(Member m, Message msg) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        StringBuilder roles = new StringBuilder();
        if (!m.getRoles().isEmpty()) {
            for(Role r : m.getRoles()) {
                roles.append(" ").append(r.getAsMention());
            }
            roles = new StringBuilder(roles.substring(1));
        }else {
            roles = new StringBuilder("None!");
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
        eb.addField("ID: ", m.getUser().getId(), false);
        eb.addField("Created at: ", m.getTimeCreated().format(formatter) + " UTC", false);
        eb.addField("Joined at: ", m.getTimeJoined().format(formatter) + " UTC", false);
        eb.addField("Roles: ", roles.toString(), false);
        if (JDBC.getRank(m.getUser().getIdLong()) == null) {
            eb.addField("Necessities Rank: ", "This Person has never used Necessities!", false);
        }else {
            eb.addField("Necessities Rank: ", JDBC.getRank(m.getUser().getIdLong()).toString(), false);
        }



        eb.setTimestamp(Instant.now());
        eb.setFooter("Requested by " + msg.getAuthor().getName());
        msg.getChannel().sendMessage(eb.build()).queue();

    }
}

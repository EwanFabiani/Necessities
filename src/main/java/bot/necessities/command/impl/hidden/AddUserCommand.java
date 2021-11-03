package bot.necessities.command.impl.hidden;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.command.CommandManager;
import bot.necessities.main.Main;
import bot.necessities.sql.JDBC;
import bot.necessities.util.ErrorCreator;
import bot.necessities.util.MiscFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;

public class AddUserCommand extends Command {

    @Override
    public String getAlias() {
        return "adduser";
    }

    @Override
    public String getDescription() {
        return "Add a user to the Database";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "adduser";
    }

    @Override
    public Category getCategory() {
        return Category.HIDDEN;
    }

    @Override
    public Permission requiredPermission() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        if (!args[0].isBlank()) {

            Member m = MiscFunctions.getUserFromString(args[0], msg);

            Boolean checked = JDBC.checkForEntryAndCreate(m.getUser().getIdLong());

            if (checked == true) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setAuthor("User already in the Database!");
                eb.setDescription("The user " + m.getUser().getName() + " is already in the Necessities database!");
                eb.setTimestamp(Instant.now());
                msg.reply(eb.build()).queue();
            }else if (checked == false) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.green);
                eb.setAuthor("Successfully added User!");
                eb.setAuthor("Successfully added " + m.getUser().getName() + " to the Necessities database!");
                eb.setTimestamp(Instant.now());
                msg.reply(eb.build()).queue();
                CommandManager.sendInformationEmbed(msg, m.getUser());
            }else {
                ErrorCreator.customError(msg, "An unexpected Error Occurred", "Unable to add this user to the Database");
            }



        }else {
            ErrorCreator.missingArgumentsError(msg, this, "You didn't specify the User!");
        }

    }
}

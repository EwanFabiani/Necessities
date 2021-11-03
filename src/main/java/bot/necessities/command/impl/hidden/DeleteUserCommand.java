package bot.necessities.command.impl.hidden;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.sql.JDBC;
import bot.necessities.util.ErrorCreator;
import bot.necessities.util.MiscFunctions;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

import java.awt.*;
import java.time.Instant;

public class DeleteUserCommand extends Command {


    @Override
    public String getAlias() {
        return "deleteuser";
    }

    @Override
    public String getDescription() {
        return "Delete A user from the Database!";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "deleteuser [User]";
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

            User u = MiscFunctions.getUserFromString(args[0], msg).getUser();

            Boolean checked = JDBC.checkForEntry(u.getIdLong());

            if (checked == true) {
                JDBC.deleteUser(u.getIdLong());
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.GREEN);
                eb.setAuthor("Successfully deleted " + u.getName() + " out of the database");
                eb.setTimestamp(Instant.now());
                msg.reply(eb.build()).queue();
            }else if (checked == false) {
                EmbedBuilder eb = new EmbedBuilder();
                eb.setColor(Color.RED);
                eb.setAuthor("User isn't in the database!");
                eb.setAuthor(u.getName() + " is not in the Necessities database!");
                eb.setTimestamp(Instant.now());
                msg.reply(eb.build()).queue();
            }else {
                ErrorCreator.customError(msg, "An unexpected Error Occurred", "Unable to remove this user from the Database");
            }



        }else {
            ErrorCreator.missingArgumentsError(msg, this, "You didn't specify the User!");
        }

    }
}

package bot.necessities.command.impl.hidden;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.sql.JDBC;
import bot.necessities.util.ErrorCreator;
import bot.necessities.util.MiscFunctions;
import bot.necessities.util.RANK;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;

public class ChangeRankCommand extends Command {

    @Override
    public String getAlias() {
        return "changerank";
    }

    @Override
    public String getDescription() {
        return "Changes Bot Rank";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "changerank [User] [RANK]";
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

        if (args.length > 2) {
            ErrorCreator.missingArgumentsError(msg, this, "Too Many Arguments");
            return;
        }

        if (!args[0].isBlank() && args.length == 2) {

            Member m = MiscFunctions.getUserFromString(args[0], msg);
            if (m == null) {
                ErrorCreator.invalidArgumentError(msg, "The User \"" + args[0] + "\" isn't a valid user!");
                return;
            }
            Long ID = m.getUser().getIdLong();
            if (JDBC.checkForEntry(ID) == false) {
                ErrorCreator.customError(msg, "This user isn't on the Database");
                return;
            }
            try {
                RANK.valueOf(args[1].toUpperCase());
            } catch (IllegalArgumentException e) {
                ErrorCreator.invalidArgumentError(msg, "Rank is invalid!");
                return;
            }

            RANK rank = RANK.valueOf(args[1].toUpperCase());

            if (JDBC.getRank(ID) == rank) {
                ErrorCreator.customError(msg, "The user has this necessities rank already");
                return;
            }

            if (JDBC.getRank(msg.getAuthor().getIdLong()) == RANK.STAFF) {
                if (rank == RANK.OWNER || rank == RANK.STAFF || JDBC.getRank(m.getIdLong()) == RANK.OWNER) {
                    ErrorCreator.customError(msg, "Missing Permission!", "You do not have the Permission to execute that Rank change!");
                }
            }

            JDBC.changeRank(ID, rank);

            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.green);
            eb.setAuthor("Success!");
            eb.setDescription("Successfully changed " + m.getUser().getName() + "'s Rank to " + args[1]);
            eb.setTimestamp(Instant.now());
            msg.reply(eb.build()).queue();

        }else {
            ErrorCreator.missingArgumentsError(msg, this, "Not enough Arguments");
        }

    }
}

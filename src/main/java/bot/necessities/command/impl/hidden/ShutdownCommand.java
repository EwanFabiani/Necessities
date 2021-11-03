package bot.necessities.command.impl.hidden;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import bot.necessities.sql.JDBC;
import bot.necessities.util.RANK;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;

public class ShutdownCommand extends Command {

    @Override
    public String getAlias() {
        return "shutdown";
    }

    @Override
    public String getDescription() {
        return "Shutdown the bot [HIDDEN COMMAND]";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "shutdown";
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

        if (JDBC.getRank(msg.getIdLong()) == RANK.OWNER) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setAuthor("Shutting down Necessities");
            eb.setDescription("Necessities is currently shutting down!");
            eb.setFooter("Don't forget that this might break things!!");
            eb.setTimestamp(Instant.now());
            msg.reply(eb.build()).queue();
            Main.api.shutdown();
        }else {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.red);
            eb.setAuthor("Only the owner has access to this Command");
            eb.setTimestamp(Instant.now());
            msg.reply(eb.build()).queue();
            Main.api.shutdown();
        }
    }
}

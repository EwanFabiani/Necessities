package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;

public class BotCommand extends Command {

    @Override
    public String getAlias() {
        return "bot";
    }

    @Override
    public String getDescription() {
        return "Get Information about Necessities";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "bot";
    }

    @Override
    public Category getCategory() {
        return Category.BOT;
    }

    @Override
    public Permission requiredPermission() {
        return null;
    }

    @Override
    public void onCommand(String command, String[] args, Message msg) throws Exception {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.blue);
        eb.setAuthor("Bot Info - Necessities");
        eb.addField("Servers", String.valueOf(Main.api.getGuilds().size()), true);
        eb.setFooter("Necessities Bot Information", Main.api.getSelfUser().getAvatarUrl());
        eb.setTimestamp(Instant.now());
        msg.reply(eb.build()).queue();

    }
}

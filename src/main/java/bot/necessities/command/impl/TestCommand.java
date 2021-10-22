package bot.necessities.command.impl;

import bot.necessities.command.Category;
import bot.necessities.command.Command;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;

public class TestCommand extends Command {
    @Override
    public String getAlias() {
        return "test";
    }

    @Override
    public String getDescription() {
        return "Test Command for test Purposes only";
    }

    @Override
    public String getSyntax() {
        return Main.PREFIX + "test";
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

        if (msg.getAuthor().getIdLong() == 541558942095114251L) {
            for (String s : args) {
                System.out.println(s);
            }
        }else {

        }

    }
}

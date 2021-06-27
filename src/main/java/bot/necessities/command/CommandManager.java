package bot.necessities.command;

import bot.necessities.command.impl.*;
import bot.necessities.listeners.OnGuildJoin;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;

import java.util.ArrayList;
import java.util.Locale;

public class CommandManager {

    private static ArrayList<Command> commands;

    public CommandManager() {

        commands = new ArrayList();

        addCommand(new PingCommand());
        addCommand(new InviteCommand());
        addCommand(new ClearCommand());
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new ServerInfoCommand());
        addCommand(new UserInfo());


    }

    public void addCommand(Command c) {
        commands.add(c);
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static CommandManager getInstance() {
        return new CommandManager();
    }

    public void callCommands(String input, Message msg) {
        String[] split = input.split(" ");
        String command = split[0];
        String args = input.substring(command.length()).trim();
        for(Command c : getCommands()) {
            if (c.getAlias().equalsIgnoreCase(command)) {
                try {
                    c.onCommand(args, args.split(" "), msg);
                }catch(Exception e) {
                    e.printStackTrace();
                    Main.sendErrorMessage(msg, e);
                }
            }
        }

    }

}

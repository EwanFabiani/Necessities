package bot.necessities.command;

import bot.necessities.command.impl.*;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.entities.Message;

import java.util.ArrayList;

public class CommandManager {

    private static ArrayList commands;

    public CommandManager() {

        commands = new ArrayList();

        addCommand(new PingCommand());
        addCommand(new InviteCommand());
        addCommand(new ClearCommand());
        addCommand(new KickCommand());
        addCommand(new BanCommand());
        addCommand(new ServerInfoCommand());
        addCommand(new UserInfoCommand());
        addCommand(new HelpCommand());
        addCommand(new AboutCommand());
        addCommand(new DiscordCommand());
        addCommand(new WeatherCommand());

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

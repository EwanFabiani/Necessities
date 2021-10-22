package bot.necessities.command;

import bot.necessities.command.impl.*;
import bot.necessities.command.impl.hidden.ShutdownCommand;
import bot.necessities.main.Main;
import bot.necessities.util.ErrorCreator;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;

import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;

public class CommandManager {

    private static ArrayList<Command> commands;
    private static ArrayList<Command> hiddenCommands;
    private static ArrayList<Command> allCommands;

    public CommandManager() {

        commands = new ArrayList<>();
        hiddenCommands = new ArrayList<>();
        allCommands = new ArrayList<>();

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
        addCommand(new AxolotlCommand());
        addCommand(new TimeCommand());
        addCommand(new PollCommand());
        addCommand(new BotCommand());


        addHiddenCommand(new ShutdownCommand());
        addHiddenCommand(new TestCommand());

        allCommands.addAll(commands);
        allCommands.addAll(hiddenCommands);

    }

    public void addCommand(Command c) {
        commands.add(c);
    }

    public void addHiddenCommand(Command c) {
        hiddenCommands.add(c);
    }

    public static ArrayList<Command> getCommands() {
        return commands;
    }

    public static ArrayList<Command> getHiddenCommands() {
        return hiddenCommands;
    }

    public static ArrayList<Command> getAllCommands() {
        return allCommands;
    }

    public static CommandManager getInstance() {
        return new CommandManager();
    }

    public void callCommands(String input, Message msg) {
        String[] split = input.split(" ");
        String command = split[0];
        String args = input.substring(command.length()).trim();
        for(Command c : getAllCommands()) {
            if (c.getAlias().equalsIgnoreCase(command)) {
                if (!msg.getMember().hasPermission(c.requiredPermission()) && c.requiredPermission() != null) {
                    System.out.println(msg.getAuthor().getName() + " does not have the required Permission: " + c.requiredPermission().getName());
                    ErrorCreator.permissionError(msg, c.requiredPermission());
                    return;
                }
                try {
                    c.onCommand(args, args.split(" "), msg);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    Main.sendErrorMessage(msg, e);
                }
            }
        }
    }

    public static void sendCommandError(Message msg, String text, Command cmd) {

        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.red);
        eb.setAuthor("Command Error!");
        eb.setDescription(text);
        eb.setFooter("An Error Occurred!");
        eb.addField("Command Syntax", cmd.getSyntax(), false);
        eb.setTimestamp(Instant.now());
        msg.getChannel().sendMessage(eb.build()).queue();

    }
}

package bot.necessities.command;

import bot.necessities.command.impl.*;
import bot.necessities.command.impl.hidden.AddUserCommand;
import bot.necessities.command.impl.hidden.ChangeRankCommand;
import bot.necessities.command.impl.hidden.DeleteUserCommand;
import bot.necessities.command.impl.hidden.ShutdownCommand;
import bot.necessities.command.impl.TimeCommand;
import bot.necessities.main.Main;
import bot.necessities.sql.JDBC;
import bot.necessities.util.ErrorCreator;
import bot.necessities.util.RANK;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;

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

        addHiddenCommand(new AddUserCommand());
        addHiddenCommand(new ShutdownCommand());
        addHiddenCommand(new TestCommand());
        addHiddenCommand(new ChangeRankCommand());
        addHiddenCommand(new DeleteUserCommand());

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
        for(Command c : getCommands()) {
            if (c.getAlias().equalsIgnoreCase(command)) {
                if (!JDBC.checkForEntryAndCreate(msg.getAuthor().getIdLong())) {
                    sendInformationEmbed(msg, msg.getAuthor());
                }
                if (!msg.getMember().hasPermission(c.requiredPermission()) && c.requiredPermission() != null) {
                    ErrorCreator.permissionError(msg, c.requiredPermission());
                    return;
                }
                try {
                    c.onCommand(args, args.split(" "), msg);
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    Main.sendErrorMessage(msg, e);
                    return;
                }
            }
        }

        RANK rank = JDBC.getRank(msg.getAuthor().getIdLong());

        if (rank == RANK.STAFF || rank == RANK.OWNER) {

            for(Command c : getHiddenCommands()) {

                if (c.getAlias().equalsIgnoreCase(command)) {
                    //Does not need to check for Permission since its only Bot-Related Commands
                    try {
                        c.onCommand(args, args.split(" "), msg);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        Main.sendErrorMessage(msg, e);
                        return;
                    }
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

    public static void sendInformationEmbed(Message msg, User u) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setAuthor("Necessities Information");
        eb.setDescription("Necessities will keep and store some Information about you!\nIt will currently only store your discord ID and your Necessities Rank.\nThis might change and we will contact you if this changes!\nIf you want this information about you to be deleted, please contact a Necessities Staff Member!\nUsing Necessities in any way will save information about you again!");
        eb.setFooter("Your Necessities Staff Team!", Main.api.getSelfUser().getAvatarUrl());
        eb.setTimestamp(Instant.now());
        u.openPrivateChannel().queue(result->{
            result.sendMessage(eb.build()).queue();
        });
    }
}

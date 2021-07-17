package bot.necessities.comfirmcommands;

import bot.necessities.comfirmcommands.impl.BugCommand;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

public class ConfirmCommandManager {

    private static ArrayList confirmcommands;

    public ConfirmCommandManager() {

        confirmcommands = new ArrayList();
        addCommand(new BugCommand());

    }

    public void addCommand(ConfirmCommand c) {
        confirmcommands.add(c);
    }

    public static ArrayList<ConfirmCommand> getConfirmCommands() {
        return confirmcommands;
    }

    public static ConfirmCommandManager getInstance() {
        return new ConfirmCommandManager();
    }

    public void callCommands(String input, Message msg) {
        String[] split = input.split(" ");
        String command = split[0];
        String args = input.substring(command.length()).trim();
        for(ConfirmCommand c : getConfirmCommands()) {
            if (c.getAlias().equalsIgnoreCase(command)) {
                try {
                    processCommand(msg, args.split(" "), c.onCommand(args, args.split(" "), msg), c);
                }catch(Exception e) {
                    e.printStackTrace();
                    Main.sendErrorMessage(msg, e);
                }
            }
        }
    }

    public void triggerConfirm(ConfirmCommandType command) {

        try {
            command.getCommand().onConfirm(command);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

    }


    //process the command, adds the reaction and registers it in the ArrayList

    public void processCommand(Message msg, String[] args, MessageEmbed eb, ConfirmCommand confirmcmd) {

        AtomicReference<Long> id = new AtomicReference<Long>(0L);

         msg.reply(eb).queue((result) -> {
            result.addReaction("\u2705");
            id = result.getIdLong();
        });

        System.out.println("test");

        ConfirmCommandType cmd = new ConfirmCommandType(args, null, confirmcmd, msg.getChannel().getIdLong());
        Main.confirmCommandTypes.add(cmd);

    }

}

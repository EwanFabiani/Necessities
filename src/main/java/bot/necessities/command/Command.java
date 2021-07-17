package bot.necessities.command;

import net.dv8tion.jda.api.entities.Message;

public abstract class Command {

    public abstract String getAlias();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract Category getCategory();
    public abstract void onCommand(String command, String[] args, Message msg) throws Exception;

}

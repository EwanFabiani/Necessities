package bot.necessities.comfirmcommands;

import bot.necessities.command.Category;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;

public abstract class ConfirmCommand{

    public abstract String getAlias();
    public abstract String getDescription();
    public abstract String getSyntax();
    public abstract Category getCategory();
    public abstract MessageEmbed onCommand(String command, String[] args, Message msg) throws Exception;
    public abstract void onConfirm(ConfirmCommandType command) throws Exception;

}
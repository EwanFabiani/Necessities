package bot.necessities.listeners;

import bot.necessities.command.CommandManager;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MessageListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getMessage().getContentRaw().startsWith(Main.PREFIX)) {
            CommandManager.getInstance().callCommands(e.getMessage().getContentRaw().substring(1), e.getMessage());
        }
    }

}

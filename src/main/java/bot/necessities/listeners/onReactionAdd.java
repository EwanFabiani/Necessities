package bot.necessities.listeners;

import bot.necessities.comfirmcommands.ConfirmCommandManager;
import bot.necessities.comfirmcommands.ConfirmCommandType;
import bot.necessities.main.Main;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class onReactionAdd extends ListenerAdapter {

    public void onMessageReactionAdd(MessageReactionAddEvent e) {
        if (e.getUser() == Main.api.getSelfUser()) return;
        for(ConfirmCommandType cmd : Main.confirmCommandTypes) {
            System.out.println("1");
            System.out.println(cmd.getMsgid() + " - " + e.getMessageIdLong());
            System.out.println(Main.confirmCommandTypes.size());
            if (cmd.getMsgid() == e.getMessageIdLong()) {
                System.out.println("2");
                ConfirmCommandManager.getInstance().triggerConfirm(cmd);

                e.getChannel().retrieveMessageById(e.getMessageIdLong()).queue((result) -> {
                    result.delete().queue();
                });

            }
        }

    }
}

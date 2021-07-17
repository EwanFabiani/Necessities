package bot.necessities.listeners;

import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.Objects;

public class OnGuildJoin extends ListenerAdapter {

    public void onGuildJoin(GuildJoinEvent e) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(Color.green);
        eb.setDescription("Thanks for inviting me to **" + e.getGuild().getName() + "**!");
        eb.setAuthor("Use -help for a list of commands");
        eb.setFooter("Necessities Bot", Main.api.getSelfUser().getEffectiveAvatarUrl());
        Objects.requireNonNull(e.getGuild().getSystemChannel()).sendMessage(eb.build()).queue();
    }

}

package bot.necessities.main;

import bot.necessities.comfirmcommands.ConfirmCommandType;
import bot.necessities.listeners.MessageListener;
import bot.necessities.listeners.OnGuildJoin;
import bot.necessities.listeners.onReactionAdd;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.time.Instant;
import java.util.ArrayList;

public class Main {

    public static String PREFIX = "-";

    public static JDA api;

    public static String token = "NzA4Njk3MjUyNDEwOTQ5Njkz.XrbHvw.mHGa5EMQ5peYW8sXayC_LSG9dNo";

    public static String VERSION = "v.0.3.6";

    public static ArrayList<ConfirmCommandType> confirmCommandTypes = new ArrayList<>();

    public static void main(String[] args) throws LoginException, IllegalArgumentException {

        api = JDABuilder.createDefault(token).setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS).build();

        api.addEventListener(new MessageListener());
        api.addEventListener(new OnGuildJoin());
        api.addEventListener(new onReactionAdd());

        api.getPresence().setActivity(Activity.playing("with Java Discord Api"));

        System.out.println("Starting the Bot!");

    }

    public static void sendErrorMessage(Message msg, Exception exception) {
        try {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.BLACK);
            eb.setAuthor("An Error Occurred!");
            eb.setDescription(exception.getClass().toString());
            eb.setTimestamp(Instant.now());
            eb.setFooter("Bot Error");
            eb.addField("Error Message: ", exception.getMessage(), false);
            eb.addField("Please Contact TechCrafter_#1179!", "He will need to fix this :/", false);
            msg.getChannel().sendMessage(eb.build()).queue();
        } catch (Exception e) {
            EmbedBuilder eb = new EmbedBuilder();
            eb.setColor(Color.BLACK);
            eb.setTimestamp(Instant.now());
            eb.setAuthor("There was an Error!");
            eb.setDescription("The Default Error Message could not be sent!");
            eb.addField("Please Contact TechCrafter_#1179", "This usually happens when the Message length of the Error Message is too big", false);
            eb.setFooter("Bot Error", Main.api.getSelfUser().getAvatarUrl());
            msg.getChannel().sendMessage(eb.build()).queue();
        }
    }
}
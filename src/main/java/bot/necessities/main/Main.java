package bot.necessities.main;

import bot.necessities.command.CommandManager;
import bot.necessities.listeners.MessageListener;
import bot.necessities.listeners.OnGuildJoin;
import bot.necessities.sql.JDBC;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.MemberCachePolicy;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.security.auth.login.LoginException;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.Instant;
public class Main {

    public static String PREFIX = "-";

    public static JDA api;

    public static String token = null;

    public static String VERSION = "v.0.3.6";

    public static String KEY = "e8495f354dce423cbb2100942211508";

    public static JDA hacked;

    public static void main(String[] args) throws LoginException, IllegalArgumentException, IOException, ParseException, SQLException, ClassNotFoundException {

        FileReader file = new FileReader("resources\\info.json");

        JSONParser parser = new JSONParser();
        JSONObject data = (JSONObject) parser.parse(file);

        JDBC.startUp(String.valueOf(data.get("name")), String.valueOf(data.get("password")));
        token = String.valueOf(data.get("token"));

        api = JDABuilder.createDefault(token).setChunkingFilter(ChunkingFilter.ALL).setMemberCachePolicy(MemberCachePolicy.ALL).enableIntents(GatewayIntent.GUILD_MEMBERS).build();

        api.addEventListener(new MessageListener());
        api.addEventListener(new OnGuildJoin());

        api.getPresence().setActivity(Activity.playing("with JDA"));

        System.out.println("Starting the Bot!");

        new CommandManager();
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
            eb.addField("Please Contact TechCrafter_#1179!", "", false);
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
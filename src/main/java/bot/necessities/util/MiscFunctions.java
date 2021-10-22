package bot.necessities.util;

import bot.necessities.main.Main;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

public class MiscFunctions {

    public static Member getUserFromString(String str, Message msg) {

        Guild g = msg.getGuild();
        String mentionid = str;
        //MENTION
        if (str.startsWith("<") && str.endsWith(">")) {
            mentionid = mentionid.substring(3, mentionid.length()-1);

            if (mentionid.matches("^[0-9]*$")) {
                Member membernamemention = g.getMemberById(mentionid);
                if (membernamemention == null) {
                    return null;
                }else {
                    return membernamemention;
                }
            }else {
                return null;
            }

        }else {
            if (str.matches("^[0-9]*$")) {
                Member membernameid = g.getMemberById(str);
                if (membernameid == null) {
                    return null;
                }else {
                    return membernameid;
                }
            }else {
                return null;
            }
        }
    }

    public static TextChannel getTextChannelFromString(String str, Message msg) {

        Guild g = msg.getGuild();

        TextChannel tc;

        if (str.startsWith("<#") && str.endsWith(">")) {

            String number = str.substring(2, str.length()-1);

            if (!number.matches("^[0-9]*$")) return null;

            tc = Main.api.getTextChannelById(Long.valueOf(number));

            if (tc == null) {
                return null;
            }

        }else {

            if (!str.matches("^[0-9]*$")) return null;

            tc = Main.api.getTextChannelById(Long.valueOf(str));

            if (tc == null) {
                return null;
            }
        }
        if (!(tc == null)) return tc;
        else return null;
    }

}

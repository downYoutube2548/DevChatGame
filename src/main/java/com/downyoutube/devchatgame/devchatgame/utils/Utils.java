package com.downyoutube.devchatgame.devchatgame.utils;

import com.downyoutube.devchatgame.devchatgame.DevChatGame;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static String colorize(String s) {
        if (s == null || s.equals(""))
            return "";
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(s);
        while (match.find()) {
            String hexColor = s.substring(match.start(), match.end());
            s = s.replace(hexColor, net.md_5.bungee.api.ChatColor.of(hexColor).toString());
            match = pattern.matcher(s);
        }

        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String durationFormat(long seconds) {
        if (seconds < 0) seconds = 0;

        if (seconds == 0) { return "0 "+Utils.colorize(DevChatGame.main.getConfig().getString("translation.second")); }

        // calculate the duration between start and end
        Duration duration = Duration.ofSeconds(seconds);

        // extract the days, hours, and minutes from the duration
        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;
        long second = duration.getSeconds() % 60;

        // format the duration as a string
        StringBuilder sb = new StringBuilder();
        int i = 0;
        if (hours > 0) {
            sb.append(hours).append(" ").append(Utils.colorize(DevChatGame.main.getConfig().getString("translation.hour"))).append(" ");
            i++;
        }
        if (minutes > 0) {
            if (i < 1) {
                sb.append(minutes).append(" ").append(Utils.colorize(DevChatGame.main.getConfig().getString("translation.minute"))).append(" ");
                i++;
            }
        }
        if (second > 0) {
            if (i < 1) {
                sb.append(second).append(" ").append(Utils.colorize(DevChatGame.main.getConfig().getString("translation.second"))).append(" ");
            }
        }


        return sb.substring(0, Math.max(sb.length()-1, 0));
    }
}

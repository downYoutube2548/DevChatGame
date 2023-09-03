package com.downyoutube.devchatgame.devchatgame;

import com.downyoutube.devchatgame.devchatgame.chatgame.ChatGame;
import com.downyoutube.devchatgame.devchatgame.utils.Utils;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

public class PlaceholderHook extends PlaceholderExpansion {
    @NotNull
    @Override
    public String getAuthor() {
        return "downYoutube2548";
    }

    @NotNull
    @Override
    public String getIdentifier() {
        return "devchatgame";
    }

    @NotNull
    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("has_question")) {
            return ChatGame.currentChatGame == null ? "no" : "yes";
        }

        else if(params.equalsIgnoreCase("question")) {
            return ChatGame.currentChatGame == null ? "" : ChatGame.currentChatGame.getQuestion(); // "name" requires the player to be valid
        }

        else if (params.startsWith("question_")) {
            if (ChatGame.currentChatGame == null) return "";

            String[] a = params.split("_");
            int chars = Integer.parseInt(a[1]);
            int line = Integer.parseInt(a[2]);

            String question = ChatGame.currentChatGame.getQuestion();

            if (question.length() <= chars * line - chars) return "";
            String message = question.substring(chars * line - chars, Math.min(question.length(), chars * line));

            String last_color = ChatColor.getLastColors(message.substring(0, chars * line - chars));

            return line <= 1 ? message : last_color+message;

        }

        else if(params.equalsIgnoreCase("answer")) {
            return ChatGame.currentChatGame == null ? "" : ChatGame.currentChatGame.getAnswer();
        }

        else if(params.equalsIgnoreCase("reward")) {
            if (ChatGame.currentChatGame == null) return "";

            StringBuilder r = new StringBuilder();
            for (String s : ChatGame.currentChatGame.getReward()) {
                if (!s.split(";")[0].equals(" ")) r.append(Utils.colorize(s.split(";")[0])).append("§7, ");
            }
            return r.substring(0, r.length()-4);
        }

        else if (params.startsWith("reward_")) {
            if (ChatGame.currentChatGame == null) return "";

            String[] a = params.split("_");
            int chars = Integer.parseInt(a[1]);
            int line = Integer.parseInt(a[2]);

            StringBuilder r = new StringBuilder();
            for (String s : ChatGame.currentChatGame.getReward()) {
                if (!s.split(";")[0].equals(" ")) r.append(Utils.colorize(s.split(";")[0])).append("§7, ");
            }
            String reward = r.substring(0, r.length()-4);

            if (reward.length() <= chars * line - chars) return "";

            String message = reward.substring(chars * line - chars, Math.min(reward.length(), chars * line));
            String last_color = ChatColor.getLastColors(reward.substring(0, chars * line - chars));

            return line <= 1 ? message : last_color+message;

        }

        else if(params.equalsIgnoreCase("duration")) {
            return ChatGame.currentChatGame == null ? "" : Utils.durationFormat(ChatGame.currentChatGame.getDuration());
        }

        return null; // Placeholder is unknown by the Expansion
    }
}

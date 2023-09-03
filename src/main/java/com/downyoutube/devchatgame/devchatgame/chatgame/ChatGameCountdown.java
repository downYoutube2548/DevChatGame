package com.downyoutube.devchatgame.devchatgame.chatgame;

import com.downyoutube.devchatgame.devchatgame.DevChatGame;
import com.downyoutube.devchatgame.devchatgame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class ChatGameCountdown {
    public static BukkitTask startCountDown() {
        if (ChatGame.currentChatGame != null) {
            return Bukkit.getScheduler().runTaskTimerAsynchronously(DevChatGame.main, ()->{
                //Bukkit.broadcastMessage(String.valueOf(ChatGame.currentChatGame.getDuration()));
                if (ChatGame.currentChatGame.getDuration() > 0) {
                    ChatGame.currentChatGame.reduceDuration();
                } else {
                    Bukkit.broadcastMessage(Utils.colorize(DevChatGame.main.getConfig().getString("chat-game.game-over"))
                            .replace("{answer}", ChatGame.currentChatGame.getAnswer())
                    );
                    ChatGame.end();
                }
            }, 20, 20);
        }
        return null;
    }
}

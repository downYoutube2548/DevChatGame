package com.downyoutube.devchatgame.devchatgame.chatgame;

import com.downyoutube.devchatgame.devchatgame.DevChatGame;
import org.bukkit.Bukkit;

public class ChatGameGenerate {
    public static void start() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(DevChatGame.main, ()->{

            ChatGame.start();

        }, 0, DevChatGame.main.getConfig().getLong("chat-game.period") * 20L);
    }
}

package com.downyoutube.devchatgame.devchatgame.events;

import com.downyoutube.devchatgame.devchatgame.DevChatGame;
import com.downyoutube.devchatgame.devchatgame.chatgame.ChatGame;
import com.downyoutube.devchatgame.devchatgame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class onChat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (ChatGame.currentChatGame != null) {
            if (ChatGame.currentChatGame.getAnswer().equals(ChatColor.stripColor(event.getMessage()))) {
                event.setCancelled(true);

                ChatGame.end(event.getPlayer());
            }
        }
    }
}

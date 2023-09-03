package com.downyoutube.devchatgame.devchatgame;

import com.downyoutube.devchatgame.devchatgame.chatgame.ChatGameGenerate;
import com.downyoutube.devchatgame.devchatgame.commands.core;
import com.downyoutube.devchatgame.devchatgame.events.onChat;
import com.google.common.io.ByteStreams;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

public final class DevChatGame extends JavaPlugin {

    public static DevChatGame main;
    private static PlaceholderHook placeholderHook;

    @Override
    public void onEnable() {
        // Plugin startup logic
        main = this;
        placeholderHook = new PlaceholderHook();
        //loadResource(this, "config.yml");
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        Objects.requireNonNull(getCommand("chatgame")).setExecutor(new core());

        Bukkit.getPluginManager().registerEvents(new onChat(), this);

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            placeholderHook.register();
        }

        ChatGameGenerate.start();
    }  // Plugin shutdown logic


    @Override
    public void onDisable() {
        placeholderHook.unregister();
    }


    private static File loadResource(Plugin plugin, String resource) {
        File folder = plugin.getDataFolder();
        if (!folder.exists())
            folder.mkdir();
        File resourceFile = new File(folder, resource);
        try {
            //if (!resourceFile.exists()) {
            resourceFile.createNewFile();
            try (InputStream in = plugin.getResource(resource);
                 OutputStream out = new FileOutputStream(resourceFile)) {
                ByteStreams.copy(in, out);
            }
            //}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resourceFile;
    }


}

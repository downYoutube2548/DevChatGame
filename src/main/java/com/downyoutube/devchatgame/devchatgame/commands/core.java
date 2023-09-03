package com.downyoutube.devchatgame.devchatgame.commands;

import com.downyoutube.devchatgame.devchatgame.DevChatGame;
import com.downyoutube.devchatgame.devchatgame.chatgame.ChatGame;
import com.downyoutube.devchatgame.devchatgame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class core implements CommandExecutor, TabExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, Command command, String label, String[] args) {

        if (args.length >= 1) {
            if (args[0].equals("start")) {
                if (args.length == 1) {
                    Bukkit.getScheduler().runTaskAsynchronously(DevChatGame.main, ()->ChatGame.start());
                } else if (args.length == 2) {
                    Bukkit.getScheduler().runTaskAsynchronously(DevChatGame.main, () -> {
                        ChatGame.start(Long.parseLong(args[1]));
                    });
                } else if (args.length == 4) {
                    if (args[1].equalsIgnoreCase("math")) {
                        Bukkit.getScheduler().runTaskAsynchronously(DevChatGame.main, () -> {
                            ChatGame.startMath(Integer.parseInt(args[2]), Long.parseLong(args[3]));
                        });
                    }
                } else if (args.length >= 5) {
                    Bukkit.getScheduler().runTaskAsynchronously(DevChatGame.main, ()-> {
                        ChatGame.start(args[1], args[2], args[3], Long.parseLong(args[4]));
                    });
                }
            }
            else if (args[0].equals("end")) {
                if (ChatGame.currentChatGame == null) return false;
                if (args.length == 1) {
                    Bukkit.broadcastMessage(Utils.colorize(DevChatGame.main.getConfig().getString("chat-game.game-over"))
                            .replace("{answer}", ChatGame.currentChatGame.getAnswer())
                    );
                    ChatGame.end();
                } else if (args.length == 2) {
                    ChatGame.end(Bukkit.getPlayer(args[1]));
                }
            }
            else if (args[0].equals("reload")) {
                DevChatGame.main.reloadConfig();
                sender.sendMessage(ChatColor.GREEN+"Reloaded Config!");
            }
        }

        return false;
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> output = new ArrayList<>();

        if (args.length == 1) {
            output = tabComplete(args[0], List.of("start", "end"));
        } else if (args[0].equals("start")) {
            if (args.length == 2) {
                output = List.of("QUESTION");
            } else if (args.length == 3) {
                output = List.of("ANSWER");
            } else if (args.length == 4) {
                output = List.of("REWARD");
            } else if (args.length == 5) {
                output = List.of("DURATION");
            }
        } else if (args[0].equals("end")) {
            if (args.length == 2) {
                List<String> onlinePlayers = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    onlinePlayers.add(player.getName());
                }
                output = tabComplete(args[1], onlinePlayers);
            }
        }

        return output;
    }

    public static List<String> tabComplete(String a, List<String> arg) {
        List<String> matches = new ArrayList<>();
        String search = a.toLowerCase(Locale.ROOT);
        for (String s : arg) {
            if (s.toLowerCase(Locale.ROOT).startsWith(search)) {
                matches.add(s);
            }
        }
        return matches;
    }
}

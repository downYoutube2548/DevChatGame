package com.downyoutube.devchatgame.devchatgame.chatgame;

import com.downyoutube.devchatgame.devchatgame.DevChatGame;
import com.downyoutube.devchatgame.devchatgame.equation.EquationGenerate;
import com.downyoutube.devchatgame.devchatgame.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public class ChatGame {

    public static ChatGame currentChatGame;
    private static BukkitTask countdownTask;

    private final String question;
    private final String answer;
    private final List<String> reward;
    private long duration;

    public ChatGame(String question, String answer, List<String> reward, long duration) {
        this.question = question;
        this.answer = answer;
        this.reward = reward;
        this.duration = duration;
    }

    public String getQuestion() {
        return this.question;
    }

    public String getAnswer() {
        return this.answer;
    }

    public List<String> getReward() {
        return this.reward;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public void reduceDuration() {
        this.duration = duration-1;
    }

    public static void start() {
        long time_for_answer = DevChatGame.main.getConfig().getLong("chat-game.time-for-answer");
        start(time_for_answer);
    }

    public static void start(long time_for_answer) {
        StringBuilder sb = new StringBuilder();
        for (String line : DevChatGame.main.getConfig().getStringList("chat-game.chat-game-message")) {
            sb.append(line).append("\n");
        }
        String message = sb.substring(0, sb.length()-1);

        Set<String> level_list = DevChatGame.main.getConfig().getConfigurationSection("chat-game.reward-each-level").getKeys(false);
        int level = Integer.parseInt(new ArrayList<>(level_list).get(new Random().nextInt(level_list.size())));
        String equation = EquationGenerate.generateEquation(level);
        StringBuilder reward_message = new StringBuilder();
        List<String> reward_each_level = DevChatGame.main.getConfig().getStringList("chat-game.reward-each-level."+level);
        for (String reward : reward_each_level) {
            if (reward.split(";")[0].equals(" ")) continue;
            reward_message.append("\n       ").append(Utils.colorize(reward.split(";")[0]));
        }
        String reward = reward_message.toString();

        if (countdownTask != null) countdownTask.cancel();
        currentChatGame = new ChatGame(equation, String.valueOf(EquationGenerate.getAnswer(equation)), reward_each_level, time_for_answer);
        countdownTask = ChatGameCountdown.startCountDown();

        Bukkit.broadcastMessage(Utils.colorize(message)
                .replace("{chat game}", equation)
                .replace("{time}", Utils.durationFormat(time_for_answer))
                .replace("{reward}", reward)
        );
    }

    public static void start(String question, String answer, String reward, long duration) {
        StringBuilder sb = new StringBuilder();
        for (String line : DevChatGame.main.getConfig().getStringList("chat-game.chat-game-message")) {
            sb.append(line).append("\n");
        }
        String message = sb.substring(0, sb.length()-1);

        if (countdownTask != null) countdownTask.cancel();
        currentChatGame = new ChatGame(question, answer, List.of(reward), duration);
        countdownTask = ChatGameCountdown.startCountDown();

        Bukkit.broadcastMessage(Utils.colorize(message)
                .replace("{chat game}", question)
                .replace("{time}", Utils.durationFormat(duration))
                .replace("{reward}", reward)
        );
    }

    public static void startMath(int level, long duration) {
        StringBuilder sb = new StringBuilder();
        for (String line : DevChatGame.main.getConfig().getStringList("chat-game.chat-game-message")) {
            sb.append(line).append("\n");
        }
        String message = sb.substring(0, sb.length()-1);
        String equation = EquationGenerate.generateEquation(level);

        if (countdownTask != null) countdownTask.cancel();
        currentChatGame = new ChatGame(equation, String.valueOf(EquationGenerate.getAnswer(equation)), List.of("§7-"), duration);
        countdownTask = ChatGameCountdown.startCountDown();

        Bukkit.broadcastMessage(Utils.colorize(message)
                .replace("{chat game}", equation)
                .replace("{time}", Utils.durationFormat(duration))
                .replace("{reward}", "§7-")
        );
    }

    public static void end() {
        countdownTask.cancel();
        currentChatGame = null;
    }

    public static void end(Player player) {
        StringBuilder r = new StringBuilder();
        for (String s : ChatGame.currentChatGame.getReward()) {
            if (s.split(";").length > 1 && !s.split(";")[1].equals(" ")) Bukkit.getScheduler().runTask(DevChatGame.main, ()-> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.split(";")[1].replace("{player}", player.getName())));
            if (!s.split(";")[0].equals(" ")) r.append(Utils.colorize(s.split(";")[0])).append("§7, ");
        }
        String reward = r.substring(0, r.length()-4);

        player.sendMessage(Utils.colorize(DevChatGame.main.getConfig().getString("chat-game.message-for-winner")).replace("{reward}", reward));
        Bukkit.broadcastMessage(Utils.colorize(DevChatGame.main.getConfig().getString("chat-game.chat-game-win"))
                .replace("{player}", player.getName())
                .replace("{answer}", ChatGame.currentChatGame.getAnswer())
        );

        end();
    }
}

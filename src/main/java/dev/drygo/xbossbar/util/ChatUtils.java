package dev.drygo.xbossbar.util;

import dev.drygo.xbossbar.XBossBar;
import dev.drygo.xbossbar.manager.ConfigManager;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatUtils {
    private static XBossBar plugin;
    public static String prefix;

    public static void init(XBossBar plugin) {
        ChatUtils.plugin = plugin;
    }

    public static String formatColor(String message) {
        message = replaceHexColors(message);
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    private static String replaceHexColors(String message) {
        Pattern hexPattern = Pattern.compile("#([A-Fa-f0-9]{6})");
        Matcher matcher = hexPattern.matcher(message);
        StringBuilder buffer = new StringBuilder();

        while (matcher.find()) {
            String hexColor = matcher.group(1);
            StringBuilder color = new StringBuilder("&x");
            for (char c : hexColor.toCharArray()) {
                color.append("&").append(c);
            }
            matcher.appendReplacement(buffer, color.toString());
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    public static String getMessage(String path, Player player) {

        String message = ConfigManager.getMessageConfig().isList(path)
                ? String.join("\n", ConfigManager.getMessageConfig().getStringList(path))
                : ConfigManager.getMessageConfig().getString(path);

        if (message == null || message.isEmpty()) {
            plugin.getLogger().warning("[WARNING] Message not found: " + path);
            return ChatUtils.formatColor("&r" + getPrefix() + " #FF0000&l[ERROR] #FF3535Message not found: " + path);
        }

        message = message.replace("%player%", player != null ? player.getName() : "Unknown");

        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            message = PlaceholderAPI.setPlaceholders(player, message);
        }

        message = message.replace("%prefix%", getPrefix());

        return ChatUtils.formatColor(message);
    }

    public static String formatMultiLineMessage(List<String> messages, String playerName) {
        StringBuilder formattedMessage = new StringBuilder();
        for (String line : messages) {
            formattedMessage.append(ChatUtils.formatColor(
                    line.replace("%player%", playerName)
                            .replace("%prefix%", getPrefix())
            )).append("\n");
        }
        return formattedMessage.toString().trim();
    }


    public static String getPrefix() {
        return prefix;
    }
    public static void reloadPrefix() {
        String prefix = ConfigManager.getMessageConfig().getString("prefix");
        if (prefix != null) {
            ChatUtils.prefix = prefix;
        } else {
            ChatUtils.prefix = ChatUtils.formatColor("#4c7dff&lAstra &cDefault Prefix &8»&r");
        }
    }
}
package dev.drygo.xbossbar.manager;

import dev.drygo.xbossbar.XBossBar;
import dev.drygo.xbossbar.util.ChatUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {
    private static XBossBar plugin;
    private static FileConfiguration messagesConfig;

    public static void init(XBossBar plugin) {
        ConfigManager.plugin = plugin;
    }

    public static void reloadAll() {
        loadConfig();
        reloadMessages();
        ChatUtils.reloadPrefix();
    }

    public static void loadConfig() {
        try {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            plugin.getLogger().info("config.yml file successfully loaded.");
        } catch (Exception e) {
            plugin.getLogger().severe("Failed on loading config.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void reloadMessages() {
        try {
            File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
            if (!messagesFile.exists()) {
                plugin.saveResource("messages.yml", false);
                plugin.getLogger().info("messages.yml file did not exist, it has been created.");
            } else {
                plugin.getLogger().info("messages.yml file has been loaded successfully.");
            }
            messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load messages configuration due to an unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static FileConfiguration getMessageConfig() {
        return messagesConfig;
    }
}
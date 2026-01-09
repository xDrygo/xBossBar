package dev.drygo.XBossBar.Managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import dev.drygo.XBossBar.Utils.ChatUtils;
import dev.drygo.XBossBar.XBossBar;

import java.io.File;

public class ConfigManager {
    private static XBossBar plugin;
    private static FileConfiguration messagesConfig;

    public static void init(XBossBar plugin) {
        ConfigManager.plugin = plugin;
    }

    public static void loadConfig() {
        try {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            plugin.getLogger().info("✅ The config.yml file successfully loaded.");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed on loading config.yml: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public static void reloadMessages() {
        try {
            File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
            if (!messagesFile.exists()) {
                plugin.saveResource("messages.yml", false);
                plugin.getLogger().info("✅ The messages.yml file did not exist, it has been created.");
            } else {
                plugin.getLogger().info("✅ The messages.yml file has been loaded successfully.");
            }

            messagesConfig = YamlConfiguration.loadConfiguration(messagesFile);
            XBossBar.prefix = ChatUtils.formatColor("#ff5b84&lVR&r&lKoth &cDefault Prefix &8»&r");
        } catch (Exception e) {
            plugin.getLogger().severe("❌ Failed to load messages configuration due to an unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static String getPrefix() { return XBossBar.prefix; }
    public static void setPrefix(String prefix) { XBossBar.prefix = prefix; }
    public static FileConfiguration getMessageConfig() {
        return messagesConfig;
    }
}

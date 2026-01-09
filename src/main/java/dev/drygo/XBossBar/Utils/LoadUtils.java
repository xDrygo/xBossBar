package dev.drygo.XBossBar.Utils;

import org.bukkit.Bukkit;
import dev.drygo.XBossBar.Hooks.XBossBarExpansion;
import dev.drygo.XBossBar.Handlers.XBossBarCommand;
import dev.drygo.XBossBar.Handlers.XBossBarTabCompleter;
import dev.drygo.XBossBar.Listeners.PlayerListener;
import dev.drygo.XBossBar.Managers.ConfigManager;
import dev.drygo.XBossBar.XBossBar;

public class LoadUtils {
    private static XBossBar plugin;

    public static void init(XBossBar plugin) {
        LoadUtils.plugin = plugin;
    }

    public static void loadFeatures() {
        loadConfigFiles();
        loadCommand();
        loadListeners();
        loadPlaceholderAPI();
    }

    public static void loadConfigFiles() {
        ConfigManager.loadConfig();
        ConfigManager.reloadMessages();
        ConfigManager.setPrefix(ChatUtils.formatColor(ConfigManager.getMessageConfig().getString("prefix", "  &8»&r")));
    }
    private static void loadListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), plugin);
    }
    private static void loadCommand() {
        if (plugin.getCommand("xbossbar") == null) {
            plugin.getLogger().severe("❌ Error: /xbossbar command is not registered in plugin.yml");
        } else {
            plugin.getCommand("xbossbar").setExecutor(new XBossBarCommand(plugin));
            plugin.getCommand("xbossbar").setTabCompleter(new XBossBarTabCompleter());
            plugin.getLogger().info("✅ /xbossbar command was successfully loaded.");
        }
    }

    private static void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new XBossBarExpansion(plugin).register();
            plugin.getLogger().info("✅ PlaceholderAPI detected. PAPI dependency successfully loaded.");
            XBossBar.enabledPAPI = true;
        } else {
            plugin.getLogger().warning("⚠  PlaceholderAPI not detected. PAPI placeholders will not work.");
        }
    }
}

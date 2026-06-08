package dev.drygo.xbossbar.util;

import org.bukkit.Bukkit;
import dev.drygo.xbossbar.hook.XBossBarExpansion;
import dev.drygo.xbossbar.command.XBossBarCommand;
import dev.drygo.xbossbar.command.XBossBarTabCompleter;
import dev.drygo.xbossbar.listener.PlayerListener;
import dev.drygo.xbossbar.manager.ConfigManager;
import dev.drygo.xbossbar.XBossBar;

public class LoadUtils {
    public static void loadFeatures() {
        ConfigManager.reloadAll();
        loadCommand();
        loadListeners();
        loadPlaceholderAPI();
    }
    private static void loadListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(), XBossBar.getInstance());
    }
    private static void loadCommand() {
        XBossBar plugin = XBossBar.getInstance();
        if (plugin.getCommand("xbossbar") == null) {
            plugin.getLogger().severe("Error: /xbossbar command is not registered in plugin.yml");
        } else {
            plugin.getCommand("xbossbar").setExecutor(new XBossBarCommand());
            plugin.getCommand("xbossbar").setTabCompleter(new XBossBarTabCompleter());
            plugin.getLogger().info("/xbossbar command was successfully loaded.");
        }
    }

    private static void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new XBossBarExpansion().register();
            XBossBar.getInstance().getLogger().info("PlaceholderAPI detected. PAPI dependency successfully loaded.");
            XBossBar.enabledPAPI = true;
        } else {
            XBossBar.getInstance().getLogger().warning("⚠PlaceholderAPI not detected. PAPI placeholders will not work.");
        }
    }
}

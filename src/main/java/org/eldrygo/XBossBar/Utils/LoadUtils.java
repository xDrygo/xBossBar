package org.eldrygo.XBossBar.Utils;

import org.bukkit.Bukkit;
import org.eldrygo.XBossBar.Hooks.XBossBarExpansion;
import org.eldrygo.XBossBar.Handlers.XBossBarCommand;
import org.eldrygo.XBossBar.Handlers.XBossBarTabCompleter;
import org.eldrygo.XBossBar.Listeners.PlayerListener;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.eldrygo.XBossBar.Managers.ConfigManager;
import org.eldrygo.XBossBar.XBossBar;

public class LoadUtils {
    private final XBossBar plugin;
    private final BossBarManager bossbarManager;
    private final ConfigManager configManager;
    private final ChatUtils chatUtils;

    public LoadUtils(XBossBar plugin, BossBarManager bossbarManager, ConfigManager configManager, ChatUtils chatUtils) {
        this.plugin = plugin;
        this.bossbarManager = bossbarManager;
        this.configManager = configManager;
        this.chatUtils = chatUtils;
    }

    public void loadFeatures() {
        loadConfigFiles();
        loadCommand();
        loadListeners();
        loadPlaceholderAPI();
    }

    public void loadConfigFiles() {
        configManager.loadConfig();
        configManager.reloadMessages();
        configManager.setPrefix(ChatUtils.formatColor(configManager.getMessageConfig().getString("prefix", "  &8»&r")));
    }
    private void loadListeners() {
        Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(bossbarManager), plugin);
    }
    private void loadCommand() {
        if (plugin.getCommand("xbossbar") == null) {
            plugin.getLogger().severe("❌ Error: /xbossbar command is not registered in plugin.yml");
        } else {
            plugin.getCommand("xbossbar").setExecutor(new XBossBarCommand(chatUtils, bossbarManager, configManager, plugin, this));
            plugin.getCommand("xbossbar").setTabCompleter(new XBossBarTabCompleter(bossbarManager));
            plugin.getLogger().info("✅ /xbossbar command was successfully loaded.");
        }
    }

    private void loadPlaceholderAPI() {
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new XBossBarExpansion(plugin, bossbarManager).register();
            plugin.getLogger().info("✅ PlaceholderAPI detected. PAPI dependency successfully loaded.");
            plugin.enabledPAPI = true;
        } else {
            plugin.getLogger().warning("⚠  PlaceholderAPI not detected. PAPI placeholders will not work.");
        }
    }
}

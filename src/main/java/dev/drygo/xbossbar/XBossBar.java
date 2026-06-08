package dev.drygo.xbossbar;

import dev.drygo.xbossbar.manager.BossBarManager;
import dev.drygo.xbossbar.manager.ConfigManager;
import dev.drygo.xbossbar.util.ChatUtils;
import dev.drygo.xbossbar.util.LoadUtils;
import dev.drygo.xbossbar.util.LogsUtils;
import org.bukkit.plugin.java.JavaPlugin;

public class XBossBar extends JavaPlugin {
    private static XBossBar instance;

    public static boolean enabledPAPI = false;

    @Override
    public void onEnable() {
        instance = this;
        BossBarManager.init();
        ConfigManager.init(this);
        ChatUtils.init(this);
        BossBarManager.startBossBarUpdateTask();
        LoadUtils.loadFeatures();
        LogsUtils.sendStartupMessage();
        LogsUtils.sendUpdateMessage();
    }

    @Override
    public void onDisable() {
        BossBarManager.stopBossBarUpdateTask();
        LogsUtils.sendShutdownMessage();
    }

    public static XBossBar getInstance() {
        return instance;
    }
    public boolean enabledPAPI() {
        return enabledPAPI;
    }
}

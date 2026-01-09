package org.eldrygo.XBossBar;

import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.XBossBar.API.XBossBarAPI;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.eldrygo.XBossBar.Managers.ConfigManager;
import org.eldrygo.XBossBar.Utils.ChatUtils;
import org.eldrygo.XBossBar.Utils.LoadUtils;
import org.eldrygo.XBossBar.Utils.LogsUtils;

public class XBossBar extends JavaPlugin {
    public static String prefix;
    public static String version;
    public static boolean enabledPAPI = false;

    @Override
    public void onEnable() {
        version = getDescription().getVersion();
        BossBarManager.init(this);
        ConfigManager.init(this);
        ChatUtils.init(this);
        BossBarManager.startBossBarUpdateTask();
        LoadUtils.init(this);
        LoadUtils.loadFeatures();
        LogsUtils.init(this);
        LogsUtils.sendStartupMessage();
    }

    @Override
    public void onDisable() {
        BossBarManager.stopBossBarUpdateTask();
        LogsUtils.sendShutdownMessage();
    }

    public boolean enabledPAPI() {
        return enabledPAPI;
    }
}

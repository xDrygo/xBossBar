package dev.drygo.XBossBar;

import dev.drygo.XBossBar.Utils.DebugUtils;
import org.bukkit.plugin.java.JavaPlugin;
import dev.drygo.XBossBar.Managers.BossBarManager;
import dev.drygo.XBossBar.Managers.ConfigManager;
import dev.drygo.XBossBar.Utils.ChatUtils;
import dev.drygo.XBossBar.Utils.LoadUtils;
import dev.drygo.XBossBar.Utils.LogsUtils;

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
        DebugUtils.init(this);
        BossBarManager.startBossBarUpdateTask();
        LoadUtils.init(this);
        LoadUtils.loadFeatures();
        LogsUtils.init(this);
        LogsUtils.sendStartupMessage();
        LogsUtils.sendUpdateMessage();
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

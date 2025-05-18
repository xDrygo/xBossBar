package org.eldrygo.XBossBar;

import org.bukkit.plugin.java.JavaPlugin;
import org.eldrygo.XBossBar.API.XBossBarAPI;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.eldrygo.XBossBar.Managers.ConfigManager;
import org.eldrygo.XBossBar.Utils.ChatUtils;
import org.eldrygo.XBossBar.Utils.LoadUtils;
import org.eldrygo.XBossBar.Utils.LogsUtils;

public class XBossBar extends JavaPlugin {
    public String prefix;
    public String version;
    public boolean enabledPAPI = false;
    private BossBarManager bossbarManager;
    private LogsUtils logsUtils;

    @Override
    public void onEnable() {
        this.version = getDescription().getVersion();
        this.bossbarManager = new BossBarManager(this);
        this.logsUtils = new LogsUtils(this);
        ConfigManager configManager = new ConfigManager(this);
        ChatUtils chatUtils = new ChatUtils(configManager, this);
        LoadUtils loadUtils = new LoadUtils(this, bossbarManager, configManager, chatUtils);

        XBossBarAPI.setManager(bossbarManager);
        bossbarManager.startBossBarUpdateTask();
        loadUtils.loadFeatures();
        logsUtils.sendStartupMessage();
    }

    @Override
    public void onDisable() {
        bossbarManager.stopBossBarUpdateTask();
        logsUtils.sendShutdownMessage();
    }

    public boolean enabledPAPI() {
        return enabledPAPI;
    }
}

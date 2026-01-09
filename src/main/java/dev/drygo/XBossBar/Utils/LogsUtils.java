package dev.drygo.XBossBar.Utils;

import dev.drygo.XBossBar.UpdateChecker.ModrinthUpdateChecker;
import org.bukkit.Bukkit;
import dev.drygo.XBossBar.XBossBar;

public class LogsUtils {
    private static XBossBar plugin;

    public static void init(XBossBar plugin) {
        LogsUtils.plugin = plugin;
    }

    public static void sendStartupMessage() {
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("&c&lx&r&f&lBossBar #a0ff72plugin enabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("#fff18dVersion: #ffffff" + XBossBar.version));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("#fff18dDeveloped by: #ffffff" + String.join(", ", plugin.getDescription().getAuthors())));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
    }
    public static void sendShutdownMessage() {
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("&c&lx&r&f&lBossBar #ff7272plugin disabled!"));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("#fff18dVersion: #ffffff" + XBossBar.version));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("#fff18dDeveloped by: #ffffff" + String.join(", ", plugin.getDescription().getAuthors())));
        Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
    }

    public static void sendUpdateMessage() {
        String latestVersion = ModrinthUpdateChecker.isUpdateAvailable(XBossBar.version);

        if (!latestVersion.equals("false")) {
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("&c&lx&r&f&lBossBar &eNew Update Available!"));
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("&cCurrent Version: &f" + XBossBar.version));
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("&aLatest Version: &f" + latestVersion));
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor("&e&lYou can download it at: &fhttps://modrinth.com/plugin/xbossbar"));
            Bukkit.getConsoleSender().sendMessage(ChatUtils.formatColor(" "));
        }
    }
}

package dev.drygo.xbossbar.util;

import dev.drygo.xbossbar.update.ModrinthUpdateChecker;
import org.bukkit.Bukkit;
import dev.drygo.xbossbar.XBossBar;

import java.util.List;

public class LogsUtils {

    public static void sendStartupMessage() {
        XBossBar plugin = XBossBar.getInstance();
        List<String> authors = plugin.getPluginMeta().getAuthors();
        var console = Bukkit.getConsoleSender();
        console.sendMessage(ChatUtils.formatColor(" "));
        console.sendMessage(ChatUtils.formatColor("&c&lx&r&f&lBossBar #a0ff72plugin enabled!"));
        console.sendMessage(ChatUtils.formatColor("#fff18dVersion: #ffffff" + plugin.getPluginMeta().getVersion()));
        console.sendMessage(ChatUtils.formatColor("#fff18dDeveloped by: #ffffff" + String.join(", ", authors)));
        console.sendMessage(ChatUtils.formatColor(" "));
    }
    public static void sendShutdownMessage() {
        XBossBar plugin = XBossBar.getInstance();
        List<String> authors = plugin.getPluginMeta().getAuthors();
        var console = Bukkit.getConsoleSender();
        console.sendMessage(ChatUtils.formatColor(" "));
        console.sendMessage(ChatUtils.formatColor("&c&lx&r&f&lBossBar #ff7272plugin disabled!"));
        console.sendMessage(ChatUtils.formatColor("#fff18dVersion: #ffffff" + plugin.getPluginMeta().getVersion()));
        console.sendMessage(ChatUtils.formatColor("#fff18dDeveloped by: #ffffff" + String.join(", ", authors)));
        console.sendMessage(ChatUtils.formatColor(" "));
    }

    public static void sendUpdateMessage() {
        XBossBar plugin = XBossBar.getInstance();
        var console = Bukkit.getConsoleSender();
        String latestVersion = ModrinthUpdateChecker.isUpdateAvailable(plugin.getPluginMeta().getVersion());
        if (!latestVersion.equals("false")) {
            console.sendMessage(ChatUtils.formatColor(" "));
            console.sendMessage(ChatUtils.formatColor("&c&lx&r&f&lBossBar &eNew Update Available!"));
            console.sendMessage(ChatUtils.formatColor("&cCurrent Version: &f" + plugin.getPluginMeta().getVersion()));
            console.sendMessage(ChatUtils.formatColor("&aLatest Version: &f" + latestVersion));
            console.sendMessage(ChatUtils.formatColor(" "));
            console.sendMessage(ChatUtils.formatColor("&e&lYou can download it at: &fhttps://modrinth.com/plugin/xbossbar"));
            console.sendMessage(ChatUtils.formatColor(" "));
        }
    }
}

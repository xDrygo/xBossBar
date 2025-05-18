package org.eldrygo.XBossBar.Utils;

import org.bukkit.Bukkit;

public class DevUtils {
    public static boolean isPAPIInstalled () {
        return Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null;
    }
}

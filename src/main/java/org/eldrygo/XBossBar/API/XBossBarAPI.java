package org.eldrygo.XBossBar.API;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.eldrygo.XBossBar.Models.BossBarModel;

import java.util.Set;

public class XBossBarAPI {

    private static BossBarManager bossBarManager;

    public static void setManager(BossBarManager manager) {
        bossBarManager = manager;
    }

    public static void createBossBar(String name, BossBarModel model) {
        bossBarManager.createBossBar(name, model);
    }

    public static void removeBossBar(String name) {
        bossBarManager.removeBossBar(name);
    }

    public static void clearAllBossBars() {
        bossBarManager.clearAllBossBars();
    }

    public static void addPlayerToBossBar(String name, Player player) {
        bossBarManager.addPlayerToBossBar(name, player);
    }

    public static void removePlayerFromBossBar(String name, Player player) {
        bossBarManager.removePlayerFromBossBar(name, player);
    }

    public static void setTitle(String name, String title) {
        bossBarManager.setTitle(name, title);
    }

    public static void setProgress(String name, double progress) {
        bossBarManager.setProgress(name, progress);
    }

    public static void setStyle(String name, BarStyle style) {
        bossBarManager.setStyle(name, style);
    }

    public static void setColor(String name, BarColor color) {
        bossBarManager.setColor(name, color);
    }

    public static BossBar getBossBar(String name) {
        return bossBarManager.getBossBar(name);
    }

    public static BossBarModel getBossBarModel(String name) {
        return bossBarManager.getBossBarModel(name);
    }

    public static Set<String> getBossBarNames() {
        return bossBarManager.getBossBarNames();
    }
}

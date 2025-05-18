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

    public static void createBossBar(String id, String title, BarColor color, BarStyle style, boolean perplayer) {
        BossBarModel model = new BossBarModel(title, color, style, 1.0, perplayer);
        bossBarManager.createBossBar(id, model);
    }

    public static void removeBossBar(String id) {
        bossBarManager.removeBossBar(id);
    }

    public static void clearAllBossBars() {
        bossBarManager.clearAllBossBars();
    }

    public static void addPlayerToBossBar(String id, Player player) {
        bossBarManager.addPlayerToBossBar(id, player);
    }

    public static void removePlayerFromBossBar(String id, Player player) {
        bossBarManager.removePlayerFromBossBar(id, player);
    }

    public static void setTitle(String id, String title) {
        bossBarManager.setTitle(id, title);
    }

    public static void setProgress(String id, double progress) {
        bossBarManager.setProgress(id, progress);
    }

    public static void setStyle(String id, BarStyle style) {
        bossBarManager.setStyle(id, style);
    }

    public static void setColor(String id, BarColor color) {
        bossBarManager.setColor(id, color);
    }

    public static BossBar getBossBar(String id) {
        return bossBarManager.getBossBar(id);
    }

    public static BossBarModel getBossBarModel(String id) {
        return bossBarManager.getBossBarModel(id);
    }

    public static Set<String> getBossBarNames() {
        return bossBarManager.getBossBarNames();
    }
}

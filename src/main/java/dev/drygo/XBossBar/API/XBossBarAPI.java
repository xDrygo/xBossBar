package dev.drygo.XBossBar.API;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import dev.drygo.XBossBar.Managers.BossBarManager;
import dev.drygo.XBossBar.Models.BossBarModel;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

public class XBossBarAPI {

    public static void createBossBar(String id, String title, BarColor color, BarStyle style, boolean perPlayer) {
        BossBarModel model = new BossBarModel(title, color, style, 1.0, perPlayer);
        BossBarManager.createBossBar(id, model);
    }

    public static void removeBossBar(String id) {
        BossBarManager.removeBossBar(id);
    }

    public static void clearAllBossBars() {
        BossBarManager.clearAllBossBars();
    }

    public static void addPlayer(String id, Player player) {
        BossBarManager.addPlayerToBossBar(id, player);
    }

    public static void removePlayer(String id, Player player) {
        BossBarManager.removePlayerFromBossBar(id, player);
    }

    public static boolean hasPlayer(String id, Player player) {
        return BossBarManager.hasPlayerBossBar(player, id);
    }

    public static boolean hasPlayer(String id, UUID uuid) {
        return BossBarManager.hasPlayerBossBar(uuid, id);
    }

    public static Set<String> getPlayerBossBars(Player player) {
        return  BossBarManager.getPlayerBossBars(player);
    }

    public static Set<String> getPlayerBossBars(UUID playerId) {
        return  BossBarManager.getPlayerBossBars(playerId);
    }

    public static void setTitle(String id, String title) {
        BossBarManager.setTitle(id, title);
    }

    public static void setProgress(String id, double progress) {
        BossBarManager.setProgress(id, progress);
    }

    public static void setStyle(String id, BarStyle style) {
        BossBarManager.setStyle(id, style);
    }

    public static void setColor(String id, BarColor color) {
        BossBarManager.setColor(id, color);
    }

    public static BossBar getBossBar(String id) {
        return BossBarManager.getBossBar(id);
    }

    public static BossBarModel getBossBarModel(String id) {
        return BossBarManager.getBossBarModel(id);
    }

    public static Set<String> getBossBarNames() {
        return BossBarManager.getBossBarNames();
    }
}

package org.eldrygo.XBossBar.Hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.eldrygo.XBossBar.Managers.BossBarManager;
import org.eldrygo.XBossBar.Models.BossBarModel;
import org.eldrygo.XBossBar.XBossBar;

public class XBossBarExpansion extends PlaceholderExpansion {

    private final XBossBar plugin;
    private final BossBarManager bossBarManager;

    public XBossBarExpansion(XBossBar plugin, BossBarManager bossBarManager) {
        this.plugin = plugin;
        this.bossBarManager = bossBarManager;
    }

    @Override
    public String getIdentifier() {
        return "xbossbar";
    }

    @Override
    public String getAuthor() {
        return "Drygo";
    }

    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, String params) {
        if (params == null || params.isEmpty()) return null;

        String[] parts = params.split("_", 2);
        if (parts.length != 2) return null;

        String type = parts[0];
        String barName = parts[1];

        BossBarModel model = bossBarManager.getBossBarModel(barName);
        if (model == null) return null;

        switch (type.toLowerCase()) {
            case "title" -> {
                if (offlinePlayer.isOnline() && model.isPersonalized()) {
                    Player player = offlinePlayer.getPlayer();
                    return player != null ? model.resolveTitleFor(player) : "";
                }
                return model.getTitle();
            }
            case "progress" -> {
                return String.format("%.2f", model.getProgress() * 100) + "%";
            }
            case "color" -> {
                return model.getColor().name();
            }
            case "style" -> {
                return model.getStyle().name();
            }
        }
        return null;
    }
}

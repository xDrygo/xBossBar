package dev.drygo.xbossbar.hook;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import dev.drygo.xbossbar.manager.BossBarManager;
import dev.drygo.xbossbar.model.BossBarModel;
import dev.drygo.xbossbar.XBossBar;
import org.checkerframework.checker.nullness.qual.NonNull;

public class XBossBarExpansion extends PlaceholderExpansion {


    @Override
    public String getIdentifier() {
        return "xbossbar";
    }

    @Override
    public String getAuthor() {
        return "33drygo";
    }

    @Override
    public String getVersion() {
        return XBossBar.getInstance().getPluginMeta().getVersion();
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer offlinePlayer, @NonNull String params) {
        if (params.isEmpty()) return null;

        String[] parts = params.split("_", 2);
        if (parts.length != 2) return null;

        String type = parts[0];
        String barName = parts[1];

        BossBarModel model = BossBarManager.getBossBarModel(barName);
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

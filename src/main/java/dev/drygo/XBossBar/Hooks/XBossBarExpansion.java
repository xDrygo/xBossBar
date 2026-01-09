package dev.drygo.XBossBar.Hooks;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import dev.drygo.XBossBar.Managers.BossBarManager;
import dev.drygo.XBossBar.Models.BossBarModel;
import dev.drygo.XBossBar.XBossBar;
import org.jspecify.annotations.NonNull;

public class XBossBarExpansion extends PlaceholderExpansion {

    private final XBossBar plugin;

    public XBossBarExpansion(XBossBar plugin) {
        this.plugin = plugin;
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

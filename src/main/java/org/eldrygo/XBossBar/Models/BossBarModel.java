package org.eldrygo.XBossBar.Models;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.eldrygo.XBossBar.Utils.ChatUtils;
import org.eldrygo.XBossBar.Utils.DevUtils;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class BossBarModel {

    private String originalTitle;
    private String resolvedTitle;
    private BarColor color;
    private BarStyle style;
    private double progress;
    private final boolean personalized;

    private final Map<UUID, String> perPlayerTitles = new ConcurrentHashMap<>();

    public BossBarModel(String originalTitle, BarColor color, BarStyle style, double progress, boolean personalized) {
        this.originalTitle = originalTitle;
        this.color = color;
        this.style = style;
        this.progress = progress;
        this.personalized = personalized;
        this.resolvedTitle = resolveTitleFor(null); // global title
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getTitle() {
        return resolvedTitle;
    }

    public BarColor getColor() {
        return color;
    }

    public void setColor(BarColor color) {
        this.color = color;
    }

    public BarStyle getStyle() {
        return style;
    }

    public void setStyle(BarStyle style) {
        this.style = style;
    }

    public double getProgress() {
        return progress;
    }

    public boolean isPersonalized() {
        return personalized;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void setTitle(String title) {
        this.originalTitle = title;
        perPlayerTitles.clear();
        if (!personalized) {
            this.resolvedTitle = resolveTitleFor(null);
        }
    }

    public String getResolvedTitleFor(Player player) {
        if (!personalized || player == null) return resolvedTitle;

        return perPlayerTitles.computeIfAbsent(player.getUniqueId(), uuid -> {
            String output;
            if (DevUtils.isPAPIInstalled()) {
                output = PlaceholderAPI.setPlaceholders(player, originalTitle);
            } else {
                output = originalTitle;
            }
            return ChatUtils.formatColor(output);
        });
    }

    public String resolveTitleFor(Player player) {
        if (player != null) {
            String output;
            if (DevUtils.isPAPIInstalled()) {
                output = PlaceholderAPI.setPlaceholders(player, originalTitle);
            } else {
                output = originalTitle;
            }
            return ChatUtils.formatColor(output);
        } else {
            String output;
            if (DevUtils.isPAPIInstalled()) {
                output = PlaceholderAPI.setPlaceholders(null, originalTitle);
            } else {
                output = originalTitle;
            }
            return ChatUtils.formatColor(output);
        }
    }

    public void updateTitle() {
        if (!personalized) {
            this.resolvedTitle = resolveTitleFor(null);
        } else {
            perPlayerTitles.clear();
        }
    }
}
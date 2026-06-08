package dev.drygo.xbossbar.manager;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import dev.drygo.xbossbar.model.BossBarModel;
import dev.drygo.xbossbar.XBossBar;
import dev.drygo.xbossbar.util.DebugUtils;

import java.util.*;

public class BossBarManager {
    private static XBossBar plugin;

    private static final Map<String, BossBar> globalBars = new LinkedHashMap<>();
    private static final Map<String, Map<UUID, BossBar>> personalizedBars = new LinkedHashMap<>();
    private static final Map<String, BossBarModel> bossBarModels = new LinkedHashMap<>();

    private static final Map<UUID, LinkedHashSet<String>> playerBossBars = new HashMap<>();

    private static BukkitRunnable bossBarUpdateTask;

    public static void init() {
        plugin = XBossBar.getInstance();
    }

    public static void startBossBarUpdateTask() {
        bossBarUpdateTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, BossBarModel> entry : bossBarModels.entrySet()) {
                    String name = entry.getKey();
                    BossBarModel model = entry.getValue();

                    if (model.isPersonalized()) {
                        Map<UUID, BossBar> playerBars = personalizedBars.get(name);
                        if (playerBars == null || playerBars.isEmpty()) continue;

                        for (Map.Entry<UUID, BossBar> pEntry : playerBars.entrySet()) {
                            Player player = plugin.getServer().getPlayer(pEntry.getKey());
                            BossBar bar = pEntry.getValue();
                            if (player != null && player.isOnline()) {
                                bar.setTitle(model.resolveTitleFor(player));
                                bar.setProgress(model.getProgress());
                                bar.setStyle(model.getStyle());
                                bar.setColor(model.getColor());
                            }
                        }
                    } else {
                        BossBar bar = globalBars.get(name);
                        if (bar != null) {
                            model.updateTitle();
                            bar.setTitle(model.getTitle());
                            bar.setProgress(model.getProgress());
                            bar.setStyle(model.getStyle());
                            bar.setColor(model.getColor());
                        }
                    }
                }
            }
        };
        bossBarUpdateTask.runTaskTimer(plugin, 0L, 1L);
    }

    public static void stopBossBarUpdateTask() {
        if (bossBarUpdateTask != null) {
            bossBarUpdateTask.cancel();
            bossBarUpdateTask = null;
        }
    }

    private static void updateBossBarForAllPlayers(String name) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            if (model.isPersonalized()) {
                Map<UUID, BossBar> playerBars = personalizedBars.get(name);
                if (playerBars == null) return;

                for (UUID playerId : new ArrayList<>(playerBars.keySet())) {
                    BossBar bar = playerBars.get(playerId);
                    if (bar != null) {
                        Player player = plugin.getServer().getPlayer(playerId);
                        if (player != null && player.isOnline()) {
                            bar.removePlayer(player);
                            playerBars.remove(playerId);
                            addPlayerToBossBar(name, player);
                        }
                    }
                }
            } else {
                BossBar bar = globalBars.get(name);
                if (bar != null) {
                    List<Player> players = new ArrayList<>(bar.getPlayers());

                    for (Player player : players) {
                        bar.removePlayer(player);
                    }

                    bar.setTitle(model.getTitle());
                    bar.setProgress(model.getProgress());
                    bar.setColor(model.getColor());
                    bar.setStyle(model.getStyle());

                    for (Player player : players) {
                        bar.addPlayer(player);
                    }
                }
            }
        }
    }

    public static void createBossBar(String name, BossBarModel model) {
        if (bossBarModels.containsKey(name)) return;

        bossBarModels.put(name, model);

        if (!model.isPersonalized()) {
            BossBar bar = plugin.getServer().createBossBar(
                    model.getTitle(),
                    model.getColor(),
                    model.getStyle()
            );
            bar.setProgress(model.getProgress());
            globalBars.put(name, bar);
        } else {
            personalizedBars.put(name, new LinkedHashMap<>());
        }
        updateBossBarForAllPlayers(name);
    }

    public static void addPlayerToBossBar(String name, Player player) {
        BossBarModel model = bossBarModels.get(name);
        if (model == null) {
            DebugUtils.debug("Attempting to add player " + player.getName() + " to bossbar '" + name + "' that does not exist");
            return;
        }

        playerBossBars.computeIfAbsent(player.getUniqueId(), k -> new LinkedHashSet<>()).add(name);
        DebugUtils.debug("Registered: " + player.getName() + " now has bossbar '" + name + "'");

        if (model.isPersonalized()) {
            Map<UUID, BossBar> playerBars = personalizedBars.get(name);
            if (playerBars == null) return;

            if (playerBars.containsKey(player.getUniqueId())) {
                DebugUtils.debug("Player " + player.getName() + " already has personalized bossbar '" + name + "'");
                return;
            }

            BossBar bar = plugin.getServer().createBossBar(
                    model.resolveTitleFor(player),
                    model.getColor(),
                    model.getStyle()
            );
            bar.setProgress(model.getProgress());
            bar.addPlayer(player);
            playerBars.put(player.getUniqueId(), bar);
            DebugUtils.debug("Created personalized bossbar '" + name + "' for " + player.getName());
        } else {
            BossBar bar = globalBars.get(name);
            if (bar != null) {
                if (!bar.getPlayers().contains(player)) {
                    bar.addPlayer(player);
                    DebugUtils.debug("Added " + player.getName() + " to global bossbar '" + name + "'");
                }
            }
        }
    }

    public static void removePlayerFromBossBar(String name, Player player) {
        BossBarModel model = bossBarModels.get(name);
        if (model == null) return;

        LinkedHashSet<String> playerBars = playerBossBars.get(player.getUniqueId());
        if (playerBars != null) {
            playerBars.remove(name);
            if (playerBars.isEmpty()) {
                playerBossBars.remove(player.getUniqueId());
            }
        }

        if (model.isPersonalized()) {
            Map<UUID, BossBar> bars = personalizedBars.get(name);
            if (bars == null) return;
            BossBar bar = bars.remove(player.getUniqueId());
            if (bar != null) {
                bar.removeAll();
            }
        } else {
            BossBar bar = globalBars.get(name);
            if (bar != null) {
                bar.removePlayer(player);
            }
        }
    }

    public static void restorePlayerBossBars(Player player) {
        LinkedHashSet<String> savedBossBars = playerBossBars.get(player.getUniqueId());

        DebugUtils.debug("Restoring bossbars for " + player.getName() + " (UUID: " + player.getUniqueId() + ")");

        if (savedBossBars == null || savedBossBars.isEmpty()) {
            DebugUtils.debug("No saved bossbars for " + player.getName());
            DebugUtils.debug("Total players with saved bossbars: " + playerBossBars.size());
            return;
        }

        DebugUtils.debug("Saved bossbars for " + player.getName() + ": " + savedBossBars);
        DebugUtils.debug("Available BossBarModels: " + bossBarModels.keySet());

        for (String name : savedBossBars) {
            DebugUtils.debug("Attempting to restore bossbar '" + name + "' for " + player.getName());

            BossBarModel model = bossBarModels.get(name);
            if (model == null) {
                DebugUtils.debug("ERROR: Bossbar '" + name + "' no longer exists in bossBarModels");
                continue;
            }

            DebugUtils.debug("Model found for '" + name + "'. Is personalized: " + model.isPersonalized());

            if (model.isPersonalized()) {
                Map<UUID, BossBar> playerBars = personalizedBars.get(name);
                if (playerBars == null) {
                    DebugUtils.debug("ERROR: Personalized bar map does not exist for '" + name + "'");
                    DebugUtils.debug("Available personalized maps: " + personalizedBars.keySet());
                    continue;
                }

                BossBar bar = plugin.getServer().createBossBar(
                        model.resolveTitleFor(player),
                        model.getColor(),
                        model.getStyle()
                );
                bar.setProgress(model.getProgress());
                bar.addPlayer(player);
                playerBars.put(player.getUniqueId(), bar);
                DebugUtils.debug("✓ Restored personalized bossbar '" + name + "' for " + player.getName());
            } else {
                BossBar bar = globalBars.get(name);
                DebugUtils.debug("Global bossbar '" + name + "': " + (bar != null ? "exists" : "DOES NOT EXIST"));
                DebugUtils.debug("Available global bars: " + globalBars.keySet());

                if (bar != null) {
                    if (!bar.getPlayers().contains(player)) {
                        bar.addPlayer(player);
                        DebugUtils.debug("✓ Restored global bossbar '" + name + "' for " + player.getName());
                    } else {
                        DebugUtils.debug("Player was already in global bossbar '" + name + "'");
                    }
                } else {
                    DebugUtils.debug("ERROR: Global bossbar '" + name + "' does not exist in globalBars");
                }
            }
        }
    }

    public static void clearPlayerBossBarsVisual(Player player) {
        LinkedHashSet<String> savedBossBars = playerBossBars.get(player.getUniqueId());
        if (savedBossBars == null || savedBossBars.isEmpty()) {
            return;
        }

        DebugUtils.debug("Clearing visual bossbars for " + player.getName());

        for (String name : savedBossBars) {
            BossBarModel model = bossBarModels.get(name);
            if (model == null) continue;

            if (model.isPersonalized()) {
                Map<UUID, BossBar> bars = personalizedBars.get(name);
                if (bars != null) {
                    BossBar bar = bars.remove(player.getUniqueId());
                    if (bar != null) {
                        bar.removeAll();
                        DebugUtils.debug("Removed personalized bossbar '" + name + "' from " + player.getName());
                    }
                }
            } else {
                BossBar bar = globalBars.get(name);
                if (bar != null) {
                    bar.removePlayer(player);
                    DebugUtils.debug("Removed " + player.getName() + " from global bossbar '" + name + "'");
                }
            }
        }

        DebugUtils.debug("Bossbars saved for next connection: " + savedBossBars);
    }

    public static void addAllBossBarsToPlayer(Player player) {
        removeAllBossBarsFromPlayer(player);

        for (String name : bossBarModels.keySet()) {
            addPlayerToBossBar(name, player);
        }
    }

    public static void removeAllBossBarsFromPlayer(Player player) {
        for (String name : bossBarModels.keySet()) {
            removePlayerFromBossBar(name, player);
        }
    }

    public static Set<String> getPlayerBossBars(UUID playerId) {
        LinkedHashSet<String> bars = playerBossBars.get(playerId);
        return bars != null ? new LinkedHashSet<>(bars) : new LinkedHashSet<>();
    }

    public static boolean hasPlayerBossBar(Player player, String bossBarName) {
        LinkedHashSet<String> bars = playerBossBars.get(player.getUniqueId());
        return bars != null && bars.contains(bossBarName);
    }

    public static boolean hasPlayerBossBar(UUID playerId, String bossBarName) {
        LinkedHashSet<String> bars = playerBossBars.get(playerId);
        return bars != null && bars.contains(bossBarName);
    }

    public static Set<String> getPlayerBossBars(Player player) {
        return getPlayerBossBars(player.getUniqueId());
    }

    public static void setTitle(String name, String title) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setTitle(title);
            updateBossBarForAllPlayers(name);
        }
    }

    public static void setProgress(String name, double progress) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setProgress(progress);
            updateBossBarForAllPlayers(name);
        }
    }

    public static void setStyle(String name, BarStyle style) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setStyle(style);
            updateBossBarForAllPlayers(name);
        }
    }

    public static void setColor(String name, BarColor color) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setColor(color);
            updateBossBarForAllPlayers(name);
        }
    }

    public static void removeBossBar(String name) {
        BossBarModel model = bossBarModels.remove(name);
        if (model == null) return;

        for (LinkedHashSet<String> playerBars : playerBossBars.values()) {
            playerBars.remove(name);
        }

        if (model.isPersonalized()) {
            Map<UUID, BossBar> bars = personalizedBars.remove(name);
            if (bars != null) {
                for (BossBar bar : bars.values()) {
                    bar.removeAll();
                }
            }
        } else {
            BossBar bar = globalBars.remove(name);
            if (bar != null) {
                bar.removeAll();
            }
        }
    }

    public static void clearAllBossBars() {
        for (String name : new LinkedHashSet<>(bossBarModels.keySet())) {
            removeBossBar(name);
        }
        playerBossBars.clear();
    }

    public static BossBar getBossBar(String name) {
        return globalBars.get(name);
    }

    public static BossBarModel getBossBarModel(String name) {
        return bossBarModels.get(name);
    }

    public static Set<String> getBossBarNames() {
        return bossBarModels.keySet();
    }
}
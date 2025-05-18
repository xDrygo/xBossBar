package org.eldrygo.XBossBar.Managers;

import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.eldrygo.XBossBar.Models.BossBarModel;
import org.eldrygo.XBossBar.XBossBar;

import java.util.*;

public class BossBarManager {

    private final XBossBar plugin;
    private final Map<String, BossBar> globalBars = new HashMap<>();
    private final Map<String, Map<UUID, BossBar>> personalizedBars = new HashMap<>();
    private final Map<String, BossBarModel> bossBarModels = new HashMap<>();
    private BukkitRunnable bossBarUpdateTask;

    public BossBarManager(XBossBar plugin) {
        this.plugin = plugin;
    }

    public void startBossBarUpdateTask() {
        bossBarUpdateTask = new BukkitRunnable() {
            @Override
            public void run() {
                for (Map.Entry<String, BossBarModel> entry : bossBarModels.entrySet()) {
                    String name = entry.getKey();
                    BossBarModel model = entry.getValue();

                    if (model.isPersonalized()) {
                        // Obtener las barras personalizadas para este nombre
                        Map<UUID, BossBar> playerBars = personalizedBars.get(name);
                        if (playerBars == null || playerBars.isEmpty()) continue; // Si no existen barras personalizadas, pasar al siguiente

                        for (Map.Entry<UUID, BossBar> pEntry : playerBars.entrySet()) {
                            Player player = plugin.getServer().getPlayer(pEntry.getKey());
                            BossBar bar = pEntry.getValue();
                            if (player != null && player.isOnline()) {
                                // Actualiza el título y progreso solo si el jugador está en línea
                                bar.setTitle(model.resolveTitleFor(player));
                                bar.setProgress(model.getProgress());
                                bar.setStyle(model.getStyle());
                                bar.setColor(model.getColor());
                            }
                        }
                    } else {
                        // Barra global, no personalizada
                        BossBar bar = globalBars.get(name);
                        if (bar != null) {
                            model.updateTitle(); // Solo actualiza si no es personalizado
                            bar.setTitle(model.getTitle());
                            bar.setProgress(model.getProgress());
                            bar.setStyle(model.getStyle());
                            bar.setColor(model.getColor());
                        }
                    }
                }
            }
        };
        // Ejecuta la tarea periódicamente cada 20 ticks (1 segundo)
        bossBarUpdateTask.runTaskTimer(plugin, 0L, 20L);
    }

    public void stopBossBarUpdateTask() {
        if (bossBarUpdateTask != null) {
            bossBarUpdateTask.cancel();
            bossBarUpdateTask = null;
        }
    }
    private void updateBossBarForAllPlayers(String name) {
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
                    // Guardar jugadores que la tenían
                    List<Player> players = new ArrayList<>(bar.getPlayers());

                    // Quitar barra a esos jugadores
                    for (Player player : players) {
                        bar.removePlayer(player);
                    }

                    // Actualizar propiedades de la barra
                    bar.setTitle(model.getTitle());
                    bar.setProgress(model.getProgress());
                    bar.setColor(model.getColor());
                    bar.setStyle(model.getStyle());

                    // Volver a agregar la barra a los mismos jugadores
                    for (Player player : players) {
                        bar.addPlayer(player);
                    }
                }
            }
        }
    }

    public void createBossBar(String name, BossBarModel model) {
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
            personalizedBars.put(name, new HashMap<>());
        }
        updateBossBarForAllPlayers(name);
    }

    public void addPlayerToBossBar(String name, Player player) {
        BossBarModel model = bossBarModels.get(name);
        if (model == null) return;

        if (model.isPersonalized()) {
            Map<UUID, BossBar> playerBars = personalizedBars.get(name);
            if (playerBars == null) return;

            // Verifica si el jugador ya tiene la BossBar personalizada
            if (playerBars.containsKey(player.getUniqueId())) {
                return; // Ya tiene la BossBar, no agregamos otra
            }

            BossBar bar = plugin.getServer().createBossBar(
                    model.resolveTitleFor(player),
                    model.getColor(),
                    model.getStyle()
            );
            bar.setProgress(model.getProgress());
            bar.addPlayer(player);
            playerBars.put(player.getUniqueId(), bar);
        } else {
            BossBar bar = globalBars.get(name);
            if (bar != null) {
                // Verifica si el jugador ya está en la BossBar global
                if (!bar.getPlayers().contains(player)) {
                    bar.addPlayer(player);
                }
            }
        }
    }

    public void removePlayerFromBossBar(String name, Player player) {
        BossBarModel model = bossBarModels.get(name);
        if (model == null) return;

        if (model.isPersonalized()) {
            Map<UUID, BossBar> playerBars = personalizedBars.get(name);
            if (playerBars == null) return;
            BossBar bar = playerBars.remove(player.getUniqueId());
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

    public void setTitle(String name, String title) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setTitle(title);
            updateBossBarForAllPlayers(name);
        }
    }

    public void setProgress(String name, double progress) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setProgress(progress);
            updateBossBarForAllPlayers(name);
        }
    }

    public void setStyle(String name, BarStyle style) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setStyle(style);
            updateBossBarForAllPlayers(name);
        }
    }
    public void setColor(String name, BarColor color) {
        BossBarModel model = bossBarModels.get(name);
        if (model != null) {
            model.setColor(color);
            updateBossBarForAllPlayers(name);
        }
    }

    public void removeBossBar(String name) {
        BossBarModel model = bossBarModels.remove(name);
        if (model == null) return;

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

    public void clearAllBossBars() {
        for (String name : new HashSet<>(bossBarModels.keySet())) {
            removeBossBar(name);
        }
    }

    public BossBar getBossBar(String name) {
        return globalBars.get(name);
    }

    public BossBarModel getBossBarModel(String name) {
        return bossBarModels.get(name);
    }

    public Set<String> getBossBarNames() {
        return bossBarModels.keySet();
    }
}

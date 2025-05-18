package org.eldrygo.XBossBar.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.eldrygo.XBossBar.Managers.BossBarManager;

import java.util.Set;

public class PlayerListener implements Listener {

    private final BossBarManager bossBarManager;

    public PlayerListener(BossBarManager bossBarManager) {
        this.bossBarManager = bossBarManager;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Set<String> bossBars = bossBarManager.getBossBarNames();
        for (String b : bossBars) {
            bossBarManager.addPlayerToBossBar(b, event.getPlayer());
        }
    }
}

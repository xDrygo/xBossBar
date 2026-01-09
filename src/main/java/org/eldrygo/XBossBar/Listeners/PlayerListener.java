package org.eldrygo.XBossBar.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.eldrygo.XBossBar.Managers.BossBarManager;

public class PlayerListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        BossBarManager.addAllBossBarsToPlayer(event.getPlayer());
    }
}

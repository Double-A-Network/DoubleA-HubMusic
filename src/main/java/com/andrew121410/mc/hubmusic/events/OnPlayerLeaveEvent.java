package com.andrew121410.mc.hubmusic.events;

import com.andrew121410.mc.hubmusic.HubMusic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeaveEvent implements Listener {

    private HubMusic plugin;

    public OnPlayerLeaveEvent(HubMusic plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.getPlayerInitializer().unload(event.getPlayer());
    }
}

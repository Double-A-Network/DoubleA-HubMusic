package com.andrew121410.mc.hubmusic.listeners;

import com.andrew121410.mc.hubmusic.HubMusic;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private HubMusic plugin;

    public OnPlayerJoinEvent(HubMusic plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        this.plugin.getPlayerInitializer().load(event.getPlayer());
    }
}

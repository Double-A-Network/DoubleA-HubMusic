package com.andrew121410.HubMusic.Events;

import com.andrew121410.HubMusic.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class OnPlayerLeaveEvent implements Listener {

    private Main plugin;

    public OnPlayerLeaveEvent(Main plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        this.plugin.getPlayerInitializer().unload(event.getPlayer());
    }
}

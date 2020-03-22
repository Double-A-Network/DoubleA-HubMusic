package com.andrew121410.HubMusic.Events;

import com.andrew121410.HubMusic.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerJoinEvent implements Listener {

    private Main plugin;

    public OnPlayerJoinEvent(Main plugin) {
        this.plugin = plugin;

        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (this.plugin.getSongPlayer().getRadioSongPlayer() != null) {
            this.plugin.getSongPlayer().getRadioSongPlayer().addPlayer(event.getPlayer());
        }

    }
}

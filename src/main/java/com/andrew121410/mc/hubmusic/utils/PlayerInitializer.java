package com.andrew121410.mc.hubmusic.utils;

import com.andrew121410.mc.hubmusic.HubMusic;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerInitializer {

    private HubMusic plugin;

    public PlayerInitializer(HubMusic plugin) {
        this.plugin = plugin;
    }

    public void load(Player player) {
        if (this.plugin.getSongPlayer().getRadioSongPlayer() != null) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    plugin.getSongPlayer().getRadioSongPlayer().addPlayer(player);
                }
            }.runTaskLater(this.plugin, 20L * this.plugin.getConfig().getInt("joinServerTimeDelay"));
        }
    }

    public void unload(Player player) {
        if (this.plugin.getSongPlayer().getRadioSongPlayer() != null) {
            this.plugin.getSongPlayer().getRadioSongPlayer().removePlayer(player);
        }
    }
}

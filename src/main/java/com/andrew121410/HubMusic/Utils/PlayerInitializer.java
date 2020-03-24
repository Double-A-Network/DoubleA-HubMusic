package com.andrew121410.HubMusic.Utils;

import com.andrew121410.HubMusic.Main;
import org.bukkit.entity.Player;

public class PlayerInitializer {

    private Main plugin;

    public PlayerInitializer(Main plugin) {
        this.plugin = plugin;
    }

    public void load(Player player) {
        if (this.plugin.getSongPlayer().getRadioSongPlayer() != null) {
            this.plugin.getSongPlayer().getRadioSongPlayer().addPlayer(player);
        }
    }

    public void unload(Player player) {
        this.plugin.getSongPlayer().getRadioSongPlayer().removePlayer(player);
    }
}

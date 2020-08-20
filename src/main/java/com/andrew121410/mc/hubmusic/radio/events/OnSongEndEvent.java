package com.andrew121410.mc.hubmusic.radio.events;

import com.andrew121410.mc.hubmusic.HubMusic;
import com.xxmicloxx.NoteBlockAPI.event.SongEndEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnSongEndEvent implements Listener {

    private HubMusic plugin;

    public OnSongEndEvent(HubMusic plugin) {
        this.plugin = plugin;
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    @EventHandler
    public void onSongEnd(SongEndEvent event) {
        this.plugin.getSongPlayer().start();
    }
}

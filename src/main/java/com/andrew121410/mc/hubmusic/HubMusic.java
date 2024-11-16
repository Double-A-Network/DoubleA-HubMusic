package com.andrew121410.mc.hubmusic;

import com.andrew121410.mc.hubmusic.commands.HubMusicCMD;
import com.andrew121410.mc.hubmusic.listeners.OnPlayerJoinEvent;
import com.andrew121410.mc.hubmusic.listeners.OnPlayerLeaveEvent;
import com.andrew121410.mc.hubmusic.radio.SongPlayer;
import com.andrew121410.mc.hubmusic.radio.events.OnSongEndEvent;
import com.andrew121410.mc.hubmusic.utils.PlayerInitializer;
import com.andrew121410.mc.hubmusic.utils.SetListMap;
import com.andrew121410.mc.hubmusic.utils.SongLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class HubMusic extends JavaPlugin {

    private static HubMusic plugin;

    private SetListMap setListMap;

    private PlayerInitializer playerInitializer;

    private SongLoader songLoader;
    private SongPlayer songPlayer;

    @Override
    public void onEnable() {
        plugin = this;
        this.setListMap = new SetListMap();
        this.playerInitializer = new PlayerInitializer(this);

        if (!this.getDataFolder().isDirectory()) {
            this.getDataFolder().mkdir();
        }

        this.songLoader = new SongLoader(this);
        this.songLoader.loadSongCache();

        this.songPlayer = new SongPlayer(this);

        regConfig();
        regEvents();
        regCommands();

        this.songPlayer.start();
    }

    public void regEvents() {
        new OnPlayerJoinEvent(plugin);
        new OnPlayerLeaveEvent(plugin);
        new OnSongEndEvent(plugin);
    }

    public void regCommands() {
        new HubMusicCMD(this);
    }

    public void regConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        this.setListMap.getSongMap().clear();
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public SongLoader getSongLoader() {
        return songLoader;
    }

    public SongPlayer getSongPlayer() {
        return songPlayer;
    }

    public PlayerInitializer getPlayerInitializer() {
        return playerInitializer;
    }

    public static HubMusic getPlugin() {
        return plugin;
    }
}

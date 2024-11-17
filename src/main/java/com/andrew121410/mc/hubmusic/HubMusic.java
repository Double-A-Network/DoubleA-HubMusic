package com.andrew121410.mc.hubmusic;

import com.andrew121410.mc.hubmusic.commands.HubMusicCMD;
import com.andrew121410.mc.hubmusic.listeners.OnPlayerJoinEvent;
import com.andrew121410.mc.hubmusic.listeners.OnPlayerLeaveEvent;
import com.andrew121410.mc.hubmusic.song.SongPlayer;
import com.andrew121410.mc.hubmusic.listeners.OnSongEndEvent;
import com.andrew121410.mc.hubmusic.utils.PlayerInitializer;
import com.andrew121410.mc.hubmusic.utils.MemoryHolder;
import com.andrew121410.mc.hubmusic.song.SongLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class HubMusic extends JavaPlugin {

    private static HubMusic plugin;

    private MemoryHolder memoryHolder;

    private PlayerInitializer playerInitializer;

    private SongLoader songLoader;
    private SongPlayer songPlayer;

    @Override
    public void onEnable() {
        plugin = this;
        this.memoryHolder = new MemoryHolder();
        this.playerInitializer = new PlayerInitializer(this);

        this.songLoader = new SongLoader(this);
        this.songLoader.loadSongCache();

        this.songPlayer = new SongPlayer(this);

        registerConfig();
        registerListeners();
        registerCommands();

        this.songPlayer.start();
    }

    public void registerListeners() {
        new OnPlayerJoinEvent(plugin);
        new OnPlayerLeaveEvent(plugin);
        new OnSongEndEvent(plugin);
    }

    public void registerCommands() {
        new HubMusicCMD(this);
    }

    public void registerConfig() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.reloadConfig();
    }

    @Override
    public void onDisable() {
        this.memoryHolder.getSongMap().clear();
    }

    public MemoryHolder getSetListMap() {
        return memoryHolder;
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

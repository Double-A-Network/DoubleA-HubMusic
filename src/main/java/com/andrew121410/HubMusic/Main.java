package com.andrew121410.HubMusic;

import com.andrew121410.HubMusic.Commands.HubMusicCMD;
import com.andrew121410.HubMusic.Events.OnPlayerJoinEvent;
import com.andrew121410.HubMusic.Events.OnPlayerLeaveEvent;
import com.andrew121410.HubMusic.Radio.Events.OnSongEndEvent;
import com.andrew121410.HubMusic.Radio.SongPlayer;
import com.andrew121410.HubMusic.Utils.PlayerInitializer;
import com.andrew121410.HubMusic.Utils.SetListMap;
import com.andrew121410.HubMusic.Utils.SongLoader;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private static Main plugin;

    private SetListMap setListMap;
    private PlayerInitializer playerInitializer;

    private SongLoader songLoader;
    private SongPlayer songPlayer;

    public void onEnable() {
        plugin = this;
        this.setListMap = new SetListMap();
        this.playerInitializer = new PlayerInitializer(this);

        if (this.getDataFolder().isDirectory()) {
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

    public void onDisable() {
        this.setListMap.getSongMap().clear();
    }

    public SetListMap getSetListMap() {
        return setListMap;
    }

    public PlayerInitializer getPlayerInitializer() {
        return playerInitializer;
    }

    public SongLoader getSongLoader() {
        return songLoader;
    }

    public SongPlayer getSongPlayer() {
        return songPlayer;
    }

    public static Main getPlugin() {
        return plugin;
    }
}

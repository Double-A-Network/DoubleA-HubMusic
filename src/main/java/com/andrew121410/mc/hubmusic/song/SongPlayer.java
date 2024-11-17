package com.andrew121410.mc.hubmusic.song;

import com.andrew121410.mc.hubmusic.HubMusic;
import com.andrew121410.mc.world16utils.config.UnlinkedWorldLocation;
import com.andrew121410.mc.world16utils.utils.Utils;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class SongPlayer {

    private final Map<String, File> songsCache;

    private final HubMusic plugin;
    private PositionSongPlayer radioSongPlayer;

    // Config
    private boolean isEnabled;
    private String volume;
    private String distance;
    private UnlinkedWorldLocation location;

    public SongPlayer(HubMusic plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();

        this.isEnabled = this.plugin.getConfig().getString("isEnabled").equalsIgnoreCase("true");
        this.volume = this.plugin.getConfig().getString("volume");
        this.distance = this.plugin.getConfig().getString("distance");
        this.location = (UnlinkedWorldLocation) this.plugin.getConfig().get("Location");
    }

    public void reloadConfigOptions() {
        this.isEnabled = this.plugin.getConfig().getString("isEnabled").equalsIgnoreCase("true");
        this.volume = this.plugin.getConfig().getString("volume");
        this.distance = this.plugin.getConfig().getString("distance");
        this.location = (UnlinkedWorldLocation) this.plugin.getConfig().get("Location");

        this.plugin.getLogger().info("Reloaded the config options.");
    }

    public Song pickRandomSong() {
        String[] keys = this.songsCache.keySet().toArray(new String[0]);

        // If there are no songs in the cache, return null.
        if (keys.length == 0) {
            return null;
        }

        String key = keys[new Random().nextInt(keys.length)];
        File file = this.songsCache.get(key);
        return NBSDecoder.parse(file);
    }

    public void start() {
        if (!isEnabled) {
            this.plugin.getLogger().info("SongPlayer tried to start but isEnabled is false.");
            return;
        }

        Song song = pickRandomSong();
        if (song == null) {
            this.plugin.getLogger().warning("Song is null. We are in SongPlayer.start()");
            return;
        }

        this.radioSongPlayer = new PositionSongPlayer(song);

        // Add all online players to the song player.
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.radioSongPlayer.addPlayer(onlinePlayer);
        }

        if (location == null) {
            this.plugin.getLogger().warning("Location is null. We are in SongPlayer.start()");
            return;
        }
        if (!location.isWorldLoaded()) {
            this.plugin.getLogger().warning("World is not loaded. We are in SongPlayer.start()");
            return;
        }

        this.radioSongPlayer.setVolume(Utils.asByteOrElse(this.volume, (byte) 100));
        this.radioSongPlayer.setDistance(Utils.asIntegerOrElse(this.distance, 10));
        this.radioSongPlayer.setTargetLocation(location);
        this.radioSongPlayer.setRepeatMode(RepeatMode.NO);
        this.radioSongPlayer.setPlaying(true);
    }

    public void stop() {
        if (this.radioSongPlayer == null) return;

        // Remove all players from the song player.
        for (UUID playerUUID : this.radioSongPlayer.getPlayerUUIDs()) {
            this.radioSongPlayer.removePlayer(playerUUID);
        }

        this.radioSongPlayer.destroy();
        this.radioSongPlayer = null;
    }

    public PositionSongPlayer getRadioSongPlayer() {
        return radioSongPlayer;
    }
}

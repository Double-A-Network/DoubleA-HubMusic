package com.andrew121410.mc.hubmusic.radio;

import com.andrew121410.mc.hubmusic.HubMusic;
import com.andrew121410.mc.world16utils.config.UnlinkedWorldLocation;
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

    private Map<String, File> songsCache;

    private HubMusic plugin;

    private PositionSongPlayer radioSongPlayer;

    public SongPlayer(HubMusic plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();
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
        if (this.plugin.getConfig().getString("isEnabled").equalsIgnoreCase("false")) {
            return;
        }

        Song song = pickRandomSong();
        if (song == null) {
            this.plugin.getLogger().warning("No songs found in the cache.");
            return;
        }

        this.radioSongPlayer = new PositionSongPlayer(song);

        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.radioSongPlayer.addPlayer(onlinePlayer);
        }

        String s = this.plugin.getConfig().getString("volume");
        if (s == null) return;
        String a = this.plugin.getConfig().getString("distance");
        if (a == null) return;
        UnlinkedWorldLocation location = (UnlinkedWorldLocation) this.plugin.getConfig().get("Location");
        if (location == null) {
            this.plugin.getLogger().warning("Location is null. We are in SongPlayer.start()");
            return;
        }
        if (!location.isWorldLoaded()) {
            this.plugin.getLogger().warning("World is not loaded. We are in SongPlayer.start()");
            return;
        }

        byte byE = Byte.parseByte(s);
        this.radioSongPlayer.setVolume(byE);
        this.radioSongPlayer.setDistance(Integer.parseInt(a));
        this.radioSongPlayer.setTargetLocation(location);
        this.radioSongPlayer.setRepeatMode(RepeatMode.NO);
        this.radioSongPlayer.setPlaying(true);
    }

    public void stop() {
        if (this.radioSongPlayer == null) {
            return;
        }

        for (UUID playerUUID : this.radioSongPlayer.getPlayerUUIDs()) {
            this.radioSongPlayer.removePlayer(playerUUID);
        }

        this.radioSongPlayer.destroy();
    }

    public PositionSongPlayer getRadioSongPlayer() {
        return radioSongPlayer;
    }
}

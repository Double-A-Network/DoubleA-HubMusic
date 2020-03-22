package com.andrew121410.HubMusic.Radio;

import com.andrew121410.HubMusic.Main;
import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.PositionSongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class SongPlayer {

    private Map<String, File> songsCache;

    private Main plugin;

    private PositionSongPlayer radioSongPlayer;

    public SongPlayer(Main plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();
    }

    public Song pickRandomSong() {
        String[] keys = this.songsCache.keySet().toArray(new String[0]);
        String key = keys[new Random().nextInt(keys.length)];
        File file = this.songsCache.get(key);
        return NBSDecoder.parse(file);
    }

    public void start() {
        if (this.plugin.getConfig().getString("isEnabled").equalsIgnoreCase("false")) {
            return;
        }

        Song song = pickRandomSong();
        this.radioSongPlayer = new PositionSongPlayer(song);

        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            this.radioSongPlayer.addPlayer(onlinePlayer);
        }

        String s = this.plugin.getConfig().getString("volume");
        if (s == null) return;
        String a = this.plugin.getConfig().getString("distance");
        if (a == null) return;
        Location location = (Location) this.plugin.getConfig().get("Location");
        if (location == null) return;

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

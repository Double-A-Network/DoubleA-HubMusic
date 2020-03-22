package com.andrew121410.HubMusic.Utils;

import com.andrew121410.HubMusic.Main;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SongLoader {

    private Map<String, File> songsCache;

    private Main plugin;
    private File songsFile;

    public SongLoader(Main plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();
        this.songsFile = new File(this.plugin.getDataFolder() + File.separator + "songs");
    }

    public List<File> findAllSongs() {
        List<File> files = new ArrayList<>();

        if (songsFile == null) {
            plugin.getServer().broadcastMessage("SONGSFILE == null");
            throw new NullPointerException("SONGS FILE == NULL");
        }

        if (songsFile.listFiles() == null) {
            return null;
        }

        for (File file : songsFile.listFiles()) if (file.getName().endsWith(".nbs")) files.add(file);

        return files;
    }

    public List<Song> loadSongs() {
        List<File> songsFiles = findAllSongs();

        if (findAllSongs() == null) return null;

        List<Song> songs = new ArrayList<>();
        for (File file : songsFiles) songs.add(NBSDecoder.parse(file));
        return songs;
    }

    public List<String> getAllSongNames() {
        List<String> names = new ArrayList<>();
        List<Song> songs = loadSongs();
        if (songs == null) return null;
        for (Song loadSong : songs) names.add(loadSong.getTitle());
        return names;
    }

    public void loadSongCache() {
        List<File> songFiles = findAllSongs();

        if (songFiles == null) {
            this.plugin.getServer().broadcastMessage("LoadSongCache could not be loaded.");
        }

        for (File songFile : songFiles) {
            String without = FilenameUtils.getBaseName(songFile.getName());
            this.songsCache.putIfAbsent(without, songFile);
        }
    }
}

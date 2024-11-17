package com.andrew121410.mc.hubmusic.song;

import com.andrew121410.mc.hubmusic.HubMusic;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SongLoader {

    private final Map<String, File> songsCache;

    private final HubMusic plugin;
    private final File songsFolder;

    public SongLoader(HubMusic plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();
        this.songsFolder = new File(this.plugin.getDataFolder() + File.separator + "songs");

        if (!this.songsFolder.exists()) {
            if (this.songsFolder.mkdir()) {
                this.plugin.getLogger().info("Created the songs folder.");
            } else {
                this.plugin.getLogger().info("Failed to create the songs folder.");
            }
        }
    }

    public List<File> findAllSongs() {
        File[] filesArray = this.songsFolder.listFiles();
        if (filesArray == null) return new ArrayList<>();

        return Arrays.stream(filesArray)
                .filter(file -> file.getName().endsWith(".nbs"))
                .collect(Collectors.toList());
    }

    //Not used for now.
    public List<Song> loadSongs() {
        List<File> songsFiles = findAllSongs();

        if (findAllSongs() == null) return null;

        List<Song> songs = new ArrayList<>();
        for (File file : songsFiles) songs.add(NBSDecoder.parse(file));
        return songs;
    }

    public void loadSongCache() {
        List<File> songFiles = findAllSongs();

        if (songFiles == null || songFiles.isEmpty()) {
            this.plugin.getLogger().info("No songs found.");
            return;
        }

        for (File songFile : songFiles) {
            String nameOfFile = songFile.getName().replaceFirst("[.][^.]+$", "");
            this.songsCache.putIfAbsent(nameOfFile, songFile);
        }
    }
}

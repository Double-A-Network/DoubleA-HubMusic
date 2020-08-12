package com.andrew121410.mc.hubmusic.utils;

import com.andrew121410.mc.hubmusic.Main;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SongLoader {

    private Map<String, File> songsCache;

    private Main plugin;
    private File songsFolder;

    public SongLoader(Main plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();
        this.songsFolder = new File(this.plugin.getDataFolder() + File.separator + "songs");

        if (!this.songsFolder.isDirectory()) {
            this.songsFolder.mkdir();
            this.plugin.getServer().getConsoleSender().sendMessage("[HubMusic] Has created songs dir in HubMusic plugin folder.");
        }
    }

    public List<File> findAllSongs() {
        List<File> files;
        files = Arrays.stream(songsFolder.listFiles()).filter(file -> file.getName().endsWith(".nbs")).collect(Collectors.toList());
        return files;
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

        if (songFiles == null) {
            this.plugin.getServer().broadcastMessage("LoadSongCache could not be loaded.");
            return;
        }

        for (File songFile : songFiles) {
            String nameOfFile = songFile.getName().replaceFirst("[.][^.]+$", "");
            this.songsCache.putIfAbsent(nameOfFile, songFile);
        }
    }
}

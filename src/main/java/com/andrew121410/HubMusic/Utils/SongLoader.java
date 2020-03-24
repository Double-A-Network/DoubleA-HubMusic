package com.andrew121410.HubMusic.Utils;

import com.andrew121410.HubMusic.Main;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import org.bukkit.craftbukkit.libs.org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SongLoader {

    private Map<String, File> songsCache;

    private Main plugin;
    private File songsFile;

    public SongLoader(Main plugin) {
        this.plugin = plugin;
        this.songsCache = this.plugin.getSetListMap().getSongMap();
        this.songsFile = new File(this.plugin.getDataFolder() + File.separator + "songs");

        if (!this.songsFile.isDirectory()) {
            this.songsFile.mkdir();
            this.plugin.getServer().getConsoleSender().sendMessage("[HubMusic] Has created songs dir in HubMusic plugin folder.");
        }
    }

    public List<File> findAllSongs() {
        List<File> files;
        files = Arrays.stream(songsFile.listFiles()).filter(file -> file.getName().endsWith(".nbs")).collect(Collectors.toList());
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
            String without = FilenameUtils.getBaseName(songFile.getName());
            this.songsCache.putIfAbsent(without, songFile);
        }
    }
}

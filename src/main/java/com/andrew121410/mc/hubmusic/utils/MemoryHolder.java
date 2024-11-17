package com.andrew121410.mc.hubmusic.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MemoryHolder {

    private final Map<String, File> songNamesMap;

    public MemoryHolder() {
        this.songNamesMap = new HashMap<>();
    }

    public Map<String, File> getSongMap() {
        return songNamesMap;
    }
}

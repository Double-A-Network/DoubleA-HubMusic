package com.andrew121410.HubMusic.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SetListMap {

    private Map<String, File> songNamesMap;

    public SetListMap() {
        this.songNamesMap = new HashMap<>();
    }

    public Map<String, File> getSongMap() {
        return songNamesMap;
    }
}

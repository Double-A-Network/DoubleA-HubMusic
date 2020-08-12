package com.andrew121410.mc.hubmusic.utils;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;

public class Utils {

    public static Block getBlockPlayerIsLookingAt(Player player) {
        return player.getTargetBlock(null, 5);
    }
}

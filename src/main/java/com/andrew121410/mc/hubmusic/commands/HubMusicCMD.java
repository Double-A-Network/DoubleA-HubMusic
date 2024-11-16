package com.andrew121410.mc.hubmusic.commands;

import com.andrew121410.mc.hubmusic.HubMusic;
import com.andrew121410.mc.hubmusic.utils.Utils;
import com.andrew121410.mc.world16utils.chat.Translate;
import com.andrew121410.mc.world16utils.config.UnlinkedWorldLocation;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HubMusicCMD implements CommandExecutor {

    private HubMusic plugin;

    public HubMusicCMD(HubMusic plugin) {
        this.plugin = plugin;

        this.plugin.getCommand("hubmusic").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String idk, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can do this command.");
            return true;
        }

        if (!player.hasPermission("hubmusic.command")) {
            player.sendMessage("You don't have permission to use this command.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage(Translate.color("&6/hubmusic start"));
            player.sendMessage(Translate.color("&6/hubmusic stop"));
            player.sendMessage(Translate.color("&6/hubmusic reload"));
            player.sendMessage(Translate.color("&6/hubmusic displaySongs"));
            player.sendMessage(Translate.color("&6/hubmusic config"));
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
            this.plugin.getSongPlayer().stop();
            this.plugin.getSongPlayer().start();
            player.sendMessage("HubMusic has been reloaded.");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("stop")) {
            this.plugin.getSongPlayer().stop();
            player.sendMessage("HubMusic has stoped.");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("start")) {
            this.plugin.getSongPlayer().start();
            player.sendMessage("HubMusic has started.");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("displaySongs")) {
            this.plugin.getSetListMap().getSongMap().forEach((k, v) -> player.sendMessage("Key: " + k + " Value: " + v));
        } else if (args[0].equalsIgnoreCase("config")) {
            if (args.length == 1) {
                player.sendMessage("/hubmusic config isEnabled <Value>");
                player.sendMessage("/hubmusic config volume <Value>");
                player.sendMessage("/hubmusic config distance <Value>");
                player.sendMessage("/hubmusic config location");
                return true;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("isEnabled")) {
                String value = args[2];
                this.plugin.getConfig().set("isEnabled", value);
                this.plugin.saveConfig();
                player.sendMessage("isEnabled is now set to " + value);
                return true;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("volume")) {
                String value = args[2];
                this.plugin.getConfig().set("volume", value);
                this.plugin.saveConfig();
                player.sendMessage("volume is now set to " + value);
                return true;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("distance")) {
                String value = args[2];
                this.plugin.getConfig().set("distance", value);
                this.plugin.saveConfig();
                player.sendMessage("distance is now set to " + value);
                return true;
            } else if (args.length == 2 && args[1].equalsIgnoreCase("location")) {
                Block block = Utils.getBlockPlayerIsLookingAt(player);
                UnlinkedWorldLocation unlinkedWorldLocation = new UnlinkedWorldLocation(block.getLocation());
                this.plugin.getConfig().set("Location", unlinkedWorldLocation);
                this.plugin.saveConfig();
                player.sendMessage(Translate.miniMessage("<gold>Location has been set!"));
                return true;
            } else if (args.length == 3 && args[1].equalsIgnoreCase("joinServerTimeDelay")) {
                String value = args[2];
                this.plugin.getConfig().set("joinServerTimeDelay", Integer.parseInt(value));
                this.plugin.saveConfig();
                player.sendMessage("joinServerTimeDelay has been set to " + value);
            }
        }

        return false;
    }
}

package me.josh.court.commands;

import org.bukkit.command.*;
import org.bukkit.configuration.file.*;
import me.josh.court.files.*;
import org.bukkit.entity.*;
import me.josh.court.handlers.*;
import org.bukkit.*;

import java.io.*;

public class CourtCommand implements CommandExecutor
{
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        File locationsFile = Locations.getLocationsFile();
        YamlConfiguration locations = YamlConfiguration.loadConfiguration(locationsFile);
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHandler.translate("&cYou cannot execute this command from the console!"));
            return false;
        }
        Player player = (Player)sender;

        if (args.length == 0) {
            if (player.hasPermission("court.help")) {
                for (String string : settings.getStringList("settings.help")) {
                    player.sendMessage(ChatHandler.translate(string));
                }
                return true;
            }
            ChatHandler.sendMessage(player, "no_permission");
            return false;
        }
        else {
            if (args.length != 2) {
                return false;
            }
            if (!args[0].equalsIgnoreCase("setspawn")) {
                return false;
            }
            if (!player.hasPermission("court.setspawn")) {
                ChatHandler.sendMessage(player, "no_permission");
                return false;
            }
            if (args[1].equalsIgnoreCase("judge")) {
                locations.set("locations.judge_spawn.world", player.getWorld().getName());
                locations.set("locations.judge_spawn.x", player.getLocation().getBlockX());
                locations.set("locations.judge_spawn.y", player.getLocation().getBlockY());
                locations.set("locations.judge_spawn.z", player.getLocation().getBlockZ());
                locations.set("locations.judge_spawn.yaw", player.getLocation().getYaw());
                locations.set("locations.judge_spawn.pitch", player.getLocation().getPitch());
                try {
                    locations.save(locationsFile);
                }
                catch (IOException ex) {
                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&clocations.yml could not be saved!"));
                }
                player.sendMessage(ChatHandler.translate(settings.getString("settings.setspawn").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%type%", "Judge")));
                return true;
            }
            if (args[1].equalsIgnoreCase("jury")) {
                locations.set("locations.jury_spawn.world", player.getWorld().getName());
                locations.set("locations.jury_spawn.x", player.getLocation().getBlockX());
                locations.set("locations.jury_spawn.y", player.getLocation().getBlockY());
                locations.set("locations.jury_spawn.z", player.getLocation().getBlockZ());
                locations.set("locations.jury_spawn.yaw", player.getLocation().getYaw());
                locations.set("locations.jury_spawn.pitch", player.getLocation().getPitch());
                try {
                    locations.save(locationsFile);
                }
                catch (IOException ex) {
                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&clocations.yml could not be saved!"));
                }
                player.sendMessage(ChatHandler.translate(settings.getString("settings.setspawn").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%type%", "Jury")));
                return true;
            }
            if (args[1].equalsIgnoreCase("accuser")) {
                locations.set("locations.accuser_spawn.world", player.getWorld().getName());
                locations.set("locations.accuser_spawn.x", player.getLocation().getBlockX());
                locations.set("locations.accuser_spawn.y", player.getLocation().getBlockY());
                locations.set("locations.accuser_spawn.z", player.getLocation().getBlockZ());
                locations.set("locations.accuser_spawn.yaw", player.getLocation().getYaw());
                locations.set("locations.accuser_spawn.pitch", player.getLocation().getPitch());
                try {
                    locations.save(locationsFile);
                }
                catch (IOException ex) {
                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&clocations.yml could not be saved!"));
                }
                player.sendMessage(ChatHandler.translate(settings.getString("settings.setspawn").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%type%", "Accuser")));
                return true;
            }
            if (args[1].equalsIgnoreCase("defender")) {
                locations.set("locations.defender_spawn.world", player.getWorld().getName());
                locations.set("locations.defender_spawn.x", player.getLocation().getBlockX());
                locations.set("locations.defender_spawn.y", player.getLocation().getBlockY());
                locations.set("locations.defender_spawn.z", player.getLocation().getBlockZ());
                locations.set("locations.defender_spawn.yaw", player.getLocation().getYaw());
                locations.set("locations.defender_spawn.pitch", player.getLocation().getPitch());
                try {
                    locations.save(locationsFile);
                }
                catch (IOException ex) {
                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&clocations.yml could not be saved!"));
                }
                player.sendMessage(ChatHandler.translate(settings.getString("settings.setspawn").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%type%", "Defender")));
                return true;
            }
            if (args[1].equalsIgnoreCase("aftercourt")) {
                locations.set("locations.afterCourt_spawn.world", player.getWorld().getName());
                locations.set("locations.afterCourt_spawn.x", player.getLocation().getBlockX());
                locations.set("locations.afterCourt_spawn.y", player.getLocation().getBlockY());
                locations.set("locations.afterCourt_spawn.z", player.getLocation().getBlockZ());
                locations.set("locations.afterCourt_spawn.yaw", player.getLocation().getYaw());
                locations.set("locations.afterCourt_spawn.pitch", player.getLocation().getPitch());
                try {
                    locations.save(locationsFile);
                }
                catch (IOException ex) {
                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&clocations.yml could not be saved!"));
                }
                player.sendMessage(ChatHandler.translate(settings.getString("settings.setspawn").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%type%", "After court")));
                return true;
            }
            ChatHandler.sendMessage(player, "setspawn_usage");
            return false;
        }
    }
}

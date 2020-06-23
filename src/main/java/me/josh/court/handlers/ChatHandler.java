package me.josh.court.handlers;

import org.bukkit.*;
import org.bukkit.entity.*;
import me.josh.court.files.*;
import org.bukkit.configuration.file.*;

import java.io.File;

public class ChatHandler {

    public static String translate(final String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static void sendMessage(final Player player, final String settingsPath) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', settings.getString("settings." + settingsPath).replaceAll("%prefix%", settings.getString("settings.prefix"))));
        return;
    }

    public static void clearChat(Player player) {
        for (int x = 0; x < 100; x++) {
            player.sendMessage("");
        }
    }
}

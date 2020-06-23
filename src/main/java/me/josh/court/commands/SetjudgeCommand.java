package me.josh.court.commands;

import me.josh.court.Court;
import org.bukkit.command.*;
import me.josh.court.files.*;
import org.bukkit.configuration.file.*;
import org.bukkit.entity.*;
import me.josh.court.handlers.*;
import org.bukkit.*;
import java.io.*;

public class SetjudgeCommand implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatHandler.translate("&cYou cannot execute this command from the console!"));
            return false;
        }
        final Player player = (Player)sender;
        if (!player.hasPermission("court.setjudge")) {
            ChatHandler.sendMessage(player, "no_permission");
            return false;
        }
        if (args.length == 0) {
            ChatHandler.sendMessage(player, "setjudge_usage");
            return true;
        }
        if (args.length == 1) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "pex user " + args[0] + " group set judge");
            player.sendMessage(ChatHandler.translate(settings.getString("settings.setjudge").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%player%", args[0])));
            return true;
        }
        ChatHandler.sendMessage(player, "setjudge_usage");
        return false;
    }
}

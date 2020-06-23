package me.josh.court.commands;

import me.josh.court.enums.LocationType;
import me.josh.court.files.Settings;
import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.CourtLocation;
import me.josh.court.handlers.EquipAccuser;
import me.josh.court.handlers.EquipDefender;
import me.josh.court.objects.Court;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class BringCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("court.bring")) {
                if (args.length == 1) {
                    Court court = Court.getCourt();
                    if (court == null) {
                        return false;
                    }
                    if (args[0].equalsIgnoreCase("accuser")) {
                        Player accuser = Bukkit.getPlayerExact(court.getAccuserName());
                        if (accuser != null) {
                            new EquipAccuser(court);
                            new CourtLocation(accuser, LocationType.ACCUSER);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (me.josh.court.Court.getInstance().courtChat.contains(players)) {
                                    players.sendMessage(ChatHandler.translate(settings.getString("settings.bring.alert")
                                            .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                            .replaceAll("%player%", accuser.getName())));
                                }
                            }
                        }else {
                            player.sendMessage(ChatHandler.translate(settings.getString("settings.bring.player_not_online")
                                    .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                    .replaceAll("%player%", court.getAccuserName())));
                            return false;
                        }
                        return true;
                    }else if (args[0].equalsIgnoreCase("defender")) {
                        Player defender = Bukkit.getPlayerExact(court.getDefenderName());
                        if (defender != null) {
                            new EquipDefender(court);
                            new CourtLocation(defender, LocationType.DEFENDER);
                            for (Player players : Bukkit.getOnlinePlayers()) {
                                if (me.josh.court.Court.getInstance().courtChat.contains(players)) {
                                    players.sendMessage(ChatHandler.translate(settings.getString("settings.bring.alert")
                                            .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                            .replaceAll("%player%", defender.getName())));
                                }
                            }
                        }else {
                            player.sendMessage(ChatHandler.translate(settings.getString("settings.bring.player_not_online")
                                    .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                    .replaceAll("%player%", court.getDefenderName())));
                            return false;
                        }
                        return true;
                    }else {
                        ChatHandler.sendMessage(player, "bring.usage");
                        return false;
                    }
                }else {
                    ChatHandler.sendMessage(player, "bring.usage");
                    return false;
                }
            }else {
                ChatHandler.sendMessage(player, "no_permission");
                return false;
            }
        }else {
            sender.sendMessage(ChatHandler.translate("&cYou cannot execute this command from the console!"));
            return false;
        }
    }
}

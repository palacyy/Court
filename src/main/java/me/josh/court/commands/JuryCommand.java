package me.josh.court.commands;

import me.josh.court.enums.LocationType;
import me.josh.court.files.Settings;
import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.CourtLocation;
import me.josh.court.handlers.JuryHandler;
import me.josh.court.objects.Court;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class JuryCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("court.jury")) {
                if (args.length == 1) {
                    Court court = Court.getCourt();
                    if (args[0].equalsIgnoreCase("accept")) {
                        if (court != null) {
                            if (me.josh.court.Court.getInstance().juryMembers.contains(player)) {
                                ChatHandler.sendMessage(player, "jury.already_in_court");
                                return false;
                            }
                            int juryMembers = me.josh.court.Court.getInstance().juryMembers.size();
                            int maxAmountOfJurys = settings.getInt("settings.jury.max_amount_of_jurys");

                            if (juryMembers == maxAmountOfJurys) {
                                ChatHandler.sendMessage(player, "jury.court_full");
                                return false;
                            }else {
                                if (player.getName().equalsIgnoreCase(court.getJudge().getName()) ||
                                (player.getName().equalsIgnoreCase(court.getAccuserName()) ||
                                        (player.getName().equalsIgnoreCase(court.getDefenderName())))) {
                                    ChatHandler.sendMessage(player, "jury.not_allowed");
                                    return false;
                                }
                                ChatHandler.clearChat(player);
                                me.josh.court.Court.getInstance().juryMembers.add(player);
                                me.josh.court.Court.getInstance().courtChat.add(player);
                                new CourtLocation(player, LocationType.JURY);
                                ChatHandler.sendMessage(player, "jury.joined_court");
                                JuryHandler.announce(player, "JOIN");
                                return true;
                            }
                        }else {
                            ChatHandler.sendMessage(player, "jury.court_not_active");
                            return false;
                        }
                    }else if (args[0].equalsIgnoreCase("leave")) {
                        if (court != null) {
                            if (me.josh.court.Court.getInstance().juryMembers.contains(player)) {
                                ChatHandler.clearChat(player);
                                me.josh.court.Court.getInstance().juryMembers.remove(player);
                                me.josh.court.Court.getInstance().courtChat.remove(player);
                                new CourtLocation(player, LocationType.AFTER_COURT);
                                ChatHandler.sendMessage(player, "jury.left_court");
                                JuryHandler.announce(player, "LEAVE");
                                return true;
                            }else {
                                ChatHandler.sendMessage(player, "jury.not_in_court");
                                return false;
                            }
                        }else {
                            ChatHandler.sendMessage(player, "jury.not_in_court");
                            return false;
                        }
                    }else {
                        ChatHandler.sendMessage(player, "jury.usage");
                        return false;
                    }
                }else {
                    ChatHandler.sendMessage(player, "jury.usage");
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

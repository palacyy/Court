package me.josh.court.commands;

import me.josh.court.Court;
import me.josh.court.files.Settings;
import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.CourtCaseGUI;
import me.josh.court.storage.Database;
import net.minecraft.server.v1_12_R1.DataConverterBanner;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CourtCaseCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("court.courtcase")) {
                if (args.length == 2) {
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target != null) {
                        if (target == player) {
                            ChatHandler.sendMessage(player, "courtcase_yourself");
                            return false;
                        }
                        if (args[0].equalsIgnoreCase("create")) {
                            int maxAmountOfCases = settings.getInt("settings.courtcase_max_cases");

                            new Database().getCasesAmount(player.getUniqueId().toString()).thenAccept(casesAmount -> {
                                if (casesAmount == maxAmountOfCases) {
                                    player.sendMessage(ChatHandler.translate(settings.getString("settings.courtcase_max_cases_reached")
                                            .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                            .replaceAll("%amount%", maxAmountOfCases + "")));
                                    return;
                                }else {
                                    new Database().hasReportedSamePlayer(player.getUniqueId().toString(), target.getUniqueId().toString()).thenAccept(hasReported -> {
                                        if (hasReported) {
                                            player.sendMessage(ChatHandler.translate(settings.getString("settings.courtcase_same_player")
                                                    .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                                    .replaceAll("%player%", target.getName())));
                                            return;
                                        }else {
                                            if (!(Court.getInstance().eco.getBalance(player) >= settings.getInt("settings.courtcase_cost_per_case"))) {
                                                ChatHandler.sendMessage(player, "courtcase_not_enough_money");
                                                return;
                                            }
                                            Court.getInstance().caseAccused.put(player, target.getUniqueId().toString());
                                            new CourtCaseGUI(player);
                                            return;
                                        }
                                    });
                                    return;
                                }
                            });
                            return true;
                        }else if (args[0].equalsIgnoreCase("delete")) {
                            new Database().deleteCase(player.getUniqueId().toString(), target.getUniqueId().toString()).thenAccept(deleteCase -> {
                                if (deleteCase) {
                                    player.sendMessage(ChatHandler.translate(settings.getString("settings.courtcase_case_deleted")
                                            .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                            .replaceAll("%player%", target.getName())));
                                    return;
                                }else {
                                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cCould not delete " + target.getName() + "’s case!"));
                                    return;
                                }
                            });
                            return true;
                        }else {
                            ChatHandler.sendMessage(player, "courtcase_usage");
                            return false;
                        }
                    }else {
                        player.sendMessage(ChatHandler.translate(settings.getString("settings.target_not_found").replaceAll("%prefix%", settings.getString("settings.prefix")).replaceAll("%target%", args[1])));
                        return false;
                    }
                }else {
                    ChatHandler.sendMessage(player, "courtcase_usage");
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
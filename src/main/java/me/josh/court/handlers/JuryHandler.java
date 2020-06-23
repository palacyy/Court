package me.josh.court.handlers;

import me.josh.court.Court;
import me.josh.court.files.JuryNotifications;
import me.josh.court.files.Settings;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class JuryHandler {

    public static boolean hasNotificationsEnabled(Player player) {
        File notificationsFile = JuryNotifications.getNotificationsFile();
        YamlConfiguration notifications = YamlConfiguration.loadConfiguration(notificationsFile);

        if (notifications.get("juryNotifications." + player.getUniqueId().toString()) != null) {
            if (notifications.getBoolean("juryNotifications." + player.getUniqueId().toString())) {
                return true;
            }else {
                return false;
            }
        }else {
            return true;
        }
    }

    public static void enableNotifications(Player player) {
        File notificationsFile = JuryNotifications.getNotificationsFile();
        YamlConfiguration notifications = YamlConfiguration.loadConfiguration(notificationsFile);

        notifications.set("juryNotifications." + player.getUniqueId().toString(), true);

        try {
            notifications.save(notificationsFile);
        }catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    public static void disableNotifications(Player player) {
        File notificationsFile = JuryNotifications.getNotificationsFile();
        YamlConfiguration notifications = YamlConfiguration.loadConfiguration(notificationsFile);

        notifications.set("juryNotifications." + player.getUniqueId().toString(), false);

        try {
            notifications.save(notificationsFile);
        } catch (IOException ex) {
            ex.printStackTrace();
            return;
        }
    }

    public static void notifyJurys(me.josh.court.objects.Court court) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getName() != court.getJudge().getName()) {
                if (hasNotificationsEnabled(player)) {
                    for (String string : settings.getStringList("settings.jury.jury_notification")) {
                        player.sendMessage(ChatHandler.translate(string.replaceAll("%prefix%", settings.getString("settings.prefix"))));
                    }
                }
            }
        }
    }

    public static void announce(Player juryMember, String announceType) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        int maxAmountOfJurys = settings.getInt("settings.jury.max_amount_of_jurys");

        switch (announceType) {

            case "JOIN":
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (Court.getInstance().courtChat.contains(player)) {
                        player.sendMessage(ChatHandler.translate(settings.getString("settings.jury.join_alert")
                                .replaceAll("%player%", juryMember.getName())
                                .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                .replaceAll("%amountOfJurys%", Court.getInstance().juryMembers.size() + ""))
                                .replaceAll("%maxAmountOfJurys%", maxAmountOfJurys + ""));
                    }
                }
                break;

            case "LEAVE":
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (Court.getInstance().courtChat.contains(player)) {
                        player.sendMessage(ChatHandler.translate(settings.getString("settings.jury.leave_alert")
                                .replaceAll("%player%", juryMember.getName())
                                .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                .replaceAll("%amountOfJurys%", Court.getInstance().juryMembers.size() + ""))
                                .replaceAll("%maxAmountOfJurys%", maxAmountOfJurys + ""));
                    }
                }
                break;

            default:
                break;
        }
    }

    public static void removeJurys() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Court.getInstance().juryMembers.contains(player)) {
                Court.getInstance().juryMembers.remove(player);
            }
        }
    }

    public static void removeCourtChatPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Court.getInstance().courtChat.contains(player)) {
                Court.getInstance().courtChat.remove(player);
            }
        }
    }
}

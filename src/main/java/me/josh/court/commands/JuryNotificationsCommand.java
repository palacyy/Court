package me.josh.court.commands;

import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.JuryHandler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class JuryNotificationsCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("court.jurynotifications")) {
                if (args.length == 0) {
                    if (JuryHandler.hasNotificationsEnabled(player)) {
                        JuryHandler.disableNotifications(player);
                        ChatHandler.sendMessage(player, "jury.notifications_disabled");
                        return true;
                    }else {
                        JuryHandler.enableNotifications(player);
                        ChatHandler.sendMessage(player, "jury.notifications_enabled");
                        return true;
                    }
                }else {
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

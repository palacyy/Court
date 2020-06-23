package me.josh.court.files;

import me.josh.court.Court;
import me.josh.court.handlers.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class JuryNotifications {

    public JuryNotifications() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Court").getDataFolder(), File.separator + "juryNotifications.yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                data.save(file);
            }
            catch (IOException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&juryNotifications.yml could not be generated!"));
            }
        }
    }

    public static File getNotificationsFile() {
        final File file = new File(Court.getInstance().getDataFolder(), "juryNotifications.yml");
        return file;
    }
}

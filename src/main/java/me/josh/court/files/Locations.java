package me.josh.court.files;

import org.bukkit.*;
import org.bukkit.configuration.file.*;
import me.josh.court.handlers.*;
import java.io.*;
import me.josh.court.*;

public class Locations {

    public Locations() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Court").getDataFolder(), File.separator + "locations.yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                data.save(file);
            }
            catch (IOException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&clocations.yml could not be generated!"));
            }
        }
    }

    public static File getLocationsFile() {
        final File file = new File(Court.getInstance().getDataFolder(), "locations.yml");
        return file;
    }
}

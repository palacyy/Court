package me.josh.court.files;

import me.josh.court.Court;
import me.josh.court.handlers.ChatHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Inventories {

    public Inventories() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Court").getDataFolder(), File.separator + "inventories.yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                data.save(file);
            }
            catch (IOException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cinventories.yml could not be generated!"));
            }
        }
    }

    public static void saveInventory(Player player) throws IOException {
        File inventoriesFile = new File(Court.getInstance().getDataFolder(), "inventories.yml");
        YamlConfiguration inventories = YamlConfiguration.loadConfiguration(inventoriesFile);

        inventories.set("inventories." + player.getUniqueId().toString() + ".contents", player.getInventory().getContents());
        inventories.set("inventories." + player.getUniqueId().toString() + ".armor", player.getInventory().getArmorContents());

        inventories.save(inventoriesFile);

        player.getInventory().clear();
    }

    public static void restoreInventory(Player player) throws IOException {
        File inventoriesFile = new File(Court.getInstance().getDataFolder(), "inventories.yml");
        YamlConfiguration inventories = YamlConfiguration.loadConfiguration(inventoriesFile);

        ItemStack[] contents = ((List<ItemStack>) inventories.get("inventories." + player.getUniqueId().toString() + ".contents")).toArray(new ItemStack[0]);
        ItemStack[] armor = ((List<ItemStack>) inventories.get("inventories." + player.getUniqueId().toString() + ".armor")).toArray(new ItemStack[0]);

        player.getInventory().setContents(contents);
        player.getInventory().setArmorContents(armor);

        inventories.set("inventories." + player.getUniqueId().toString(), null);

        inventories.save(inventoriesFile);
    }
}

package me.josh.court.handlers;

import me.josh.court.files.Settings;
import me.josh.court.objects.Court;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.io.File;
import java.util.ArrayList;

public class FaithGUI {

    public FaithGUI(Player judge) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        Court court = Court.getCourt();

        Inventory inventory = Bukkit.createInventory(null, 9 * 3, ChatHandler.translate(settings.getString("settings.court.faith_gui.title").replaceAll("%player%", court.getDefenderName())));

        ItemStack guilty = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta guiltyMeta = guilty.getItemMeta();
        guiltyMeta.setDisplayName(ChatHandler.translate(settings.getString("settings.court.faith_gui.items.guilty.name")));
        ArrayList<String> guiltyLore = new ArrayList<>();
        for (String string : settings.getStringList("settings.court.faith_gui.items.guilty.lore")) {
            guiltyLore.add(ChatHandler.translate(string).replaceAll("%player%", court.getDefenderName()));
        }
        guiltyMeta.setLore(guiltyLore);
        guilty.setItemMeta(guiltyMeta);

        ItemStack innocent = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
        ItemMeta innocentMeta = innocent.getItemMeta();
        innocentMeta.setDisplayName(ChatHandler.translate(settings.getString("settings.court.faith_gui.items.innocent.name")));
        ArrayList<String> innocentLore = new ArrayList<>();
        for (String string : settings.getStringList("settings.court.faith_gui.items.innocent.lore")) {
            innocentLore.add(ChatHandler.translate(string).replaceAll("%player%", court.getDefenderName()));
        }
        innocentMeta.setLore(innocentLore);
        innocent.setItemMeta(innocentMeta);

        ItemStack playerHead = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(court.getDefenderName()));
        skullMeta.setDisplayName(ChatHandler.translate("&e" + skullMeta.getOwningPlayer().getName() + "’s Head"));
        playerHead.setItemMeta(skullMeta);

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);
        ItemStack blueGlass = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);

        inventory.setItem(0, blueGlass);
        inventory.setItem(1, blueGlass);
        inventory.setItem(2, blueGlass);
        inventory.setItem(3, blueGlass);
        inventory.setItem(4, playerHead);
        inventory.setItem(5, blueGlass);
        inventory.setItem(6, blueGlass);
        inventory.setItem(7, blueGlass);
        inventory.setItem(8, blueGlass);
        inventory.setItem(18, blueGlass);
        inventory.setItem(19, blueGlass);
        inventory.setItem(20, blueGlass);
        inventory.setItem(21, blueGlass);
        inventory.setItem(22, blueGlass);
        inventory.setItem(23, blueGlass);
        inventory.setItem(24, blueGlass);
        inventory.setItem(25, blueGlass);
        inventory.setItem(26, blueGlass);
        inventory.setItem(12, innocent);
        inventory.setItem(14, guilty);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                inventory.setItem(i, filler);
            }
        }
        judge.openInventory(inventory);
    }
}

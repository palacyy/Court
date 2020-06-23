package me.josh.court.handlers;

import me.josh.court.Court;
import me.josh.court.files.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class CourtCaseGUI {

    public CourtCaseGUI(Player accuser) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, ChatHandler.translate(settings.getString("settings.courtcase_case_gui.title")));

        ItemStack griefCase = new ItemBuilder(Material.getMaterial(settings.getString("settings.courtcase_case_gui.items.grief_item.material"))).setDisplayName(
                settings.getString("settings.courtcase_case_gui.items.grief_item.name")).setLore(settings.getStringList("settings.courtcase_case_gui.items.grief_item.lore")).build();
        ItemStack townyCase = new ItemBuilder(Material.getMaterial(settings.getString("settings.courtcase_case_gui.items.towny_item.material"))).setDisplayName(
                settings.getString("settings.courtcase_case_gui.items.towny_item.name")).setLore(settings.getStringList("settings.courtcase_case_gui.items.towny_item.lore")).build();
        ItemStack divorceCase = new ItemBuilder(Material.getMaterial(settings.getString("settings.courtcase_case_gui.items.divorce_item.material"))).setDisplayName(
                settings.getString("settings.courtcase_case_gui.items.divorce_item.name")).setLore(settings.getStringList("settings.courtcase_case_gui.items.divorce_item.lore")).build();
        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);

        inventory.setItem(11, griefCase);
        inventory.setItem(13, townyCase);
        inventory.setItem(15, divorceCase);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                inventory.setItem(i, filler);
            }
        }
        accuser.openInventory(inventory);
    }

    public static void openConfirmationGUI(Player accuser) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        Inventory inventory = Bukkit.createInventory(null, 9 * 3, ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.title")));
        UUID uuid = UUID.fromString(Court.getInstance().caseAccused.get(accuser));
        Player defender = Bukkit.getPlayer(uuid);

        ItemStack confirm = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.setDisplayName(ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.confirm_item.name")));
        ArrayList<String> confirmLore = new ArrayList<>();
        for (String string : settings.getStringList("settings.courtcase_case_confirmation_gui.confirm_item.lore")) {
            confirmLore.add(ChatHandler.translate(string).replaceAll("%target%", defender.getName()));
        }
        confirmMeta.setLore(confirmLore);
        confirm.setItemMeta(confirmMeta);

        ItemStack cancel = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
        ItemMeta cancelMeta = cancel.getItemMeta();
        cancelMeta.setDisplayName(ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.cancel_item.name")));
        ArrayList<String> cancelLore = new ArrayList<>();
        for (String string : settings.getStringList("settings.courtcase_case_confirmation_gui.cancel_item.lore")) {
            cancelLore.add(ChatHandler.translate(string));
        }
        cancelMeta.setLore(cancelLore);
        cancel.setItemMeta(cancelMeta);

        ItemStack filler = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7);

        inventory.setItem(12, confirm);
        inventory.setItem(14, cancel);

        for (int i = 0; i < inventory.getSize(); i++) {
            if (inventory.getItem(i) == null || inventory.getItem(i).getType().equals(Material.AIR)) {
                inventory.setItem(i, filler);
            }
        }
        accuser.openInventory(inventory);
    }
}

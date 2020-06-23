package me.josh.court.handlers;

import me.josh.court.enums.LoreValue;
import me.josh.court.files.Settings;
import me.josh.court.objects.Case;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class CaseHandler {

    public static ItemStack convertCase(Case courtCase) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        String caseType = courtCase.getCaseType();
        OfflinePlayer accuser = Bukkit.getOfflinePlayer(UUID.fromString(courtCase.getAccuserUUID()));
        OfflinePlayer defender = Bukkit.getOfflinePlayer(UUID.fromString(courtCase.getDefenderUUID()));

        ItemStack itemStack = new ItemStack(Material.getMaterial(settings.getString("settings.cases_gui.items.case_item.material")));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ChatHandler.translate(settings.getString("settings.cases_gui.items.case_item.name")
                .replaceAll("%case_id%", courtCase.getCaseID() + "")));
        ArrayList<String> lore = new ArrayList<>();
        for (String string : settings.getStringList("settings.cases_gui.items.case_item.lore")) {
            lore.add(ChatHandler.translate(string)
                    .replaceAll("%accuser%", accuser.getName())
                    .replaceAll("%defender%", defender.getName())
                    .replaceAll("%case_type%", caseType));
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    public static String getLoreValue(ItemStack itemStack, LoreValue loreValue) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> lore = itemMeta.getLore();
        String line;
        String finalString = null;

        switch (loreValue) {

            case ACCUSER:
                line = ChatColor.stripColor(lore.get(0));
                String[] accuserValue = line.split(": ");
                finalString = accuserValue[1];
                break;

            case DEFENDER:
                line = ChatColor.stripColor(lore.get(1));
                String[] defenderValue = line.split(": ");
                finalString = defenderValue[1];
                break;

            case CASE_TYPE:
                line = ChatColor.stripColor(lore.get(2));
                String[] caseTypeValue = line.split(": ");
                finalString = caseTypeValue[1];
                break;

            default:
                break;
        }
        return finalString;
    }
}

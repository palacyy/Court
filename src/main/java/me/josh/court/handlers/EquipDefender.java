package me.josh.court.handlers;

import me.josh.court.files.Inventories;
import me.josh.court.files.Settings;
import me.josh.court.objects.Court;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.File;
import java.io.IOException;

public class EquipDefender {

    public EquipDefender(Court courtCase) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        Player defender = Bukkit.getPlayerExact(courtCase.getDefenderName());

        try {
            Inventories.saveInventory(defender);
        }catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cCould not save " + courtCase.getDefenderName() + "’s inventory!"));
            return;
        }

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.RED);
        helmet.setItemMeta(helmetMeta);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.RED);
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.RED);
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.RED);
        boots.setItemMeta(bootsMeta);

        defender.getInventory().setHelmet(helmet);
        defender.getInventory().setChestplate(chestplate);
        defender.getInventory().setLeggings(leggings);
        defender.getInventory().setBoots(boots);
        defender.setHealth(20);
        defender.setFoodLevel(20);

        me.josh.court.Court.getInstance().courtChat.add(defender);
        ChatHandler.clearChat(defender);

        defender.sendMessage(ChatHandler.translate(settings.getString("settings.bring.player_alert")
                .replaceAll("%prefix%", settings.getString("settings.prefix"))
                .replaceAll("%player%", courtCase.getJudge().getName())));
    }
}

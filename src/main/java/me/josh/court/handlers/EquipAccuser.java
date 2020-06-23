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

public class EquipAccuser {

    public EquipAccuser(Court courtCase) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        Player accuser = Bukkit.getPlayerExact(courtCase.getAccuserName());

        try {
            Inventories.saveInventory(accuser);
        }catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cCould not save " + courtCase.getAccuserName() + "’s inventory!"));
            return;
        }

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.BLUE);
        helmet.setItemMeta(helmetMeta);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.BLUE);
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.BLUE);
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.BLUE);
        boots.setItemMeta(bootsMeta);

        accuser.getInventory().setHelmet(helmet);
        accuser.getInventory().setChestplate(chestplate);
        accuser.getInventory().setLeggings(leggings);
        accuser.getInventory().setBoots(boots);
        accuser.setHealth(20);
        accuser.setFoodLevel(20);

        me.josh.court.Court.getInstance().courtChat.add(accuser);
        ChatHandler.clearChat(accuser);

        accuser.sendMessage(ChatHandler.translate(settings.getString("settings.bring.player_alert")
                .replaceAll("%prefix%", settings.getString("settings.prefix"))
                .replaceAll("%player%", courtCase.getJudge().getName())));
    }
}

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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class EquipJudge {

    public EquipJudge(Court courtCase) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        try {
            Inventories.saveInventory(courtCase.getJudge());
        }catch (IOException ex) {
            Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cCould not save " + courtCase.getJudge().getName() + "’s inventory!"));
            return;
        }

        Player judge = courtCase.getJudge();
        String caseType = courtCase.getCaseType();
        String accuserName = courtCase.getAccuserName();
        String defenderName = courtCase.getDefenderName();

        ItemStack decision = new ItemBuilder(Material.getMaterial(settings.getString("settings.court.judgeItems.faith_item.material"))).setDisplayName(settings.getString("settings.court.judgeItems.faith_item.name")).build();
        ItemMeta decisionMeta = decision.getItemMeta();
        ArrayList<String> decisionLore = new ArrayList<>();
        for (String string : settings.getStringList("settings.court.judgeItems.faith_item.lore")) {
            decisionLore.add(ChatHandler.translate(string).replaceAll("%player%", courtCase.getDefenderName()));
        }
        decisionMeta.setLore(decisionLore);
        decision.setItemMeta(decisionMeta);

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmetMeta = (LeatherArmorMeta) helmet.getItemMeta();
        helmetMeta.setColor(Color.BLACK);
        helmet.setItemMeta(helmetMeta);

        ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chestplateMeta = (LeatherArmorMeta) chestplate.getItemMeta();
        chestplateMeta.setColor(Color.BLACK);
        chestplate.setItemMeta(chestplateMeta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggingsMeta = (LeatherArmorMeta) leggings.getItemMeta();
        leggingsMeta.setColor(Color.BLACK);
        leggings.setItemMeta(leggingsMeta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta bootsMeta = (LeatherArmorMeta) boots.getItemMeta();
        bootsMeta.setColor(Color.BLACK);
        boots.setItemMeta(bootsMeta);

        judge.getInventory().setHelmet(helmet);
        judge.getInventory().setChestplate(chestplate);
        judge.getInventory().setLeggings(leggings);
        judge.getInventory().setBoots(boots);
        judge.getInventory().setItem(4, decision);
        judge.setHealth(20);
        judge.setFoodLevel(20);

        me.josh.court.Court.getInstance().courtChat.add(judge);
        ChatHandler.clearChat(judge);

        for (String string : settings.getStringList("settings.court.judge_case_alert")) {
            judge.sendMessage(ChatHandler.translate(string
                    .replaceAll("%prefix%", settings.getString("settings.prefix"))
                    .replaceAll("%player%", judge.getName())
                    .replaceAll("%case_type%", caseType))
                    .replaceAll("%accuser%", accuserName)
                    .replaceAll("%defender%", defenderName));
        }
    }
}

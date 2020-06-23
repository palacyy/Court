package me.josh.court.events;

import me.josh.court.Court;
import me.josh.court.files.Settings;
import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.CourtCaseGUI;
import me.josh.court.objects.Case;
import me.josh.court.storage.Database;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.UUID;

public class CourtCaseEvents implements Listener {

    @EventHandler
    public void onCaseTypeSelect(InventoryClickEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (event.getInventory() == null || event.getInventory().getName() == null) {
            return;
        }
        Inventory inventory = event.getInventory();
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (inventory.getName().equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_gui.title")))) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                    ItemStack item = event.getCurrentItem();
                    String itemName = item.getItemMeta().getDisplayName();
                    if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_gui.items.grief_item.name")))) {
                        Court.getInstance().caseType.put(player, "GRIEF");
                        CourtCaseGUI.openConfirmationGUI(player);
                        return;
                    }else if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_gui.items.towny_item.name")))) {
                        Court.getInstance().caseType.put(player, "TOWNY");
                        CourtCaseGUI.openConfirmationGUI(player);
                        return;
                    }else if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_gui.items.divorce_item.name")))) {
                        Court.getInstance().caseType.put(player, "DIVORCE");
                        CourtCaseGUI.openConfirmationGUI(player);
                        return;
                    }else {
                        return;
                    }
                }else {
                    return;
                }
            }else {
                return;
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onCaseConfirmation(InventoryClickEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (event.getInventory() == null || event.getInventory().getName() == null) {
            return;
        }
        Inventory inventory = event.getInventory();
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (inventory.getName().equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.title")))) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                        ItemStack item = event.getCurrentItem();
                    String itemName = item.getItemMeta().getDisplayName();
                    UUID uuid = UUID.fromString(Court.getInstance().caseAccused.get(player));
                    Player defender = Bukkit.getPlayer(uuid);
                    String caseType = Court.getInstance().caseType.get(player);
                    event.setCancelled(true);

                    if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.confirm_item.name")))) {
                        player.closeInventory();
                        ChatHandler.sendMessage(player, "courtcase_case_confirmed");
                        Case courtCase = new Case(-1, player.getUniqueId().toString(), uuid.toString(), caseType);
                        new Database().insertCase(courtCase);
                        Court.getInstance().caseType.remove(player);
                        Court.getInstance().caseAccused.remove(player);
                        Court.getInstance().eco.withdrawPlayer(player, settings.getInt("settings.courtcase_cost_per_case"));
                        return;

                    }else if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.cancel_item.name")))) {
                        player.closeInventory();
                        Court.getInstance().caseType.remove(player);
                        Court.getInstance().caseAccused.remove(player);
                        ChatHandler.sendMessage(player, "courtcase_case_cancelled");
                        return;
                    }else {
                        return;
                    }
                }else {
                    return;
                }
            }else {
                return;
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (event.getInventory() != null && event.getInventory().getName() != null) {
            Player player = (Player) event.getPlayer();
            Inventory inventory = event.getInventory();
            String inventoryName = inventory.getName();
            if (inventoryName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_gui.title")))) {
                if (!Court.getInstance().caseType.containsKey(player)) {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Court.getInstance(), new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.openInventory(event.getInventory());
                        }
                    }, 1L);
                }else {
                    return;
                }
            }else if (inventoryName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.courtcase_case_confirmation_gui.title")))) {
                Court.getInstance().caseAccused.remove(player);
                Court.getInstance().caseType.remove(player);
                return;
            }else {
                return;
            }
        }else {
            return;
        }
    }
}

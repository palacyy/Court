package me.josh.court.events;

import me.josh.court.Court;
import me.josh.court.files.Settings;
import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.CourtCaseGUI;
import me.josh.court.handlers.FaithGUI;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class CourtEvents implements Listener {

    @EventHandler
    public void onGlobalChat(AsyncPlayerChatEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (Court.getInstance().courtChat.contains(player)) {
                event.getRecipients().remove(player);
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (Court.getInstance().courtChat.contains(player)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        if (Court.getInstance().courtChat.contains(player)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (Court.getInstance().courtChat.contains(player)) {
                event.setCancelled(true);
                return;
            }else if (Court.getInstance().courtChat.contains(damager)) {
                event.setCancelled(true);
                return;
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        if (Court.getInstance().courtChat.contains(player)) {
            event.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onItemPickup(InventoryPickupItemEvent event) {
        if (event.getInventory().getHolder() instanceof Player) {
            Player player = (Player) event.getInventory().getHolder();
            if (Court.getInstance().courtChat.contains(player)) {
                event.setCancelled(true);
                return;
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (Court.getInstance().courtChat.contains(player)) {
                event.setCancelled(true);
                return;
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onCourtChat(AsyncPlayerChatEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        Player player = event.getPlayer();
        if (Court.getInstance().courtChat.contains(player)) {
            me.josh.court.objects.Court court = me.josh.court.objects.Court.getCourt();
            String message = event.getMessage();
            event.setCancelled(true);
            for (Player players : Bukkit.getOnlinePlayers()) {
                if (Court.getInstance().courtChat.contains(players)) {
                    if (!Court.getInstance().declaringPunishment.contains(player)) {
                        if (player.getName().equalsIgnoreCase(court.getJudge().getName())) {
                            players.sendMessage(ChatHandler.translate(settings.getString("settings.court.courtChat_format.judge")
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%message%", message)));

                        } else if (player.getName().equalsIgnoreCase(court.getAccuserName())) {
                            players.sendMessage(ChatHandler.translate(settings.getString("settings.court.courtChat_format.accuser")
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%message%", message)));

                        } else if (player.getName().equalsIgnoreCase(court.getDefenderName())) {
                            players.sendMessage(ChatHandler.translate(settings.getString("settings.court.courtChat_format.defender")
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%message%", message)));

                        } else if (Court.getInstance().juryMembers.contains(players)){
                            players.sendMessage(ChatHandler.translate(settings.getString("settings.court.courtChat_format.jury")
                                    .replaceAll("%player%", player.getName())
                                    .replaceAll("%message%", message)));
                        }
                    }
                }
            }
        }else {
            return;
        }
    }

    @EventHandler
    public void onFaithGUI(PlayerInteractEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        Player player = event.getPlayer();
        if (event.getItem() != null && event.getItem().hasItemMeta()) {
            ItemStack itemStack = event.getItem();
            ItemMeta itemMeta = itemStack.getItemMeta();
            if (Court.getInstance().courtChat.contains(player)) {
                if (itemMeta.getDisplayName().equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.court.judgeItems.faith_item.name")))) {
                    new FaithGUI(player);
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
    }

    @EventHandler
    public void onFaithGUISelect(InventoryClickEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        if (event.getInventory() == null || event.getInventory().getName() == null) {
            return;
        }

        Inventory inventory = event.getInventory();
        me.josh.court.objects.Court court = me.josh.court.objects.Court.getCourt();
        if (event.getWhoClicked() instanceof Player) {
            Player player = (Player) event.getWhoClicked();
            if (inventory.getName().equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.court.faith_gui.title")))) {
                event.setCancelled(true);
                if (event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                    ItemStack item = event.getCurrentItem();
                    String itemName = item.getItemMeta().getDisplayName();
                    if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.court.faith_gui.items.innocent.name")))) {
                        Bukkit.broadcastMessage(ChatHandler.translate(settings.getString("settings.court.faith_broadcasts.innocent")
                                .replaceAll("%player%", court.getDefenderName())));
                        court.endCourt();
                        return;
                    }else if (itemName.equalsIgnoreCase(ChatHandler.translate(settings.getString("settings.court.faith_gui.items.guilty.name")))) {
                        Court.getInstance().declaringPunishment.add(player);
                        player.closeInventory();
                        player.sendMessage(ChatHandler.translate(settings.getString("settings.court.faith_lenght.usage")
                                .replaceAll("%prefix%", settings.getString("settings.prefix"))
                                .replaceAll("%player%", court.getDefenderName())));
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
    public void onFaithLenght(AsyncPlayerChatEvent event) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);

        me.josh.court.objects.Court court = me.josh.court.objects.Court.getCourt();

        Player player = event.getPlayer();
            if (Court.getInstance().declaringPunishment.contains(player)) {
                event.setCancelled(true);
                String message = event.getMessage();
                try {
                    int numberOfDays = Integer.parseInt(message);

                    if (numberOfDays <= 0) {
                        ChatHandler.sendMessage(player, "court.faith_lenght.higher_than_zero");
                        return;
                    }

                    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tempban " + court.getDefenderName() + " " + numberOfDays + "d You have been found guilty!");
                    player.sendMessage(ChatHandler.translate(settings.getString("settings.court.faith_lenght.success")
                            .replaceAll("%prefix%", settings.getString("settings.prefix")))
                            .replaceAll("%player%", court.getDefenderName())
                            .replaceAll("%amount%", numberOfDays + ""));
                    Bukkit.broadcastMessage(ChatHandler.translate(settings.getString("settings.court.faith_broadcasts.guilty")
                            .replaceAll("%player%", court.getDefenderName())));
                    Court.getInstance().declaringPunishment.remove(player);
                    court.endCourt();
                }catch (NumberFormatException ex) {
                    player.sendMessage(ChatHandler.translate(settings.getString("settings.court.faith_lenght.invalid")
                            .replaceAll("%prefix%", settings.getString("settings.prefix"))));
                    return;
                }

            }
        }
    }

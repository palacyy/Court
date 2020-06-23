package me.josh.court.commands;

import me.josh.court.enums.LoreValue;
import me.josh.court.files.Settings;
import me.josh.court.handlers.CaseHandler;
import me.josh.court.handlers.ChatHandler;
import me.josh.court.handlers.ItemBuilder;
import me.josh.court.objects.Court;
import me.josh.court.storage.Database;
import me.justrayz.rlib.inventory.RInventoryBuilder;
import me.justrayz.rlib.inventory.RMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.stream.Collectors;

public class CasesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        File settingsFile = Settings.getSettingsFile();
        YamlConfiguration settings = YamlConfiguration.loadConfiguration(settingsFile);
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (player.hasPermission("court.cases")) {
                if (args.length == 0) {
                    ItemStack nextPage = new ItemBuilder(Material.getMaterial(settings.getString("settings.cases_gui.items.next_page.material")))
                            .setDisplayName(settings.getString("settings.cases_gui.items.next_page.name"))
                            .setLore(settings.getStringList("settings.cases_gui.items.next_page.lore"))
                            .build();
                    ItemStack previousPage = new ItemBuilder(Material.getMaterial(settings.getString("settings.cases_gui.items.previous_page.material")))
                            .setDisplayName(settings.getString("settings.cases_gui.items.previous_page.name"))
                            .setLore(settings.getStringList("settings.cases_gui.items.previous_page.lore"))
                            .build();
                    new Database().getCases().thenAccept((cases) -> {
                        RMenu gui = new RMenu(settings.getString("settings.cases_gui.title"), 1, null);
                        gui.setForward(nextPage);
                        gui.setBackward(previousPage);
                        gui.setPageType(Material.getMaterial(settings.getString("settings.cases_gui.items.page_number.material")));
                        gui.setItems(cases.stream().map(CaseHandler::convertCase).collect(Collectors.toList()));
                        gui.addResponse(RInventoryBuilder.buildResponse(Material.getMaterial(settings.getString("settings.cases_gui.items.case_item.material")), (e) -> {
                            String accuserName = CaseHandler.getLoreValue(e.getCurrentItem(), LoreValue.ACCUSER);
                            String defenderName = CaseHandler.getLoreValue(e.getCurrentItem(), LoreValue.DEFENDER);
                            String caseType = CaseHandler.getLoreValue(e.getCurrentItem(), LoreValue.CASE_TYPE);

                            String  accuserUUID = Bukkit.getOfflinePlayer(accuserName).getUniqueId().toString();
                            String  defenderUUID = Bukkit.getOfflinePlayer(defenderName).getUniqueId().toString();

                            player.closeInventory();

                            Court court = new Court(player, accuserName, defenderName, caseType);
                            court.startCourt();

                            new Database().deleteCase(accuserUUID, defenderUUID).thenAccept(deleteCase -> {

                                if (!deleteCase) {
                                    Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cCould not delete " + accuserName + "’s case!"));
                                    return;
                                }
                            });
                        }));
                        gui.getPage(1).open(player);
                    });
                    ChatHandler.sendMessage(player, "cases_gui_opened");
                    return true;
                } else {
                    return false;
                }
            } else {
                ChatHandler.sendMessage(player, "no_permission");
                return false;
            }
        } else {
            sender.sendMessage(ChatHandler.translate("&cYou cannot execute this command from the console!"));
            return false;
        }
    }
}

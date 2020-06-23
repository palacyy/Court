package me.josh.court.files;

import org.bukkit.*;
import org.bukkit.configuration.file.*;
import me.josh.court.handlers.*;
import java.io.*;
import java.util.Arrays;

import me.josh.court.*;

public class Settings {

    public Settings() {
        File file = new File(Bukkit.getServer().getPluginManager().getPlugin("Court").getDataFolder(), File.separator + "settings.yml");
        YamlConfiguration data = YamlConfiguration.loadConfiguration(file);
        if (!file.exists()) {
            try {
                data.set("settings.prefix", "&9Court &8»");
                data.set("settings.no_permission", "%prefix% &cYou do not have enough permissions.");
                data.set("settings.target_not_found", "%prefix% &cCannot find player: %target%");
                data.set("settings.help", Arrays.asList("&8&m-----------------------------------------",
                        "&8&l» &9&lCourt&7:",
                        "&8&m-----------------------------------------",
                        "&8» &9/court setspawn (Judge, Jury, Accuser, Defender, Aftercourt) &7- Set the spawn for each role &a(court.setspawn)",
                        "&8» &9/setjudge (player) &7- Set the server's judge &a(court.setjudge)",
                        "&8» &9/courtcase (create/delete) (accused) &7- Create or delete a courtcase &a(court.courtcase)",
                        "&8» &9/cases &7- View open cases &a(court.cases)",
                        "&8» &9/jurynotifications &7- Toggle jury notifications &a(court.jurynotifications)",
                        "&8» &9/jury (accept/leave)  &7- Join or leave courts &a(court.jury)",
                        "&8» &9/bring (accuser/defender) &7- Bring the accuser or defender to the courthouse &a(court.bring)",
                        "&8&m-----------------------------------------"));
                data.set("settings.setspawn_usage", "%prefix% &cUsage: /court setspawn (Judge, Jury, Accuser, Defender, Aftercourt)");
                data.set("settings.setspawn", "%prefix% &7You have set the &c%type%'s &7spawn to your current location.");
                data.set("settings.setjudge_usage", "%prefix% &cUsage: /setjudge (player)");
                data.set("settings.setjudge", "%prefix% &7You have set the server's judge to &b%player%&7.");
                data.set("settings.courtcase_usage", "%prefix% &cUsage: /courtcase (create/delete) (accused)");
                data.set("settings.courtcase_max_cases", 2);
                data.set("settings.courtcase_cost_per_case", 100);
                data.set("settings.courtcase_case_gui.title", "&a&lCase Type");
                data.set("settings.courtcase_case_gui.items.grief_item.material", "DIRT");
                data.set("settings.courtcase_case_gui.items.grief_item.name", "&e&lGrief Case");
                data.set("settings.courtcase_case_gui.items.grief_item.lore", Arrays.asList("&8» &7Submit a case involving &bGriefing"));
                data.set("settings.courtcase_case_gui.items.towny_item.material", "BEACON");
                data.set("settings.courtcase_case_gui.items.towny_item.name", "&e&lTowny Case");
                data.set("settings.courtcase_case_gui.items.towny_item.lore", Arrays.asList("&8» &7Submit a case involving &bTowny"));
                data.set("settings.courtcase_case_gui.items.divorce_item.material", "BED");
                data.set("settings.courtcase_case_gui.items.divorce_item.name", "&e&lDivorce Case");
                data.set("settings.courtcase_case_gui.items.divorce_item.lore", Arrays.asList("&8» &7Submit a case involving &bDivorce"));
                data.set("settings.courtcase_case_confirmation_gui.title", "&d&lConfirmation");
                data.set("settings.courtcase_case_confirmation_gui.confirm_item.name", "&a&lConfirm");
                data.set("settings.courtcase_case_confirmation_gui.confirm_item.lore", Arrays.asList("&8» &aSubmit a case against %target%"));
                data.set("settings.courtcase_case_confirmation_gui.cancel_item.name", "&c&lCancel");
                data.set("settings.courtcase_case_confirmation_gui.cancel_item.lore", Arrays.asList("&8» &cCancel the case"));
                data.set("settings.courtcase_case_confirmed", "%prefix% &aYou have submitted your case, $100 has been taken out of your account.");
                data.set("settings.courtcase_case_cancelled", "%prefix% &cYou have cancelled your case.");
                data.set("settings.courtcase_case_deleted", "%prefix% &cIf you've submitted a case against %player%, it has now been deleted.");
                data.set("settings.courtcase_max_cases_reached", "%prefix% &cYou cannot submit more than %amount% case(s)!");
                data.set("settings.courtcase_same_player", "%prefix% &cYou've already submitted a case against %player%!");
                data.set("settings.courtcase_yourself", "%prefix% &cYou cannot (submit/delete) a case against yourself.");
                data.set("settings.courtcase_not_enough_money", "%prefix% &cYou need at least $100 to submit a case.");
                data.set("settings.cases_gui_opened", "%prefix% &eYou are now viewing all of the open cases.");
                data.set("settings.cases_gui.title", "&c&lCases");
                data.set("settings.cases_gui.items.case_item.material", "BOOK");
                data.set("settings.cases_gui.items.case_item.name", "&cCase (#%case_id%)");
                data.set("settings.cases_gui.items.case_item.lore", Arrays.asList("&8» &7Accuser: &c%accuser%", "&8» &7Defender: &c%defender%", "&8» &7Case Type: &6%case_type%"));
                data.set("settings.cases_gui.items.next_page.material", "ARROW");
                data.set("settings.cases_gui.items.next_page.name", "&aNext Page");
                data.set("settings.cases_gui.items.next_page.lore", Arrays.asList("&7Go to the next page."));
                data.set("settings.cases_gui.items.previous_page.material", "REDSTONE");
                data.set("settings.cases_gui.items.previous_page.name", "&cPrevious Page");
                data.set("settings.cases_gui.items.previous_page.lore", Arrays.asList("&7Go to the previous page."));
                data.set("settings.cases_gui.items.page_number.material", "PAPER");
                data.set("settings.jury.usage", "%prefix% &cUsage: /jury (accept/leave)");
                data.set("settings.jury.max_amount_of_jurys", 15);
                data.set("settings.jury.joined_court", "%prefix% &aYou have joined the court as part of the Jury. Please take a seat.");
                data.set("settings.jury.left_court", "%prefix% &cYou have left the court before it finished, so you don't get any rewards :(");
                data.set("settings.jury.join_alert", "%prefix% &d%player% &ehas become a part of the Jury. &7(&b&o%amountOfJurys%&f&7/&b&l&o%maxAmountOfJurys%&7)");
                data.set("settings.jury.leave_alert", "%prefix% &d%player% &cis no longer a part of the Jury. &7(&b&o%amountOfJurys%&f&7/&b&l&o%maxAmountOfJurys%&7)");
                data.set("settings.jury.court_not_active", "%prefix% &cThere is no court currently happening.");
                data.set("settings.jury.not_allowed", "%prefix% &cYou are not allowed to join this court as part of the Jury.");
                data.set("settings.jury.already_in_court", "%prefix% &cYou are already a part of the Jury for the current court.");
                data.set("settings.jury.not_in_court", "%prefix% &cYou are not a part of the Jury for the current court.");
                data.set("settings.jury.court_full", "%prefix% &cThe courthouse is currently full!");
                data.set("settings.jury.jury_notification", Arrays.asList("%prefix% &8&m--------------------------------------", "%prefix% &aJury Notification:", "%prefix% &8&m--------------------------------------", "%prefix% &eA new court has just begun!", "%prefix% &bTo join use: &b/jury accept!", "%prefix% &8&m--------------------------------------"));
                data.set("settings.jury.notifications_enabled", "%prefix% &aYou have enabled jury notifications.");
                data.set("settings.jury.notifications_disabled", "%prefix% &cYou have disabled jury notifications.");
                data.set("settings.bring.usage", "%prefix% &cUsage: /bring (accuser/defender)");
                data.set("settings.bring.player_not_online", "%prefix% &c%player% is currently not online!");
                data.set("settings.bring.alert", "%prefix% &eThe judge has brought &d%player% &eto the courthouse!");
                data.set("settings.bring.player_alert", "%prefix% &aYou have been brought to the courthouse by %player%!");
                data.set("settings.court.courtChat_format.judge", "&8[&b&lCourt Chat&8] &7(&8&lJudge&7) &8&l%player% &8» &d%message%");
                data.set("settings.court.courtChat_format.accuser", "&8[&b&lCourt Chat&8] &7(&9Accuser&7) &9%player% &8» &d%message%");
                data.set("settings.court.courtChat_format.defender", "&8[&b&lCourt Chat&8] &7(&cDefender&7) &c%player% &8» &d%message%");
                data.set("settings.court.courtChat_format.jury", "&8[&b&lCourt Chat&8] &7(&6Jury&7) &6%player% &8» &d%message%");
                data.set("settings.court.judge_case_alert", Arrays.asList("%prefix% &8&m--------------------------------------", "%prefix% &aHello there %player%!", "%prefix% &bToday, you're dealing with another %case_type% case.", "%prefix% &dLet me give you some important details.", "%prefix% &8&m--------------------------------------", "%prefix% &7Accuser: &c%accuser%", "%prefix% &7Defender: &c%defender%", "%prefix% &7Case Type: &6%case_type%", "%prefix% &8&m--------------------------------------", "%prefix% &eUse /bring (accuser/defender) in order to bring the individual to court.", "%prefix% &8&m--------------------------------------"));
                data.set("settings.court.judgeItems.faith_item.material", "BOOK");
                data.set("settings.court.judgeItems.faith_item.name", "&9&l« &a&lThe Book Of Faith &9&l»");
                data.set("settings.court.judgeItems.faith_item.lore", Arrays.asList("&8» &7Click to decide &b%player%'s &7faith."));
                data.set("settings.court.faith_gui.title", "&6&lFaith");
                data.set("settings.court.faith_gui.items.guilty.name", "&c&lGuilty");
                data.set("settings.court.faith_gui.items.guilty.lore", Arrays.asList("&8» &cDeclare %player% as guilty and ban him from the server.", "&8» &cAfter clicking this, you will have to type the number of days", "&8» &cthat the defender should be banned for."));
                data.set("settings.court.faith_gui.items.innocent.name", "&a&lInnocent");
                data.set("settings.court.faith_gui.items.innocent.lore", Arrays.asList("&8» &aDeclare %player% as innocent and let him free."));
                data.set("settings.court.faith_lenght.usage", "%prefix% &7Please enter the number of days you’d like to ban &6%player% &7for as a message in chat.");
                data.set("settings.court.faith_lenght.invalid", "%prefix% &cThat’s not a valid number!");
                data.set("settings.court.faith_lenght.higher_than_zero", "%prefix% &cThe number must be greater than 0!");
                data.set("settings.court.faith_lenght.success", "%prefix% &aYou’ve successfully banned %player% for %amount% day(s)!");
                data.set("settings.court.faith_broadcasts.guilty", "&7(&9&lCOURT&7) &6%player% &chas been found guilty and has been punished!");
                data.set("settings.court.faith_broadcasts.innocent", "&7(&9&lCOURT&7) &6%player% &ehas been proven innocent and has been released!");
                data.save(file);
            }
            catch (IOException ex) {
                Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&csettings.yml could not be generated!"));
            }
        }
    }

    public static File getSettingsFile() {
        final File file = new File(Court.getInstance().getDataFolder(), "settings.yml");
        return file;
    }
}

package me.josh.court;

import me.josh.court.commands.*;
import me.josh.court.events.CourtCaseEvents;
import me.josh.court.events.CourtEvents;
import me.justrayz.rlib.RHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.craftbukkit.libs.it.unimi.dsi.fastutil.Hash;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.*;
import me.josh.court.files.*;
import org.bukkit.*;
import me.josh.court.handlers.*;
import org.bukkit.plugin.*;

import java.util.ArrayList;
import java.util.HashMap;

public class Court extends JavaPlugin {

    public static Court instance;

    public Economy eco = null;
    public HashMap<Player, String> caseAccused = new HashMap<>();
    public HashMap<Player, String> caseType = new HashMap<>();
    public ArrayList<Player> juryMembers = new ArrayList<>();
    public ArrayList<Player> courtChat = new ArrayList<>();
    public ArrayList<Player> declaringPunishment = new ArrayList<>();

    public void onEnable() {
        instance = this;
        if (!setupEconomy()) {
            getServer().getPluginManager().disablePlugin(this);
            getServer().getConsoleSender().sendMessage(ChatHandler.translate("&cCourt requires Vault in order to work!"));
            return;
        }
        new Settings();
        new Locations();
        new Inventories();
        new JuryNotifications();
        RHandler.registerHandler(this);
        registerCommands();
        registerEvents();
        Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&aCourt has been successfully enabled!"));
    }

    public void onDisable() {
        me.josh.court.objects.Court court = me.josh.court.objects.Court.getCourt();
        if (court != null) {
            court.endCourt();
        }
        Bukkit.getConsoleSender().sendMessage(ChatHandler.translate("&cCourt has been successfully disabled!"));
    }

    private void registerCommands() {
        getCommand("COURT").setExecutor(new CourtCommand());
        getCommand("SETJUDGE").setExecutor(new SetjudgeCommand());
        getCommand("COURTCASE").setExecutor(new CourtCaseCommand());
        getCommand("CASES").setExecutor(new CasesCommand());
        getCommand("JURYNOTIFICATIONS").setExecutor(new JuryNotificationsCommand());
        getCommand("JURY").setExecutor(new JuryCommand());
        getCommand("BRING").setExecutor(new BringCommand());
    }

    private void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new CourtCaseEvents(), this);
        pm.registerEvents(new CourtEvents(), this);
    }

    public static Court getInstance() {
        return instance;
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> ecoProvider = getServer().getServicesManager().getRegistration(Economy.class);
        if (ecoProvider == null) {
            return false;
        }
        eco = ecoProvider.getProvider();
        return eco != null;
    }
}

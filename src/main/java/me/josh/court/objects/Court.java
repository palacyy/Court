package me.josh.court.objects;

import me.josh.court.enums.LocationType;
import me.josh.court.files.Inventories;
import me.josh.court.handlers.CourtLocation;
import me.josh.court.handlers.EquipJudge;
import me.josh.court.handlers.JuryHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.IOException;

public class Court {

    private static Court court = null;

    public static Court getCourt() {
        return court;
    }

    private Player judge;
    private String accuserName;
    private String defenderName;
    private String caseType;

    public Court(Player judge, String accuserName, String defenderName, String caseType) {
        this.judge = judge;
        this.accuserName = accuserName;
        this.defenderName = defenderName;
        this.caseType = caseType;
    }

    public void startCourt() {
        court = this;
        new CourtLocation(judge, LocationType.JUDGE);
        new EquipJudge(court);
        JuryHandler.notifyJurys(court);
    }

    public void endCourt() {
        court = null;
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (me.josh.court.Court.getInstance().courtChat.contains(player)) {
                new CourtLocation(player, LocationType.AFTER_COURT);
            }
        }
        restoreInventories();
        JuryHandler.removeCourtChatPlayers();
        JuryHandler.removeJurys();
    }

    public void restoreInventories() {
        Player accuser = Bukkit.getPlayerExact(accuserName);
        Player defender = Bukkit.getPlayerExact(defenderName);

        try {
            Inventories.restoreInventory(judge);
            if (accuser != null) {
                Inventories.restoreInventory(accuser);
            }else {
                return;
            }
            if (defender != null) {
                Inventories.restoreInventory(defender);
            }else {
                return;
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public Player getJudge() {
        return judge;
    }

    public void setJudge(Player judge) {
        this.judge = judge;
    }

    public String getAccuserName() {
        return accuserName;
    }

    public void setAccuserName(String accuserName) {
        this.accuserName = accuserName;
    }

    public String getDefenderName() {
        return defenderName;
    }

    public void setDefenderName(String defenderName) {
        this.defenderName = defenderName;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }
}

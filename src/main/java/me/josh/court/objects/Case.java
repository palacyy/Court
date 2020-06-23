package me.josh.court.objects;

import java.util.List;

public class Case {

    private int caseID;
    private String accuserUUID;
    private String defenderUUID;
    private String caseType;

    public Case(int caseID, String accuserUUID, String defenderUUID, String caseType) {
        this.caseID = caseID;
        this.accuserUUID = accuserUUID;
        this.defenderUUID = defenderUUID;
        this.caseType = caseType;
    }

    public Case(List<Object> params) {
        this((int) params.get(0), (String) params.get(1), (String) params.get(2), (String) params.get(3));
    }

    public int getCaseID() {
        return caseID;
    }

    public void setCaseID(int caseID) {
        this.caseID = caseID;
    }

    public String getAccuserUUID() {
        return accuserUUID;
    }

    public void setAccuserUUID(String accuserUUID) {
        this.accuserUUID = accuserUUID;
    }

    public String getDefenderUUID() {
        return defenderUUID;
    }

    public void setDefenderUUID(String defenderUUID) {
        this.defenderUUID = defenderUUID;
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

}

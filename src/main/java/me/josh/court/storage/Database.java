package me.josh.court.storage;

import me.josh.court.objects.Case;
import me.justrayz.rlib.datamanagement.RDataManager;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class Database extends RDataManager {

    public Database(String location) {
        super(location);
    }

    public Database() {
        super("database.db");
    }

    @Override
    public void generate() {
        this.query("CREATE TABLE IF NOT EXISTS `cases` (case_id INTEGER PRIMARY KEY AUTOINCREMENT, accuser VARCHAR(64) NOT NULL, defender VARCHAR(64) NOT NULL, case_type VARCHAR(32) NOT NULL)", null);
    }

    public CompletableFuture<Boolean> insertCase(Case courtCase) {
        return this.queryAsync("INSERT INTO cases (accuser, defender, case_type) VALUES (?, ?, ?)", null, courtCase.getAccuserUUID(), courtCase.getDefenderUUID(), courtCase.getCaseType());
    }

    public CompletableFuture<List<Case>> getCases() {
        return this.queryResultAsync("SELECT * FROM cases", "Something went wrong whilst getting the cases!", new int[]{1, 2, 3, 4}, Object.class).thenApply(a -> a.stream().map(Case::new).collect(Collectors.toList()));
    }

    public CompletableFuture<Boolean> deleteCase(String accuserUUID, String defenderUUID) {
        return this.queryAsync("DELETE FROM cases WHERE accuser=? AND defender=?", "Something went wrong whilst deleting the case!", accuserUUID, defenderUUID);
    }

    public CompletableFuture<Integer> getCasesAmount(String accuserUUID) {
        return this.queryResultAsync("SELECT COUNT(*) FROM cases WHERE accuser=?", null, new int[]{1}, Object.class, accuserUUID).thenApply((l) -> ((int) l.get(0).get(0)));
    }

    public CompletableFuture<Boolean> hasReportedSamePlayer(String accuserUUID, String defenderUUID) {
        return this.queryResultAsync("SELECT COUNT(*) FROM cases WHERE accuser=? AND defender=?", null, new int[]{1}, Object.class, accuserUUID, defenderUUID).thenApply((l) -> (((int) l.get(0).get(0)) >= 1));
    }
}
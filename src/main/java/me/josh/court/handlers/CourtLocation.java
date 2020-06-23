package me.josh.court.handlers;

import me.josh.court.enums.LocationType;
import me.josh.court.files.Locations;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class CourtLocation {

    public CourtLocation(Player player, LocationType locationType) {
        File locationsFile = Locations.getLocationsFile();
        YamlConfiguration locations = YamlConfiguration.loadConfiguration(locationsFile);

        switch (locationType) {

            case JUDGE:
                Location judgeLocation = new Location(Bukkit.getWorld(locations.getString("locations.judge_spawn.world")),
                        locations.getInt("locations.judge_spawn.x"), locations.getInt("locations.judge_spawn.y"),
                        locations.getInt("locations.judge_spawn.z"), (float) locations.getDouble("locations.judge_spawn.yaw"),
                        (float) locations.getDouble("locations.judge_spawn.pitch"));
                player.teleport(judgeLocation.add(0.5, 0, 0.5));
                break;

            case JURY:
                Location juryLocation = new Location(Bukkit.getWorld(locations.getString("locations.jury_spawn.world")),
                        locations.getInt("locations.jury_spawn.x"), locations.getInt("locations.jury_spawn.y"),
                        locations.getInt("locations.jury_spawn.z"), (float) locations.getDouble("locations.jury_spawn.yaw"),
                        (float) locations.getDouble("locations.jury_spawn.pitch"));
                player.teleport(juryLocation.add(0.5, 0, 0.5));
                break;

            case ACCUSER:
                Location accuserLocation = new Location(Bukkit.getWorld(locations.getString("locations.accuser_spawn.world")),
                        locations.getInt("locations.accuser_spawn.x"), locations.getInt("locations.accuser_spawn.y"),
                        locations.getInt("locations.accuser_spawn.z"), (float) locations.getDouble("locations.accuser_spawn.yaw"),
                        (float) locations.getDouble("locations.accuser_spawn.pitch"));
                player.teleport(accuserLocation.add(0.5, 0, 0.5));
                break;

            case DEFENDER:
                Location defenderLocation = new Location(Bukkit.getWorld(locations.getString("locations.defender_spawn.world")),
                        locations.getInt("locations.defender_spawn.x"), locations.getInt("locations.defender_spawn.y"),
                        locations.getInt("locations.defender_spawn.z"), (float) locations.getDouble("locations.defender_spawn.yaw"),
                        (float) locations.getDouble("locations.defender_spawn.pitch"));
                player.teleport(defenderLocation.add(0.5, 0, 0.5));
                break;

            case AFTER_COURT:
                Location afterCourtLocation = new Location(Bukkit.getWorld(locations.getString("locations.afterCourt_spawn.world")),
                        locations.getInt("locations.afterCourt_spawn.x"), locations.getInt("locations.afterCourt_spawn.y"),
                        locations.getInt("locations.afterCourt_spawn.z"), (float) locations.getDouble("locations.afterCourt_spawn.yaw"),
                        (float) locations.getDouble("locations.afterCourt_spawn.pitch"));
                player.teleport(afterCourtLocation.add(0.5, 0, 0.5));
                break;
        }
    }
}

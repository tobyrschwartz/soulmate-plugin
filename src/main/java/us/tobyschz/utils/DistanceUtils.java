package us.tobyschz.utils;

import org.bukkit.entity.Player;
import org.bukkit.Location;

public class DistanceUtils {
    private DistanceUtils(){}

    public static double getDistance(Player player1, Player player2) {
        Location loc1 = player1.getLocation();
        Location loc2 = player2.getLocation();

        double distance = calcDistance(loc1, loc2);
        return loc1.distance(loc2);
    }

    public static double calcDistance(Location loc1, Location loc2) {
        double distance = loc1.distance(loc2);
        Debug.log("Distance calculated: " + distance);
        return distance;
    }

}

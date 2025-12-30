package us.tobyschz.managers;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import us.tobyschz.SoulmatePlugin;

@Getter
public class ConfigManager {
    private boolean debug;
    private int penaltyDistance;
    private long maxTimeApart;
    private double damageMultiplier;

    public ConfigManager() {
        load(SoulmatePlugin.getInstance());
    }

    public void load(JavaPlugin plugin) {
        plugin.reloadConfig();
        debug = plugin.getConfig().getBoolean("debug");
        penaltyDistance = plugin.getConfig().getInt("penalty_distance");
        maxTimeApart = plugin.getConfig().getLong("max_time_apart");
        damageMultiplier = plugin.getConfig().getDouble("damage_multiplier");
    }
}

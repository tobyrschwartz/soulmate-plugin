package us.tobyschz.utils;

import org.bukkit.Bukkit;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.ConfigManager;

public class Debug {
    private static final ConfigManager manager = SoulmatePlugin.getInstance().getConfigManager();

    public static void log(String msg) {
        if (!manager.isDebug()) return;
        Bukkit.getLogger().warning("[DEBUG] " + msg);
    }
}

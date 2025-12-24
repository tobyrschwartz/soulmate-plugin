package us.tobyschz.utils;

import org.bukkit.Bukkit;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.DebugManager;

public class Debug {
    private static final DebugManager manager = SoulmatePlugin.getInstance().getDebugManager();

    public static void log(String msg) {
        if (!manager.isEnabled()) return;
        Bukkit.getLogger().warning("[DEBUG] " + msg);
    }
}

package us.tobyschz;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import us.tobyschz.listeners.BedListener;
import us.tobyschz.listeners.JoinLeaveListener;
import us.tobyschz.listeners.MovementListener;
import us.tobyschz.managers.BondManager;
import us.tobyschz.managers.DebugManager;
import us.tobyschz.managers.UserManager;
import us.tobyschz.utils.Debug;

public final class SoulmatePlugin extends JavaPlugin {
    @Getter
    public static SoulmatePlugin instance;
    @Getter
    private BondManager bondManager;
    @Getter
    private UserManager userManager;
    @Getter
    private DebugManager debugManager;

    @Override
    public void onEnable() {
        getLogger().info("Starting soulmate plugin...");

        instance = this;
        debugManager = new DebugManager();

        if (debugManager.isEnabled()) {
            Debug.log("DEBUG MODE IS ON");
        }

        userManager = new UserManager();
        for (Player player : getServer().getOnlinePlayers()) {
            userManager.add(player);
        }
        bondManager = new BondManager();

        registerListeners();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling soulmate plugin...");
        bondManager.saveBonds();
        userManager.shutdown();
    }

    public void registerListeners() {
        PluginManager pm = getServer().getPluginManager();
        pm.registerEvents(new JoinLeaveListener(), this);
        pm.registerEvents(new MovementListener(), this);
        pm.registerEvents(new BedListener(), this);
    }
}

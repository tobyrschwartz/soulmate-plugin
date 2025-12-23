package us.tobyschz;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.managers.UserManager;

public final class SoulmatePlugin extends JavaPlugin {
    @Getter
    public static SoulmatePlugin instance;
    @Getter
    private BondManager bondManager;
    @Getter
    private UserManager userManager;

    @Override
    public void onEnable() {
        getLogger().info("Starting soulmate plugin...");
        instance = this;
        userManager = new UserManager();
        for (Player player : getServer().getOnlinePlayers()) {
            userManager.add(player);
        }
        bondManager = new BondManager();
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling soulmate plugin...");
        bondManager.saveBonds();
        userManager.shutdown();
    }

}

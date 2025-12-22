package us.tobyschz;

import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("unused")
public final class SoulmatePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("Enabling soulmate plugin");
    }

    @Override
    public void onDisable() {
        getLogger().info("Disabling soulmate plugin");
    }
}

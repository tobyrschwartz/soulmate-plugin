package us.tobyschz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.managers.UserManager;

import java.util.UUID;

public class JoinLeaveListener implements Listener {

    UserManager userManager = SoulmatePlugin.getInstance().getUserManager();
    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        userManager.add(player);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        userManager.remove(uuid);
        bondManager.removePendingBed(uuid);
    }
}

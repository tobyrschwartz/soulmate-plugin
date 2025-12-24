package us.tobyschz.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.utils.Debug;

public class MovementListener implements Listener {

    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo() != null && e.getFrom().getBlockX() == e.getTo().getBlockX()
                && e.getFrom().getBlockY() == e.getTo().getBlockY()
                && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
            return;
        }
        bondManager.getOnlineBondPartner(e.getPlayer()).ifPresent(partner -> {
            bondHandler();
        });
    }

    public void bondHandler() {
        Debug.log("Bond handler has been called");
    }
}

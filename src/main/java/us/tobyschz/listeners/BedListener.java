package us.tobyschz.listeners;

import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerBedLeaveEvent;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.managers.UserManager;
import us.tobyschz.models.BedKey;
import us.tobyschz.utils.Debug;

import java.util.Optional;
import java.util.UUID;

import static us.tobyschz.utils.FireworkUtils.spawnFireworks;

public class BedListener implements Listener {

    UserManager userManager = SoulmatePlugin.getInstance().getUserManager();
    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();

    @EventHandler
    public void onBedEnter(PlayerBedEnterEvent event) {
        if (event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK) {
            Debug.log("Bed event ok for " + event.getPlayer().getName());
            UUID uuid = event.getPlayer().getUniqueId();
            if (bondManager.hasBond(uuid)) return;

            Block bed = event.getBed();
            Debug.log("Adding pending bed for " + event.getPlayer().getName());
            Optional<UUID> bonded = bondManager.addPendingBed(uuid, new BedKey(
                    bed.getWorld().getUID(),
                    bed.getX(),
                    bed.getY(),
                    bed.getZ()
            ));
            if (bonded.isPresent()) {
                Player other = userManager.getPlayer(bonded.get());
                Debug.log("Bond added for " + event.getPlayer().getName() + " and for " + other.getName());
                event.getPlayer().sendMessage(String.format(ChatColor.RED + "You have been bonded to %s!", other.getName()));
                other.sendMessage(String.format(ChatColor.RED + "You have been bonded to %s!", event.getPlayer().getName()));
                spawnFireworks(bed.getLocation());
            }
        }
    }

    @EventHandler
    public void onBedLeave(PlayerBedLeaveEvent event) {
        Debug.log("Bed exit event for " + event.getPlayer().getName());
        UUID uuid = event.getPlayer().getUniqueId();
        bondManager.removePendingBed(uuid);
    }
}

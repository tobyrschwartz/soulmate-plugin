package us.tobyschz.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.managers.ConfigManager;
import us.tobyschz.utils.Debug;

import java.util.Optional;

public class DamageListener implements Listener {

    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();
    ConfigManager configManager = SoulmatePlugin.getInstance().getConfigManager();

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) return;

        Optional<Player> partner = bondManager.getOnlineBondPartner(p);
        partner.ifPresent(player -> {
            double expectedDamage = e.getDamage() * configManager.getDamageMultiplier();
            double newHealth = player.getHealth() - expectedDamage;
            if (newHealth > 0) {
                player.setHealth(newHealth);
                player.sendMessage(ChatColor.RED + "Shared pain: -" + (int)expectedDamage + "HP");
            }
        });
    }
}

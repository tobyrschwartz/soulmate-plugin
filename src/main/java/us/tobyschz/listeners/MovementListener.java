package us.tobyschz.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.managers.ConfigManager;
import us.tobyschz.models.Bond;
import us.tobyschz.utils.Debug;
import us.tobyschz.utils.DistanceUtils;

public class MovementListener implements Listener {

    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();
    ConfigManager configManager = SoulmatePlugin.getInstance().getConfigManager();

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (e.getTo() != null && e.getFrom().getBlockX() == e.getTo().getBlockX()
                && e.getFrom().getBlockY() == e.getTo().getBlockY()
                && e.getFrom().getBlockZ() == e.getTo().getBlockZ()) {
            return;
        }
        Player p = e.getPlayer();
        bondManager.getOnlineBondPartner(p).ifPresent(partner -> {
            bondHandler(p, partner);
        });
    }

    public void bondHandler(Player p, Player partner) {
        Debug.log("Bond handler has been called");
        Bond bond = bondManager.getBond(p.getUniqueId());
        double dist = DistanceUtils.getDistance(p, partner);
        if (dist <= configManager.getPerkDistance()) {
            PotionEffect strengthEffect = new PotionEffect(PotionEffectType.STRENGTH, 10, 1);
            p.addPotionEffect(strengthEffect);
            partner.addPotionEffect(strengthEffect);
            PotionEffect hasteEffect = new PotionEffect(PotionEffectType.HASTE, 10, 1);
            p.addPotionEffect(hasteEffect);
            partner.addPotionEffect(hasteEffect);
        }
        if (dist <= configManager.getPenaltyDistance()) {
            PotionEffect effect = new PotionEffect(PotionEffectType.REGENERATION, 30, 1);
            p.addPotionEffect(effect);
            partner.addPotionEffect(effect);
            bond.setLastTogether(p.getWorld().getFullTime());
            bond.setClose(true);
        } else {
            if (bond != null) {
                bond.setClose(false);
                long diff = p.getWorld().getFullTime() - bond.lastTogether();
                if (configManager.getMaxTimeApart() > diff) {
                    bondManager.applyPenalty(p, partner, diff, configManager.getMaxTimeApart());
                }
            }
        }
    }
}

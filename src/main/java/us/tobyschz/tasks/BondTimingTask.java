package us.tobyschz.tasks;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.models.Bond;

public class BondTimingTask extends BukkitRunnable {

    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();
    long maxTimeApart = SoulmatePlugin.getInstance().getConfigManager().getMaxTimeApart();

    @Override
    public void run() {
        for (Bond b : bondManager.getBonds()) {
            if (!b.isClose()) {
                b.incrementTimeApart();
                if (b.getTimeApart() / maxTimeApart != 0L) {
                    int periodsApart = Math.toIntExact(b.getTimeApart() / maxTimeApart);
                    PotionEffect effect = new PotionEffect(PotionEffectType.HUNGER,
                            periodsApart * 20 * 60, 0);
                    for (Player p : bondManager.getOnlinePlayers(b)) {
                        if (p != null) {
                            p.addPotionEffect(effect);
                            p.sendMessage(ChatColor.RED +
                                    "Your bond has been separated for too long! You have hunger for " + periodsApart +
                                    "minutes.");
                        }
                    }
                }
            } else {
                b.setTimeApart(0);
            }
        }
    }
}

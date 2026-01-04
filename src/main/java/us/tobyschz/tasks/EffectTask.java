package us.tobyschz.tasks;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;
import us.tobyschz.models.Bond;

public class EffectTask extends BukkitRunnable {

    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();

    PotionEffect strengthEffect = new PotionEffect(PotionEffectType.STRENGTH, 100, 0);
    PotionEffect hasteEffect = new PotionEffect(PotionEffectType.HASTE, 100, 0);
    PotionEffect regenEffect = new PotionEffect(PotionEffectType.REGENERATION, 100, 0);

    @Override
    public void run() {

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!bondManager.hasBond(p)) continue;

            Bond bond = bondManager.getBond(p);

            if (bond.isClose()) p.addPotionEffect(regenEffect);

            if (bond.isPerkClose()) {
                p.addPotionEffect(strengthEffect);
                p.addPotionEffect(hasteEffect);
            }

        }
    }
}
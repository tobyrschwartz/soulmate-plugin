package us.tobyschz.tasks;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.managers.BondManager;

public class ParticleTask extends BukkitRunnable {

    BondManager bondManager = SoulmatePlugin.getInstance().getBondManager();
    public ParticleTask(JavaPlugin plugin) {
    }

    @Override
    public void run() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!bondManager.hasBondAndIsClose(p)) continue;

            Location loc = p.getLocation().add(0, 1.2, 0);

            bondManager.getOnlineBondPartner(p).ifPresent(partner -> {
                p.spawnParticle(
                        Particle.HEART,
                        loc,
                        2,
                        0.3, 0.3, 0.3,
                        0.0
                );
            });

        }
    }
}

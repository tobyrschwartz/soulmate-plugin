package us.tobyschz.utils;

import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

import org.bukkit.Color;

public class FireworkUtils {
    private FireworkUtils() {}

    public static void spawnFireworks(Location location) {
        World world = location.getWorld();
        if (world == null) return;
        Firework fw = world.spawn(location, Firework.class);
        FireworkMeta meta = fw.getFireworkMeta();

        meta.addEffect(
                FireworkEffect.builder()
                        .with(FireworkEffect.Type.BALL_LARGE)
                        .withColor(Color.FUCHSIA, Color.RED)
                        .withFade(Color.WHITE)
                        .flicker(true)
                        .trail(true)
                        .build()
        );

        meta.setPower(1);
        fw.setFireworkMeta(meta);
    }
}

package us.tobyschz.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.models.BedKey;
import us.tobyschz.models.Bond;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

import static us.tobyschz.models.BedKey.areBedsAdjacent;

public class BondManager {
    private final HashMap<UUID, Bond> bonds = new HashMap<>();
    private final HashMap<UUID, BedKey> pendingBeds = new HashMap<>();
    private final File file;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public BondManager() {
        this.file = new File(SoulmatePlugin.getInstance().getDataFolder(), "bonds.json");

        loadBonds();
    }

    private void loadBonds() {
        if  (!this.file.exists()) return;

        try (Reader reader = new FileReader(this.file)) {
            Type type = new TypeToken<List<BondRecord>>(){}.getType();
            List<BondRecord> records = gson.fromJson(reader, type);

            if (records == null) return;

            for (BondRecord r : records) {
                Bond bond = new Bond(r.user1(), r.user2(), r.timestamp());
                bonds.put(r.user1(), bond);
                bonds.put(r.user2(), bond);
            }
        } catch (IOException e) {
            SoulmatePlugin.getInstance().getLogger().warning( "Failed to load Bonds file!\n" + e);
        }
    }

    public void saveBonds() {
        Set<Bond> uniqueBonds = new HashSet<>(bonds.values());
        List<BondRecord> records = new ArrayList<>();

        for (Bond bond : uniqueBonds) {
            records.add(new BondRecord(bond.user1(), bond.user2(), bond.lastTogether()));
        }

        try {
            file.getParentFile().mkdirs();
            try (Writer writer = new FileWriter(file)) {
                gson.toJson(records, writer);
            }
        } catch (IOException e) {
            SoulmatePlugin.getInstance().getLogger().warning("Failed to save Bonds file!\n" + e);
        }
    }

    public boolean hasBond(UUID uuid) {
        return bonds.containsKey(uuid);
    }

    public Optional<Player> getOnlineBondPartner(Player player) {
        return Optional.ofNullable(bonds.get(player.getUniqueId()))
                .map(bond -> Bukkit.getPlayer(bond.getPartner(player.getUniqueId())));
    }

    public Bond getBond(UUID uuid) {
        return bonds.get(uuid);
    }

    public Optional<UUID> addPendingBed(UUID uuid, BedKey bedKey) {
        for (Map.Entry<UUID, BedKey> entry : pendingBeds.entrySet()) {
            if (areBedsAdjacent(entry.getValue(), bedKey)) {
                createBond(uuid, entry.getKey(), bedKey.time());
                pendingBeds.remove(entry.getKey());
                return Optional.ofNullable(entry.getKey());
            }
        }
        pendingBeds.put(uuid, bedKey);
        return Optional.empty();
    }

    public void createBond(UUID uuid, UUID partner, long timestamp) {
        Bond bond = new Bond(uuid, partner, timestamp);
        bonds.put(uuid, bond);
        bonds.put(partner, bond);
    }

    public void removePendingBed(UUID uuid) {
        pendingBeds.remove(uuid);
    }

    public void applyPenalty(Player player, Player partner, long diff, long penalty_time) {
        int periods_apart = Math.toIntExact(diff / ((penalty_time > 0) ? penalty_time : 1));
        PotionEffect effect = new PotionEffect(PotionEffectType.HUNGER, periods_apart * 20 * 60, 1);
        player.addPotionEffect(effect);
        partner.addPotionEffect(effect);
        player.sendMessage(ChatColor.RED + "You have been apart for too long and now have hunger for " +
                periods_apart + "minutes!");
        partner.sendMessage(ChatColor.RED + "You have been apart for too long and now have hunger for " +
                periods_apart + "minutes!");
    }

    public boolean hasBondAndIsClose(UUID uuid) {
        return bonds.containsKey(uuid) && bonds.get(uuid).isClose();
    }

    public boolean hasBondAndIsClose(Player p) {
        UUID uuid = p.getUniqueId();
        return bonds.containsKey(uuid) && bonds.get(uuid).isClose();
    }
}

record BondRecord (UUID user1, UUID user2, long timestamp) {}





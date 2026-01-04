package us.tobyschz.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
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
    private final UserManager userManager = SoulmatePlugin.getInstance().getUserManager();

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
                int online = 0;
                if (userManager.isOnline(r.user1())) online++;
                if (userManager.isOnline(r.user2())) online++;
                Bond bond = new Bond(r.user1(), r.user2(), r.timeApart(), online);
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
            records.add(new BondRecord(bond.getUser1(), bond.getUser2(), bond.getTimeApart()));
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
                createBond(uuid, entry.getKey(), 0, 2);
                pendingBeds.remove(entry.getKey());
                return Optional.ofNullable(entry.getKey());
            }
        }
        pendingBeds.put(uuid, bedKey);
        return Optional.empty();
    }

    public void createBond(UUID uuid, UUID partner, long timeApart, int online) {
        Bond bond = new Bond(uuid, partner, timeApart, online);
        bonds.put(uuid, bond);
        bonds.put(partner, bond);
    }

    public void removePendingBed(UUID uuid) {
        pendingBeds.remove(uuid);
    }

    public boolean hasBondAndIsClose(UUID uuid) {
        return bonds.containsKey(uuid) && bonds.get(uuid).isClose();
    }

    public boolean hasBondAndIsClose(Player p) {
        UUID uuid = p.getUniqueId();
        return hasBondAndIsClose(uuid);
    }

    public boolean hasBond(Player p) {
        return hasBond(p.getUniqueId());
    }

    public Bond getBond(Player p) {
        return getBond(p.getUniqueId());
    }

    public Collection<Bond> getBonds() {
        return bonds.values();
    }

    public void setOnline(UUID uuid, boolean online) {
        Bond bond = bonds.get(uuid);
        if (bond != null) {
            bond.setOnline(bond.getOnline() + (online ? 1 : -1 ));
        }
    }

    public Player[] getOnlinePlayers(Bond bond) {
        Player[] players = new Player[2];
        players[0] = userManager.getPlayer(bond.getUser1());
        players[1] = userManager.getPlayer(bond.getUser2());
        return players;
    }


    public void setOnline(Player player,  boolean online) {
        setOnline(player.getUniqueId(), online);
    }
}

record BondRecord (UUID user1, UUID user2, long timeApart) {}





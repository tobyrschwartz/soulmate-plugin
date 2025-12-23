package us.tobyschz.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import us.tobyschz.SoulmatePlugin;
import us.tobyschz.models.Bond;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;

public class BondManager {
    private final HashMap<UUID, Bond> bonds = new HashMap<>();
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
                Bond bond = new Bond(r.user1(), r.user2());
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
            records.add(new BondRecord(bond.user1(), bond.user2()));
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

}

record BondRecord (UUID user1, UUID user2) {}



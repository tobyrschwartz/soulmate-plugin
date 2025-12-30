package us.tobyschz.models;

import java.util.UUID;

public record BedKey(long time, UUID worldId, int x, int y, int z) {
    public static boolean areBedsAdjacent(BedKey a, BedKey b) {
        if (!a.worldId().equals(b.worldId())) return false;
        if (a.y() != b.y()) return false;

        int dx = Math.abs(a.x() - b.x());
        int dz = Math.abs(a.z() - b.z());

        return dx + dz == 1;
    }
}

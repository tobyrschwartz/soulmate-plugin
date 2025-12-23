package us.tobyschz.models;

import java.util.UUID;

public record Bond(UUID user1, UUID user2) {
    public UUID getPartner(UUID uuid) {
        if (uuid.equals(user1)) return user2;
        if (uuid.equals(user2)) return user1;
        throw new IllegalArgumentException("UUID not in bond");
    }

    public boolean contains(UUID uuid) {
        return uuid.equals(user1) || uuid.equals(user2);
    }
}


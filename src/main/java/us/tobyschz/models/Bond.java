package us.tobyschz.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

public class Bond {
    private final UUID user1;
    private final UUID user2;
    @Setter
    private long lastTogether;
    @Getter
    @Setter
    private boolean close = false;

    public Bond(UUID user1, UUID user2, long lastTogether) {
        this.user1 = user1;
        this.user2 = user2;
        this.lastTogether = lastTogether;
    }

    public UUID getPartner(UUID uuid) {
        if (uuid.equals(user1)) return user2;
        if (uuid.equals(user2)) return user1;
        throw new IllegalArgumentException("UUID not in bond");
    }

    public boolean contains(UUID uuid) {
        return uuid.equals(user1) || uuid.equals(user2);
    }

    public UUID user1() {
        return user1;
    }

    public UUID user2() {
        return user2;
    }

    public long lastTogether() {
        return lastTogether;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Bond) obj;
        return Objects.equals(this.user1, that.user1) &&
                Objects.equals(this.user2, that.user2) &&
                this.lastTogether == that.lastTogether;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2, lastTogether);
    }

    @Override
    public String toString() {
        return "Bond[" +
                "user1=" + user1 + ", " +
                "user2=" + user2 + ", " +
                "lastTogether=" + lastTogether + ']';
    }

}


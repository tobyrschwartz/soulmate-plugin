package us.tobyschz.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

@Getter
public class Bond {
    private final UUID user1;
    private final UUID user2;
    @Setter
    private long timeApart;
    @Setter
    private boolean perkClose = false;
    @Setter
    private boolean close = false;
    @Setter
    private int online;

    public Bond(UUID user1, UUID user2, long timeApart, int online) {
        this.user1 = user1;
        this.user2 = user2;
        this.timeApart = timeApart;
        this.online = online;
    }

    public Bond(UUID user1, UUID user2, long timeApart) {
        this.user1 = user1;
        this.user2 = user2;
        this.timeApart = timeApart;
        this.online = 0;
    }

    public UUID getPartner(UUID uuid) {
        if (uuid.equals(user1)) return user2;
        if (uuid.equals(user2)) return user1;
        throw new IllegalArgumentException("UUID not in bond");
    }

    public boolean contains(UUID uuid) {
        return uuid.equals(user1) || uuid.equals(user2);
    }

    public void incrementTimeApart() {
        timeApart = timeApart + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Bond) obj;
        return Objects.equals(this.user1, that.user1) &&
                Objects.equals(this.user2, that.user2) &&
                this.timeApart == that.timeApart;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user1, user2, timeApart);
    }

    @Override
    public String toString() {
        return "Bond[" +
                "user1=" + user1 + ", " +
                "user2=" + user2 + ", " +
                "timeApart=" + timeApart + ']';
    }

}


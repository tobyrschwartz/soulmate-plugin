package us.tobyschz.models;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class User {
    private final UUID uuid;
    @Setter
    private boolean isDowned;
    @Setter
    private long downedUntil;

    public User(UUID uuid) {
        this.uuid = uuid;
        this.isDowned = false;
        this.downedUntil = 0L;
    }

    public User(UUID uuid, UUID soulmate, boolean isDowned, long downedUntil) {
        this.uuid = uuid;
        this.isDowned = isDowned;
        this.downedUntil = downedUntil;
    }

}

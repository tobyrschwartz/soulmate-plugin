package us.tobyschz.managers;

import org.bukkit.entity.Player;
import us.tobyschz.models.User;

import java.util.*;

public class UserManager {
    private final HashMap<UUID, User> onlineUsers = new HashMap<>();

    public Optional<User> getUserByUUID(UUID uuid) {
        return Optional.ofNullable(onlineUsers.get(uuid));
    }

    public User add(Player player) {
        return onlineUsers.computeIfAbsent(
                player.getUniqueId(),
                User::new
        );
    }

    public void remove(UUID uuid) {
        onlineUsers.remove(uuid);
    }

    public void shutdown() {
        onlineUsers.clear();
    }

}

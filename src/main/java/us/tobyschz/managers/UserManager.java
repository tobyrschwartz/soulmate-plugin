package us.tobyschz.managers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import us.tobyschz.models.User;
import us.tobyschz.utils.Debug;

import java.util.*;

public class UserManager {
    private final HashMap<UUID, User> onlineUsers = new HashMap<>();

    public Optional<User> getUserByUUID(UUID uuid) {
        return Optional.ofNullable(onlineUsers.get(uuid));
    }

    public User add(Player player) {
        Debug.log("Adding user " + player.getName() + " to the online user list");
        return onlineUsers.computeIfAbsent(
                player.getUniqueId(),
                User::new
        );
    }

    public void remove(UUID uuid) {
        onlineUsers.remove(uuid);
    }

    public Player getPlayer(UUID uuid) {
        return Bukkit.getPlayer(uuid);
    }

    public boolean isOnline(UUID uuid) {
        return onlineUsers.containsKey(uuid);
    }

    public void shutdown() {
        onlineUsers.clear();
    }

}

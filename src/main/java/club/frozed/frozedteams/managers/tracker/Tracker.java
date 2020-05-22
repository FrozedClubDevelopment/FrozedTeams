package club.frozed.frozedteams.managers.tracker;

import org.bukkit.entity.Player;

public interface Tracker {
    void trackPlayer(Player player);

    void trackAll();
}

package club.frozed.frozedteams.managers.tracker;

import org.bukkit.entity.Player;

public interface Track {
    void trackPlayer(Player player);

    void trackAll();
}

package club.frozed.frozedteams.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryzeon
 * Project: FrozedTeams
 * Date: 11/09/2020 @ 19:26
 * Template by Elp1to
 */

public class Utils {

    public static List<Player> getOnlinePlayers() {
        List<Player> players = new ArrayList<>();
        for (Player player : Bukkit.getServer().getOnlinePlayers())
            players.add(player);
        return players;
    }

}

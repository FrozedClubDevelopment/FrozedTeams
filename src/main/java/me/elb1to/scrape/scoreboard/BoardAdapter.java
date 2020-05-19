package me.elb1to.scrape.scoreboard;

import me.elb1to.scrape.scoreboard.cooldown.BoardCooldown;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Set;

public interface BoardAdapter {
    String getTitle(final Player p0);

    List<String> getScoreboard(final Player p0, final Board p1, final Set<BoardCooldown> p2);
}

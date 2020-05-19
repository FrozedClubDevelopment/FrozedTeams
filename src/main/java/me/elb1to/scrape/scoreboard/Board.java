package me.elb1to.scrape.scoreboard;

import me.elb1to.scrape.Scrape;
import me.elb1to.scrape.ScrapeOptions;
import me.elb1to.scrape.scoreboard.cooldown.BoardCooldown;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

public class Board {
    private static Set<Board> boards;
    private Scoreboard scoreboard;
    private Player player;
    private Objective objective;
    private Set<String> keys;
    private List<BoardEntry> entries;
    private Set<BoardCooldown> cooldowns;
    private final Scrape scrape;
    private final ScrapeOptions options;

    public Board(final Player player, final Scrape scrape, final ScrapeOptions options) {
        this.player = player;
        this.scrape = scrape;
        this.options = options;
        this.keys = new HashSet<String>();
        this.cooldowns = new HashSet<BoardCooldown>();
        this.entries = new ArrayList<BoardEntry>();
        this.setup();
    }

    private void setup() {
        if (this.options.hook() && !this.player.getScoreboard().equals(Bukkit.getScoreboardManager().getMainScoreboard())) {
            this.scoreboard = this.player.getScoreboard();
        } else {
            this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        }
        (this.objective = this.scoreboard.registerNewObjective("elb1to_is_a_god", "dummy")).setDisplaySlot(DisplaySlot.SIDEBAR);
        if (this.scrape.getAdapter() != null) {
            this.objective.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.scrape.getAdapter().getTitle(this.player)));
        } else {
            this.objective.setDisplayName("Default Title");
        }
        Board.boards.add(this);
    }

    public String getNewKey(final BoardEntry entry) {
        for (final ChatColor color : ChatColor.values()) {
            String colorText = color + "" + ChatColor.WHITE;
            if (entry.getText().length() > 16) {
                final String sub = entry.getText().substring(0, 16);
                colorText += ChatColor.getLastColors(sub);
            }
            if (!this.keys.contains(colorText)) {
                this.keys.add(colorText);
                return colorText;
            }
        }
        throw new IndexOutOfBoundsException("No more keys available!");
    }

    public List<String> getBoardEntriesFormatted() {
        final List<String> toReturn = new ArrayList<String>();
        for (final BoardEntry entry : new ArrayList<BoardEntry>(this.entries)) {
            toReturn.add(entry.getText());
        }
        return toReturn;
    }

    public BoardEntry getByPosition(final int position) {
        int i = 0;
        for (final BoardEntry board : this.entries) {
            if (i == position) {
                return board;
            }
            ++i;
        }
        return null;
    }

    public BoardCooldown getCooldown(final String id) {
        for (final BoardCooldown cooldown : this.getCooldowns()) {
            if (cooldown.getId().equals(id)) {
                return cooldown;
            }
        }
        return null;
    }

    public Set<BoardCooldown> getCooldowns() {
        final Iterator<BoardCooldown> iterator = this.cooldowns.iterator();
        while (iterator.hasNext()) {
            final BoardCooldown cooldown = iterator.next();
            if (System.currentTimeMillis() >= cooldown.getEnd()) {
                iterator.remove();
            }
        }
        return this.cooldowns;
    }

    public static Board getByPlayer(final Player player) {
        for (final Board board : Board.boards) {
            if (board.getPlayer().getName().equals(player.getName())) {
                return board;
            }
        }
        return null;
    }

    public static Set<Board> getBoards() {
        return Board.boards;
    }

    public Scoreboard getScoreboard() {
        return this.scoreboard;
    }

    public Player getPlayer() {
        return this.player;
    }

    public Objective getObjective() {
        return this.objective;
    }

    public Set<String> getKeys() {
        return this.keys;
    }

    public List<BoardEntry> getEntries() {
        return this.entries;
    }

    static {
        Board.boards = new HashSet<Board>();
    }
}

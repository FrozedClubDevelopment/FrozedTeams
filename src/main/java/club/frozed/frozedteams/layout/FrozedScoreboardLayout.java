package club.frozed.frozedteams.layout;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.data.PlayerData;
import club.frozed.frozedteams.enums.PlayerState;
import club.frozed.frozedteams.managers.PlayerDataManager;
import club.frozed.frozedteams.managers.PlayerManager;
import club.frozed.frozedteams.utils.board.Board;
import club.frozed.frozedteams.utils.board.BoardAdapter;
import club.frozed.frozedteams.utils.chat.ColorText;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class FrozedScoreboardLayout implements BoardAdapter {

    @Override
    public String getTitle(Player player) {
        String title = FrozedTeams.getInstance().getConfiguration("design").getString("Scoreboard.title");
        return ColorText.translate(title);
    }

    @Override
    public List<String> getScoreboard(Player player, Board board) {
        return ColorText.translate(getPlayerScoreboard(player));
    }

    private List<String> getPlayerScoreboard(Player player) {
        List<String> scoreboard = new ArrayList<>();
        PlayerData data = PlayerDataManager.getInstance().getByUUID(player.getUniqueId());
        PlayerManager playerManager = PlayerManager.getInstance();

        if (data.getState() == PlayerState.IN_SPAWN) {
            for (String string : FrozedTeams.getInstance().getConfiguration("design").getStringList("spawn-scoreboard")) {
                scoreboard.add(replace(string, player));
            }
        }
        if (data.getState() == PlayerState.OUTSIDE_SPAWN) {
            for (String string : FrozedTeams.getInstance().getConfiguration("design").getStringList("outside-spawn-scoreboard")) {
                scoreboard.add(replace(string, player));
            }
        }
        
        return scoreboard;
    }

    @Override
    public long getInterval() {
        return 0;
    }

    @Override
    public void onScoreboardCreate(Player player, Scoreboard scoreboard) {
        // Empty because I want, hehe xd
    }

    public String replace(String string, Player player) {
        PlayerData data = PlayerDataManager.getInstance().getByUUID(player.getUniqueId());
        PlayerManager playerManager = PlayerManager.getInstance();
        return string
                //.replaceAll("<player_team>", playerManager.getPlayerTeamName())
                .replaceAll("<player_gold>", String.valueOf(FrozedTeams.getEconomy().getBalance(player)))
                .replaceAll("<player_ping>", String.valueOf(((CraftPlayer) player).getHandle().ping))
                ;
    }

    @Override
    public void preLoop() {
        // Empty because I want, hehe xd
    }
}

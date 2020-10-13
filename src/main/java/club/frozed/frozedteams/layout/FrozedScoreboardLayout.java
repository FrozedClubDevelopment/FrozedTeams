package club.frozed.frozedteams.layout;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.data.PlayerData;
import club.frozed.frozedteams.enums.PlayerState;
import club.frozed.frozedteams.managers.PlayerDataManager;
import club.frozed.frozedteams.managers.PlayerManager;
import club.frozed.frozedteams.utils.Utils;
import club.frozed.frozedteams.utils.chat.CC;
import club.frozed.frozedteams.utils.scoreboard.AssembleAdapter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FrozedScoreboardLayout implements AssembleAdapter {

    @Override
    public String getTitle(Player player) {
        String title = FrozedTeams.getInstance().getConfiguration("design").getString("SCOREBOARD.TITLE");
        return CC.translate(title);
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> scoreboard = new ArrayList<>();
        PlayerData data = PlayerDataManager.getInstance().getByUUID(player.getUniqueId());

        if (data.getState() == PlayerState.IN_SPAWN) {
            for (String string : FrozedTeams.getInstance().getConfiguration("design").getStringList("SCOREBOARD.SPAWN-SCOREBOARD")) {
                scoreboard.add(replace(string, player));
            }
        }
        if (data.getState() == PlayerState.OUTSIDE_SPAWN) {
            for (String string : FrozedTeams.getInstance().getConfiguration("design").getStringList("SCOREBOARD.OUTSIDE-SPAWN-SCOREBOARD")) {
                scoreboard.add(replace(string, player));
            }
        }

        return scoreboard;
    }

    public String replace(String string, Player player) {
        PlayerData data = PlayerDataManager.getInstance().getByUUID(player.getUniqueId());
        PlayerManager playerManager = PlayerManager.getInstance();
        return string
                //.replaceAll("<player_team>", playerManager.getPlayerTeamName())
                .replaceAll("<player_gold>", String.valueOf(FrozedTeams.getEconomy().getBalance(player)))
                .replaceAll("<player_ping>", String.valueOf(Utils.getPing(player)))
                ;
    }
}

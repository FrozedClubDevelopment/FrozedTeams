package club.frozed.frozedteams.managers.team;

import club.frozed.frozedteams.utils.chat.CC;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: FrozedTeams
 * Date: 11/09/2020 @ 18:43
 * Template by Elb1to
 */

@Getter
@Setter
public class Team {

    public static List<Team> teams = new ArrayList<>();

    // Team info
    private UUID uuid;
    private String name;
    private List<String> members;
    private String password;
    private String leader;

    // Team Options
    private boolean friendFire;

    public Team(String leader,UUID uuid,String name, String password) {
        this.leader = leader;
        this.uuid = uuid;
        this.name = name;
        this.password = password;
        this.members = new ArrayList<>();
        teams.add(this);
    }

    public void joinTeam(Player player){
        if (!members.isEmpty()){
            members.forEach(name ->{
                Player teamPlayer = Bukkit.getPlayer(name);
                if (teamPlayer != null || teamPlayer.isOnline()){
                    teamPlayer.sendMessage(CC.translate("&b" + player.getName() + " &fjoined to team"));
                }
            });
        }
        members.add(player.getName());
    }

    public boolean isLeader(Player player){
        return leader.equalsIgnoreCase(player.getName());
    }

    public static Team getTeamByName(String name) {
        return teams.stream().filter(team -> team.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public static Team getTeamByUUID(UUID uuid){
        return teams.stream().filter(team -> team.getUuid().equals(uuid)).findFirst().orElse(null);
    }

    public static Team getPlayerTeam(Player player){
        for (Team team : teams){
            if (team.getLeader().equalsIgnoreCase(player.getName()) || team.getMembers().contains(player.getName())){
                return  team;
            }
        }
        return null;
    }
}

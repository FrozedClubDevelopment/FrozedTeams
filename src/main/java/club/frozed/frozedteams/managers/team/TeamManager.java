package club.frozed.frozedteams.managers.team;

/**
 * Created by Ryzeon
 * Project: FrozedTeams
 * Date: 11/09/2020 @ 18:44
 * Template by Elb1to
 */

public class TeamManager {

    public void loadTeams(){

    }
    public void saveTeams(){

    }

    public boolean isExits(String team){
        return Team.teams.contains(Team.getTeamByName(team));
    }
}

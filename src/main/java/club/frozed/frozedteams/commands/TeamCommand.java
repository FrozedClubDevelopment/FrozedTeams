package club.frozed.frozedteams.commands;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.managers.team.Team;
import club.frozed.frozedteams.utils.chat.CC;
import club.frozed.frozedteams.utils.command.BaseCommand;
import club.frozed.frozedteams.utils.command.Command;
import club.frozed.frozedteams.utils.command.CommandArgs;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Created by Ryzeon
 * Project: FrozedTeams
 * Date: 11/09/2020 @ 18:58
 * Template by Elp1to
 */

public class TeamCommand extends BaseCommand {

    @Command(name = "team")
    @Override
    public void onCommand(CommandArgs command) {
        Player p = command.getPlayer();
        String[] args = command.getArgs();

        if (args.length == 0){
            p.sendMessage(CC.translate("&b/team create <name> <password>"));
            p.sendMessage(CC.translate("&b/team join <name> <password>"));
            p.sendMessage(CC.translate("&b/team promote <player>"));
            p.sendMessage(CC.translate("&b/team set HQ/rally"));
            p.sendMessage(CC.translate("&b/team friendlyfire"));
            return;
        }
        switch (args[0]){
            case "create":
                if (args[1] == null) return;

                if (FrozedTeams.getInstance().getTeamManager().isExits(args[1])){
                    p.sendMessage(CC.translate("&cThat team already exists"));
                    return;
                }
                if (args[2] == null){
                    p.sendMessage(CC.translate("&cPlease specific a password"));
                    return;
                }
                new Team(p.getName(), UUID.randomUUID(), args[1], args[2]);
                p.sendMessage(CC.translate("&bSuccessfully create team."));
                break;
            case "join":
                if (args[1] == null) return;

                if (!FrozedTeams.getInstance().getTeamManager().isExits(args[1])){
                    p.sendMessage(CC.translate("&cThat team don't exists"));
                    return;
                }
                if (args[2] == null){
                    p.sendMessage(CC.translate("&cPlease specific a password."));
                    return;
                }
                if (!args[2].equalsIgnoreCase(Team.getTeamByName(args[2]).getPassword())){
                    p.sendMessage(CC.translate("&cPassword isn't correct"));
                    return;
                }
                if (!Team.getTeamByName(args[1]).getMembers().isEmpty() || Team.getTeamByName(args[1]).getMembers().contains(p.getName())){
                    p.sendMessage(CC.translate("&cYou already in that team."));
                    return;
                }

                Team.getTeamByName(args[1]).joinTeam(p);
                break;
            case "promote":
                Team team = Team.getPlayerTeam(p);

                if (team == null) return;

                if (!team.isLeader(p)){
                    p.sendMessage(CC.translate("&cYou must be a leader."));
                    return;
                }

                Player targetPlayer = Bukkit.getPlayer(args[1]);

                if (targetPlayer != null){
                    team.setLeader(targetPlayer.getName());
                    team.getMembers().remove(targetPlayer.getName());
                    team.getMembers().add(p.getName());
                    // HACER EL MSSG DE PROMOTEASTE XDE
                }
                break;
            case "set":
                if (args[1] == null){
                    p.sendMessage(CC.translate("&b/team set HQ/rally"));
                    return;
                }
                switch (args[1]){
                    case "HQ":
                        break;
                    case "rally":
                        break;
                }
                break;
            case "friendlyfire":
                Team playerTeam = Team.getPlayerTeam(p);
                if (playerTeam == null) return;

                if (!playerTeam.isLeader(p)){
                    p.sendMessage(CC.translate("&cYou must be a leader."));
                    return;
                }

                String status = playerTeam.isFriendFire() ? "&cdisabled" : "&aenabled";
                playerTeam.setFriendFire(!playerTeam.isFriendFire());
                p.sendMessage(CC.translate("&bYou "+ status + " &bfriendlyfire"));
                break;
            default:
                p.sendMessage(CC.translate("&c/team create <name> <password>"));
                p.sendMessage(CC.translate("&c/team join <name> <password>"));
                p.sendMessage(CC.translate("&c/team promote <player>"));
                p.sendMessage(CC.translate("&c/team set HQ/rally"));
                p.sendMessage(CC.translate("&c/team friendlyfire"));
                break;
        }
    }
}

package club.frozed.frozedteams.commands;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.managers.tracker.PermanentTracker;
import club.frozed.frozedteams.managers.tracker.TemporalTracker;
import club.frozed.frozedteams.managers.tracker.Tracker;
import club.frozed.frozedteams.profile.Profile;
import club.frozed.frozedteams.profile.warp.PlayerWarp;
import club.frozed.frozedteams.utils.chat.ColorText;
import club.frozed.frozedteams.utils.command.Command;
import club.frozed.frozedteams.utils.command.CommandArgs;
import club.frozed.frozedteams.utils.configurations.ConfigFile;
import club.frozed.frozedteams.utils.location.LocationUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.stream.Collectors;

public class PlayerCommands {

    private FrozedTeams plugin = FrozedTeams.getInstance();
    private ConfigFile langConfig = plugin.getConfiguration("messages");

    @Command(name = "track")
    public void onTrackerCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (player.getWorld().getEnvironment() == World.Environment.NETHER && !plugin.getConfiguration("config").getBoolean("NETHER-TRACKER")) {
            player.sendMessage(langConfig.getString("CANT-NETHER-TRACK"));
        } else if (args.length == 0) {
            player.sendMessage(ChatColor.RED + "Usage: /track <all|player>");
        } else {
            Block center = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
            Tracker tracker = null;
            if (center.getType() == Material.DIAMOND_BLOCK) {
                tracker = new PermanentTracker(player);
            } else if (center.getType() == Material.OBSIDIAN) {
                tracker = new TemporalTracker(player);
            }
            if (tracker == null) {
                player.sendMessage(langConfig.getString("INVALID-TRACK-COMPASS"));
            } else if (args[0].equalsIgnoreCase("all")) {
                tracker.trackAll();
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if (target != null && player.canSee(target)) {
                    tracker.trackPlayer(target);
                } else {
                    player.sendMessage(langConfig.getString("PLAYER-NOT-FOUND").replace("{PLAYER}", args[0]));
                }
            }
        }
    }

    @Command(name = "warp", aliases = {"go"})
    public void onWarpCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length < 1) {
            player.sendMessage(ColorText.translate("&7&m" + StringUtils.repeat("-", 48)));
            player.sendMessage(ColorText.translate("&6Warp Commands:"));
            player.sendMessage(ColorText.translate(" &e/warp set (warpname)"));
            player.sendMessage(ColorText.translate(" &e/warp delete (warpname)"));
            player.sendMessage(ColorText.translate(" &e/warp (warpname)"));
            player.sendMessage(ColorText.translate(" &e/warp list"));
            player.sendMessage(ColorText.translate("&7&m" + StringUtils.repeat("-", 48)));
        } else {
            Profile profile = Profile.get(player);
            if (profile.getWarps().isEmpty()) {
                player.sendMessage(langConfig.getString("NO-WARPS"));
            } else {
                PlayerWarp warp = profile.getWarpByName(args[0]);

                if (warp == null) {
                    player.sendMessage(langConfig.getString("WARP-NOT-FOUND").replace("{WARP}", args[0]));
                } else {
                    if (!player.teleport(warp.getWarpLocation())) {
                        player.sendMessage(langConfig.getString("COULD-NOT-TELEPORT"));
                        return;
                    }

                    player.sendMessage(langConfig.getString("TELEPORTING-WARP").replace("{WARP}", warp.getName()));
                }
            }
        }
    }

    @Command(name = "warp.set")
    public void onWarpSetCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length < 1) {
            player.sendMessage(ColorText.translate("&cUsage: /warp set (warpname)"));
        } else {
            Profile profile = Profile.get(player);
            PlayerWarp warp = profile.getWarpByName(args[0]);

            if (warp != null) {
                player.sendMessage(langConfig.getString("WARP-ALREADY-CREATED").replace("{WARP}", warp.getName()));
            } else {
                warp = new PlayerWarp(args[0], LocationUtils.toString(player.getLocation()));
                profile.getWarps().add(warp);

                player.sendMessage(langConfig.getString("WARP-CREATED").replace("{WARP}", warp.getName()));
            }
        }
    }

    @Command(name = "warp.delete")
    public void onWarpDeleteCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        if (args.length < 1) {
            player.sendMessage(ColorText.translate("&cUsage: /warp delete (warpname)"));
        } else {
            Profile profile = Profile.get(player);
            PlayerWarp warp = profile.getWarpByName(args[0]);

            if (warp == null) {
                player.sendMessage(langConfig.getString("WARP-NOT-FOUND").replace("{WARP}", args[0]));
            } else {
                player.sendMessage(langConfig.getString("WARP-DELETED").replace("{WARP}", warp.getName()));
                profile.getWarps().remove(warp);
            }
        }
    }

    @Command(name = "warp.list")
    public void onWarpListCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();

        Profile profile = Profile.get(player);
        if (profile.getWarps().isEmpty()) {
            player.sendMessage(langConfig.getString("NO-WARPS"));
        } else {
            player.sendMessage(ColorText.translate("&7&m" + StringUtils.repeat("-", 48)));
            player.sendMessage(ColorText.translate("&6Warp List: &7(" + profile.getWarps().size() + '/' + 50 + ')'));
            player.sendMessage("");
            player.sendMessage(ColorText.translate("&e" + StringUtils.join(profile.getWarps().stream().map(PlayerWarp::getName).collect(Collectors.toList()), ", ")));
            player.sendMessage(ColorText.translate("&7&m" + StringUtils.repeat("-", 48)));
        }
    }
}

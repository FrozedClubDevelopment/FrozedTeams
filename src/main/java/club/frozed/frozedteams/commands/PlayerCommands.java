package club.frozed.frozedteams.commands;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.data.PlayerData;
import club.frozed.frozedteams.managers.PlayerDataManager;
import club.frozed.frozedteams.managers.tracker.PermanentTracker;
import club.frozed.frozedteams.managers.tracker.TemporalTracker;
import club.frozed.frozedteams.managers.tracker.Tracker;
import club.frozed.frozedteams.profile.Profile;
import club.frozed.frozedteams.profile.warp.PlayerWarp;
import club.frozed.frozedteams.utils.chat.CC;
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
            player.sendMessage(CC.translate("&7&m" + StringUtils.repeat("-", 48)));
            player.sendMessage(CC.translate("&6Warp Commands:"));
            player.sendMessage(CC.translate(" &e/warp set (warpname)"));
            player.sendMessage(CC.translate(" &e/warp delete (warpname)"));
            player.sendMessage(CC.translate(" &e/warp (warpname)"));
            player.sendMessage(CC.translate(" &e/warp list"));
            player.sendMessage(CC.translate("&7&m" + StringUtils.repeat("-", 48)));
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
            player.sendMessage(CC.translate("&cUsage: /warp set (warpname)"));
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
            player.sendMessage(CC.translate("&cUsage: /warp delete (warpname)"));
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
            player.sendMessage(CC.translate("&7&m" + StringUtils.repeat("-", 48)));
            player.sendMessage(CC.translate("&6Warp List: &7(" + profile.getWarps().size() + '/' + 50 + ')'));
            player.sendMessage("");
            player.sendMessage(CC.translate("&e" + StringUtils.join(profile.getWarps().stream().map(PlayerWarp::getName).collect(Collectors.toList()), ", ")));
            player.sendMessage(CC.translate("&7&m" + StringUtils.repeat("-", 48)));
        }
    }

    @Command(name = "buy")
    public void onBuyCommand(CommandArgs commandArgs) {

    }

    @Command(name = "sell")
    public void onSellCommand(CommandArgs commandArgs) {

    }

    @Command(name = "debug")
    public void onDebugCommand(CommandArgs commandArgs) {
        Player player = commandArgs.getPlayer();
        String[] args = commandArgs.getArgs();

        player.sendMessage(CC.translate("&bPlayer debug -> &f" + player.getName()));
        player.sendMessage(CC.translate("&b - getByUUID -> " + PlayerDataManager.getInstance().getByUUID(player.getUniqueId())));
        player.sendMessage(CC.translate("&b - getState -> " + new PlayerData(player.getUniqueId()).getState()));
        player.sendMessage(CC.translate("&b - getName -> " + new PlayerData(player.getUniqueId()).getName()));
        player.sendMessage(CC.translate("&b - getUuid -> " + new PlayerData(player.getUniqueId()).getUuid()));
        player.sendMessage(CC.translate("&b - getBalance -> " + new PlayerData(player.getUniqueId()).getBalance()));

    }
}

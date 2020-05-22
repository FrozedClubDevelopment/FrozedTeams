package club.frozed.frozedteams.commands;

import club.frozed.frozedteams.managers.tracker.PermanentTracker;
import club.frozed.frozedteams.managers.tracker.TemporalTracker;
import club.frozed.frozedteams.managers.tracker.Track;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrackCommand implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        } else {
            Player player = (Player) sender;
            if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
                player.sendMessage(ChatColor.RED + "You cannot track in the nether!");
                return true;
            } else if (args.length == 0) {
                player.sendMessage(ChatColor.RED + "/track <all|player>");
                return true;
            } else {
                Block center = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
                Track tracker = null;
                if (center.getType() == Material.DIAMOND_BLOCK) {
                    tracker = new PermanentTracker(player);
                } else if (center.getType() == Material.OBSIDIAN) {
                    tracker = new TemporalTracker(player);
                }
                if (tracker == null) {
                    player.sendMessage(ChatColor.RED + "Not a valid tracking compass.");
                    return true;
                } else if (args[0].equalsIgnoreCase("all")) {
                    tracker.trackAll();
                    return true;
                } else {
                    Player target = Bukkit.getPlayer(args[0]);
                    if (target != null && player.canSee(target)) {
                        tracker.trackPlayer(target);
                    } else {
                        player.sendMessage(ChatColor.RED + "Player '" + args[0] + "' not found.");
                    }
                    return true;
                }
            }
        }
    }
}

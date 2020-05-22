package club.frozed.frozedteams.managers.tracker;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.utils.chat.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.TreeSet;

public class PermanentTracker implements Track {

    private Player player;
    private Block middleBlock;

    public PermanentTracker(Player player) {
        this.player = player;
        this.middleBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
    }

    public boolean onBlock(Player target, TrackerDirection direction) {
        int length = this.countDistance(direction) * 25;
        int playerX = target.getLocation().getBlockX();
        int playerZ = target.getLocation().getBlockZ();
        return (direction == TrackerDirection.NORTH && playerZ <= this.middleBlock.getZ() && playerZ >= this.middleBlock.getZ() - length) ||
                (direction == TrackerDirection.EAST && playerX >= this.middleBlock.getX() && playerX <= this.middleBlock.getX() + length) ||
                (direction == TrackerDirection.SOUTH && playerZ >= this.middleBlock.getZ() && playerZ <= this.middleBlock.getZ() + length) ||
                (direction == TrackerDirection.WEST && playerX <= this.middleBlock.getX() && playerX >= this.middleBlock.getX() - length);
    }

    public void trackPlayer(Player target) {
        int north = this.countDistance(TrackerDirection.NORTH) * 25;
        int east = this.countDistance(TrackerDirection.EAST) * 25;
        int south = this.countDistance(TrackerDirection.SOUTH) * 25;
        int west = this.countDistance(TrackerDirection.WEST) * 25;
        if ((north == 0 && east == 0 && south == 0 && west == 0) || this.middleBlock.getType() != Material.DIAMOND_BLOCK) {
            this.player.sendMessage(ChatColor.RED + "Not a valid tracking compass.");
            return;
        }
        for (TrackerDirection direction : TrackerDirection.values()) {
            int length = this.countDistance(direction) * 25;
            if (length > 0) {
                if (this.onBlock(target, direction)) {
                    this.player.sendMessage(ChatColor.GREEN + target.getName() + " is within " + length + " blocks " + direction + " of here.");
                } else {
                    this.player.sendMessage(ChatColor.RED + target.getName() + " is NOT within " + length + " blocks " + direction + " of here.");
                }
            }
        }
    }

    public void trackAll() {
        int north = this.countDistance(TrackerDirection.NORTH) * 25;
        int east = this.countDistance(TrackerDirection.EAST) * 25;
        int south = this.countDistance(TrackerDirection.SOUTH) * 25;
        int west = this.countDistance(TrackerDirection.WEST) * 25;
        if (north == 0 && east == 0 && south == 0 && west == 0) {
            this.player.sendMessage(ChatColor.RED + "Not a valid tracking compass.");
            return;
        }
        for (TrackerDirection direction : TrackerDirection.values()) {
            int length = this.countDistance(direction) * 25;
            if (length != 0) {
                Set<String> players = new TreeSet<>();
                for (Player player : FrozedTeams.getInstance().getServer().getOnlinePlayers()) {
                    if (this.player.canSee(player) && (this.onBlock(player, direction) & !player.equals(this.player))) {
                        players.add(player.getDisplayName());
                    }
                }
                FancyMessage message = new FancyMessage(direction + " (" + length + "): ").color(ChatColor.DARK_AQUA);
                int i = 0;
                for (String str : players) {
                    if (i == players.size() - 1) {
                        message.then(str).color(ChatColor.GRAY).tooltip(ChatColor.GREEN + "Click here to track " + ChatColor.RESET + str + ChatColor.GREEN + ".").command("/track " + ChatColor.stripColor(str));
                    } else {
                        message.then(str).color(ChatColor.GRAY).tooltip(ChatColor.GREEN + "Click here to track " + ChatColor.RESET + str + ChatColor.GREEN + ".").command("/track " + ChatColor.stripColor(str)).then(", ");
                    }
                    ++i;
                }
                message.send(this.player);
            }
        }
    }

    public int countDistance(TrackerDirection direction) {
        int length = 0;
        int i = 1;
        while (i < 10000) {
            Block next = this.middleBlock.getRelative(BlockFace.valueOf(direction.toString().toUpperCase()), i);
            if (next.getType() == Material.OBSIDIAN) {
                ++length;
                ++i;
            } else {
                if (next.getType() == Material.GOLD_BLOCK) {
                    ++length;
                    break;
                }
                length = 0;
                break;
            }
        }
        return length;
    }
}
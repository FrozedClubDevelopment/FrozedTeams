package club.frozed.frozedteams.managers.tracker;

import club.frozed.frozedteams.FrozedTeams;
import club.frozed.frozedteams.enums.TrackerDirection;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

public class TemporalTracker implements Tracker {

    private Player player;
    private Block middleBlock;

    public TemporalTracker(Player player) {
        this.player = player;
        this.middleBlock = player.getLocation().getBlock().getRelative(BlockFace.DOWN);
    }

    public boolean onTracker(Player target, TrackerDirection direction, int length) {
        int playerX = target.getLocation().getBlockX();
        int playerZ = target.getLocation().getBlockZ();
        if (direction == TrackerDirection.NORTH && playerZ <= this.middleBlock.getZ() && playerZ >= this.middleBlock.getZ() - length) {
            return true;
        } else if (direction == TrackerDirection.EAST && playerX >= this.middleBlock.getX() && playerX <= this.middleBlock.getX() + length) {
            return true;
        } else if (direction == TrackerDirection.SOUTH && playerZ >= this.middleBlock.getZ() && playerZ <= this.middleBlock.getZ() + length) {
            return true;
        } else {
            return direction == TrackerDirection.WEST && playerX <= this.middleBlock.getX() && playerX >= this.middleBlock.getX() - length;
        }
    }

    public int countDistance(TrackerDirection direction, boolean blocks) {
        Set<Block> toDelete = new HashSet<>();
        int length = 0;
        Block block;
        for (int i = 1; i < 10000; ++i) {
            block = this.middleBlock.getRelative(BlockFace.valueOf(direction.toString().toUpperCase()), i);
            if (block.getType() != Material.COBBLESTONE) {
                if (block.getType() == Material.STONE) {
                    ++length;
                    toDelete.add(block);
                    toDelete.add(this.middleBlock);
                } else {
                    length = 0;
                    toDelete.clear();
                }
                break;
            }
            ++length;
            toDelete.add(block);
        }
        if (blocks) {
            for (Block value : toDelete) {
                block = value;
                block.getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getTypeId());
                block.setType(Material.AIR);
            }
        }
        return length;
    }


    @Override
    public void trackPlayer(Player target) {
        int north = this.countDistance(TrackerDirection.NORTH, true) * 25;
        int east = this.countDistance(TrackerDirection.EAST, true) * 25;
        int south = this.countDistance(TrackerDirection.SOUTH, true) * 25;
        int west = this.countDistance(TrackerDirection.WEST, true) * 25;
        if (north == 0 && east == 0 && south == 0 && west == 0) {
            player.sendMessage(FrozedTeams.getInstance().getConfiguration("messages").getString("INVALID-TRACK-COMPASS"));
            return;
        }
        for (TrackerDirection direction : TrackerDirection.values()) {
            int length = 0;
            if (direction == TrackerDirection.NORTH) {
                length = north;
            }
            if (direction == TrackerDirection.EAST) {
                length = east;
            }
            if (direction == TrackerDirection.SOUTH) {
                length = south;
            }
            if (direction == TrackerDirection.WEST) {
                length = west;
            }
            if (length <= 0) continue;
            if (this.onTracker(target, direction, length)) {
                player.sendMessage(FrozedTeams.getInstance().getConfiguration("messages").getString("TRACK-WITHIN-FORMAT").replace("{PLAYER}", target.getName()).replace("{BLOCKS}", String.valueOf(length)).replace("{DIRECTION}", direction.name()));
                continue;
            }
            player.sendMessage(FrozedTeams.getInstance().getConfiguration("messages").getString("TRACK-NOT-WITHIN-FORMAT").replace("{PLAYER}", target.getName()).replace("{BLOCKS}", String.valueOf(length)).replace("{DIRECTION}", direction.name()));
        }
    }

    @Override
    public void trackAll() {
        int north = this.countDistance(TrackerDirection.NORTH, false) * 25;
        int east = this.countDistance(TrackerDirection.EAST, false) * 25;
        int south = this.countDistance(TrackerDirection.SOUTH, false) * 25;
        int west = this.countDistance(TrackerDirection.WEST, false) * 25;
        if (north == 0 && east == 0 && south == 0 && west == 0) {
            player.sendMessage(FrozedTeams.getInstance().getConfiguration("messages").getString("INVALID-TRACK-COMPASS"));
        } else {
            player.sendMessage(FrozedTeams.getInstance().getConfiguration("messages").getString("CANT-TRACK-ALL"));
        }
    }
}

package club.frozed.frozedteams.listeners;

import club.frozed.frozedteams.utils.region.Cuboid;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_AIR) {
            if (event.getItem() != null) {
                if (event.getItem().getType() == Material.BLAZE_ROD) {
                    Location loc1 = new Location(event.getPlayer().getLocation().getWorld(),
                            event.getPlayer().getLocation().getX() + 2,
                            event.getPlayer().getLocation().getY() + 1,
                            event.getPlayer().getLocation().getZ() + 2);

                    Location loc2 = new Location(event.getPlayer().getLocation().getWorld(),
                            event.getPlayer().getLocation().getX() - 2,
                            event.getPlayer().getLocation().getY(),
                            event.getPlayer().getLocation().getZ() - 2);

                    Cuboid cuboid = new Cuboid(loc1, loc2);
                    for (Block block : cuboid) block.setType(Material.GLASS);
                }
            }
        }
    }
}

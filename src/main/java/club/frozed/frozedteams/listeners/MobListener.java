package club.frozed.frozedteams.listeners;

import club.frozed.frozedteams.FrozedTeams;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

public class MobListener implements Listener {

    /*@EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (entity.getWorld() == LocationManager.getSpawn().getWorld()) {
            if (LocationManager.getSpawn().distance(entity.getLocation()) <= FrozedTeams.getInstance().getConfig().getInt("Settings.protect-size")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent event) {
        Entity entity = event.getEntity();
        if (entity.getWorld() == LocationManager.getSpawn().getWorld()) {
            if (LocationManager.getSpawn().distance(entity.getLocation()) <= FrozedTeams.getInstance().getConfig().getInt("Settings.protect-size")) {
                event.setCancelled(true);
            }
        }
    }*/
}

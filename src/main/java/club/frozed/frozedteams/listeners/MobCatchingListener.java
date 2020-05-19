package club.frozed.frozedteams.listeners;

import club.frozed.frozedteams.utils.chat.Messages;
import org.bukkit.Material;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class MobCatchingListener implements Listener {

    @Deprecated
    @EventHandler
    public void onMobCatching(EntityDamageByEntityEvent event) {
        /*
         * Get the player damaging the mob, if the player has an egg, get the mob being damaged
         * If the player EXP Level is bigger than 25, allow the player to catch the mob
         * If not, cancel the mob catching event.
         */
        if (event.getDamager() instanceof Egg && event.getEntity() instanceof LivingEntity) {
            Player shooter = (Player) ((Egg) event.getDamager()).getShooter();
            if (shooter.getLevel() >= 25) {
                Entity eventEgg = event.getDamager();
                Entity eventEntity = event.getEntity();
                if (eventEntity instanceof Player) return;
                /*
                 * If the player is not 6 blocks away from the entity
                 * cancel catching the mob and send a message.
                 */
                if (eventEntity.getLocation().distance(shooter.getLocation()) <= 5) {
                    shooter.sendMessage(Messages.CATCH_FAILED);
                    return;
                }
                int entityId = 0;
                String entityName = "";
                switch (event.getEntityType()) {
                    case PIG:
                        entityName = "Pig";
                        entityId = eventEntity.getEntityId();
                        break;
                    case CHICKEN:
                        entityName = "Chicken";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SHEEP:
                        entityName = "Sheep";
                        entityId = eventEntity.getEntityId();
                        break;
                    case HORSE:
                        entityName = "Horse";
                        entityId = eventEntity.getEntityId();
                        break;
                    case MUSHROOM_COW:
                        entityName = "Mooshroom";
                        entityId = eventEntity.getEntityId();
                        break;
                    case WOLF:
                        entityName = "Wolf";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SQUID:
                        entityName = "Squid";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SKELETON:
                        entityName = "Skeleton";
                        entityId = eventEntity.getEntityId();
                        break;
                    case ZOMBIE:
                        entityName = "Zombie";
                        entityId = eventEntity.getEntityId();
                        break;
                    case PIG_ZOMBIE:
                        entityName = "Zombie Pigman";
                        entityId = eventEntity.getEntityId();
                        break;
                    case CREEPER:
                        entityName = "Creeper";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SPIDER:
                        entityName = "Spider";
                        entityId = eventEntity.getEntityId();
                        break;
                    case CAVE_SPIDER:
                        entityName = "Cave Spider";
                        entityId = eventEntity.getEntityId();
                        break;
                    case ENDERMAN:
                        entityName = "Enderman";
                        entityId = eventEntity.getEntityId();
                        break;
                    case MAGMA_CUBE:
                        entityName = "Magma Cube";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SLIME:
                        entityName = "Slime";
                        entityId = eventEntity.getEntityId();
                        break;
                    case OCELOT:
                        entityName = "Ocelot";
                        entityId = eventEntity.getEntityId();
                        break;
                    case BLAZE:
                        entityName = "Blaze";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SILVERFISH:
                        entityName = "Silverfish";
                        entityId = eventEntity.getEntityId();
                        break;
                    case SNOWMAN:
                        entityName = "Snowman";
                        entityId = eventEntity.getEntityId();
                        break;
                    case COW:
                        entityName = "Cow";
                        entityId = eventEntity.getEntityId();
                        break;
                }
                shooter.setLevel(shooter.getLevel() - 25);
                event.getEntity().remove();
                event.getEntity().getWorld().dropItem(eventEgg.getLocation(), new ItemStack(Material.MONSTER_EGG, 1, (short) entityId));
                String message = Messages.CATCH_SUCCEEDED;
                message = message.replace("$mob", entityName);
                shooter.sendMessage(message);
            }
        }
    }

}

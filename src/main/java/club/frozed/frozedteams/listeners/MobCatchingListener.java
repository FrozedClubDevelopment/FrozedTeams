package club.frozed.frozedteams.listeners;

import club.frozed.frozedteams.utils.chat.Messages;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerEggThrowEvent;
import org.bukkit.inventory.ItemStack;

public class MobCatchingListener implements Listener {

    @Deprecated
    @EventHandler
    public void onMobCatching(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Egg && event.getEntity() instanceof LivingEntity) {
            Player shooter = (Player) ((Egg) event.getDamager()).getShooter();
            if (shooter.getLevel() < 25) {
                shooter.sendMessage(Messages.CATCH_NOT_ENOUGH_XP);
                return;
            }
            if (shooter.getLevel() >= 25) {
                Entity eventEgg = event.getDamager();
                Entity eventEntity = event.getEntity();
                if (eventEntity instanceof Player) return;
                if (eventEntity.getLocation().distance(shooter.getLocation()) <= 5) {
                    shooter.sendMessage(Messages.CATCH_NOT_TOO_FAR);
                    return;
                }
                String entityName;
                int entityId = 0;
                switch (event.getEntityType()) {
                    case PIG:
                        entityName = "Pig";
                        entityId = EntityType.PIG.getTypeId();
                        break;
                    case CHICKEN:
                        entityName = "Chicken";
                        entityId = EntityType.CHICKEN.getTypeId();
                        break;
                    case SHEEP:
                        entityName = "Sheep";
                        entityId = EntityType.SHEEP.getTypeId();
                        break;
                    case HORSE:
                        entityName = "Horse";
                        entityId = EntityType.HORSE.getTypeId();
                        break;
                    case MUSHROOM_COW:
                        entityName = "Mooshroom Cow";
                        entityId = EntityType.MUSHROOM_COW.getTypeId();
                        break;
                    case WOLF:
                        entityName = "Wolf";
                        entityId = EntityType.WOLF.getTypeId();
                        break;
                    case SQUID:
                        entityName = "Squid";
                        entityId = EntityType.SQUID.getTypeId();
                        break;
                    case SKELETON:
                        entityName = "Skeleton";
                        entityId = EntityType.SKELETON.getTypeId();
                        break;
                    case ZOMBIE:
                        entityName = "Zombie";
                        entityId = EntityType.ZOMBIE.getTypeId();
                        break;
                    case PIG_ZOMBIE:
                        entityName = "Zombie Pigman";
                        entityId = EntityType.PIG_ZOMBIE.getTypeId();
                        break;
                    case CREEPER:
                        entityName = "Creeper";
                        entityId = EntityType.CREEPER.getTypeId();
                        break;
                    case SPIDER:
                        entityName = "Spider";
                        entityId = EntityType.SPIDER.getTypeId();
                        break;
                    case CAVE_SPIDER:
                        entityName = "Cave Spider";
                        entityId = EntityType.CAVE_SPIDER.getTypeId();
                        break;
                    case ENDERMAN:
                        entityName = "Enderman";
                        entityId = EntityType.ENDERMAN.getTypeId();
                        break;
                    case MAGMA_CUBE:
                        entityName = "Magma Cube";
                        entityId = EntityType.MAGMA_CUBE.getTypeId();
                        break;
                    case SLIME:
                        entityName = "Slime";
                        entityId = EntityType.SLIME.getTypeId();
                        break;
                    case OCELOT:
                        entityName = "Ocelot";
                        entityId = EntityType.OCELOT.getTypeId();
                        break;
                    case BLAZE:
                        entityName = "Blaze";
                        entityId = EntityType.BLAZE.getTypeId();
                        break;
                    case SILVERFISH:
                        entityName = "Silverfish";
                        entityId = EntityType.SILVERFISH.getTypeId();
                        break;
                    case SNOWMAN:
                        entityName = "Snowman";
                        entityId = EntityType.SNOWMAN.getTypeId();
                        break;
                    case COW:
                        entityName = "Cow";
                        entityId = EntityType.COW.getTypeId();
                        break;
                    default:
                        entityName = "";
                        break;
                }
                shooter.setLevel(shooter.getLevel() - 25);
                event.getEntity().remove();
                event.getEntity().getWorld().dropItem(eventEgg.getLocation(), new ItemStack(Material.MONSTER_EGG, 1, (short) entityId));
                String message = Messages.CATCH_SUCCEEDED;
                message = message.replace("<entity>", entityName);
                shooter.sendMessage(message);
            }
        }
    }

    @EventHandler
    public void onEggShoot(PlayerEggThrowEvent eggThrowEvent) {
        eggThrowEvent.setHatching(false);
    }
}

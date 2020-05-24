package club.frozed.frozedteams.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class SalvagingListener implements Listener {

    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction() == null || player.getItemInHand().getType() == null || event.getClickedBlock() == null)
            return;
        Material block = event.getClickedBlock().getType();
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (block == Material.DIAMOND_BLOCK || block == Material.IRON_BLOCK || block == Material.GOLD_BLOCK) {
                if (isSalvageFurnace(event.getClickedBlock())) {
                    if (isBlockMaterial(block)) {
                        event.setCancelled(true);
                        player.updateInventory();
                        if (block == Material.DIAMOND_BLOCK) {
                            switch (event.getPlayer().getItemInHand().getType()) {
                                case DIAMOND_HELMET:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 5));
                                    break;
                                case DIAMOND_CHESTPLATE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 8));
                                    break;
                                case DIAMOND_LEGGINGS:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 7));
                                    break;
                                case DIAMOND_BOOTS:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 4));
                                    break;
                                case DIAMOND_SWORD:
                                case DIAMOND_HOE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 2));
                                    break;
                                case DIAMOND_PICKAXE:
                                case DIAMOND_AXE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 3));
                                    break;
                                case DIAMOND_SPADE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.DIAMOND, 1));
                                    break;
                                default:
                                    break;
                            }
                        }

                        if (block == Material.GOLD_BLOCK) {
                            switch (event.getPlayer().getItemInHand().getType()) {
                                case GOLD_HELMET:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 5));
                                    break;
                                case GOLD_CHESTPLATE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 8));
                                    break;
                                case GOLD_LEGGINGS:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 7));
                                    break;
                                case GOLD_BOOTS:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 4));
                                    break;
                                case GOLD_SWORD:
                                case GOLD_HOE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 2));
                                    break;
                                case GOLD_PICKAXE:
                                case GOLD_AXE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 3));
                                    break;
                                case GOLD_SPADE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.GOLD_INGOT, 1));
                                    break;
                                default:
                                    break;
                            }
                        }

                        if (block == Material.IRON_BLOCK) {
                            switch (event.getPlayer().getItemInHand().getType()) {
                                case IRON_HELMET:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 5));
                                    break;
                                case IRON_CHESTPLATE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 8));
                                    break;
                                case IRON_LEGGINGS:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 7));
                                    break;
                                case IRON_BOOTS:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 4));
                                    break;
                                case IRON_SWORD:
                                case IRON_HOE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 2));
                                    break;
                                case IRON_PICKAXE:
                                case IRON_AXE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 3));
                                    break;
                                case IRON_SPADE:
                                    player.setItemInHand(null);
                                    player.getWorld().dropItem(event.getClickedBlock().getLocation(), new ItemStack(Material.IRON_INGOT, 1));
                                    break;
                                default:
                                    break;
                            }
                        }
                        player.updateInventory();
                    }
                }
            }
        }
    }

    public boolean isBlockMaterial(Material block) {
        return block.toString().startsWith("DIAMOND") || block.toString().startsWith("GOLD") || block.toString().startsWith("IRON");
    }

    public boolean isSalvageFurnace(Block block) {
        for (BlockFace face : BlockFace.values()) {
            if (block.getRelative(face).getType() == Material.FURNACE) {
                return true;
            }
        }
        return false;
    }
}

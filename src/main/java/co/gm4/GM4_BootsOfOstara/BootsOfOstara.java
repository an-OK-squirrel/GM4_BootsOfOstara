package co.gm4.GM4_BootsOfOstara;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class BootsOfOstara extends JavaPlugin implements Listener{

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {

        Player player = e.getPlayer();

        // Check for being on ground
        if (!player.isOnGround() || player.isFlying()) return;

        ItemStack boots = player.getInventory().getBoots();
        if (!(boots instanceof ItemStack)) return;


        // Is it a pair of magic boots?
        if (boots.getType() != Material.LEATHER_BOOTS) return;
        if (boots.getItemMeta().getLore().size() == 0) return;
        if (!boots.getItemMeta().getLore().get(0).equals("Brings Abundance Beneath You!")) return;

        Block feetBlock = player.getLocation().getBlock();
        Block groundBlock = feetBlock.getRelative(BlockFace.DOWN);

        if (!(feetBlock.getType() == Material.AIR)) return;

        ItemStack offhand = player.getInventory().getItemInOffHand();
        if (!(offhand instanceof ItemStack)) return;

        if (groundBlock.getType() == Material.SOIL) {
            switch (offhand.getType()) {

                case CARROT_ITEM:
                    feetBlock.setType(Material.CARROT);
                    decreaseItemSize(offhand, player.getInventory());
                    break;

                case POTATO_ITEM:
                    feetBlock.setType(Material.POTATO);
                    decreaseItemSize(offhand, player.getInventory());
                    break;

                case SEEDS:
                    feetBlock.setType(Material.CROPS);
                    decreaseItemSize(offhand, player.getInventory());
                    break;

                case BEETROOT_SEEDS:
                    feetBlock.setType(Material.BEETROOT_BLOCK);
                    decreaseItemSize(offhand, player.getInventory());
                    break;

            }
        }
    }

    public void decreaseItemSize(ItemStack stack, Inventory inventory) {
        if (stack.getAmount() == 1) {
            inventory.clear(40);
            return;
        }
        stack.setAmount(stack.getAmount() - 1);
    }

}

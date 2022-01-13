package pl.joagger.brusheros.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.joagger.brusheros.BrushItem;
import pl.joagger.brusheros.BrushPluginConfiguration;

public class BrushKeepListener implements Listener {

    private static final String PERMISSION = "brusheros.drop";

    private final BrushPluginConfiguration configuration;

    public BrushKeepListener(BrushPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        if (event.getPlayer().hasPermission(PERMISSION)) {
            return;
        }

        BrushItem item = configuration.findByItemStack(event.getItemDrop().getItemStack());
        if (item != null) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    private void onItemClickMove(InventoryClickEvent event) {
        if (event.getWhoClicked().hasPermission(PERMISSION)) {
            return;
        }

        if (isForbidden(event.getClickedInventory(), event.getWhoClicked().getInventory())) {
            if (configuration.findByItemStack(event.getCursor()) != null
                    || configuration.findByItemStack(event.getCurrentItem()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onItemClickMove(InventoryDragEvent event) {
        if (event.getWhoClicked().hasPermission(PERMISSION)) {
            return;
        }

        if (isForbidden(event.getInventory(), event.getWhoClicked().getInventory())) {
            if (configuration.findByItemStack(event.getCursor()) != null ||
                    configuration.findByItemStack(event.getOldCursor()) != null) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    private void onHotbarMove(InventoryClickEvent event) {
        if (event.getWhoClicked().hasPermission(PERMISSION)) {
            return;
        }

        if (event.getAction() != InventoryAction.HOTBAR_MOVE_AND_READD &&
                event.getAction() != InventoryAction.HOTBAR_SWAP) {
            return;
        }

        if (isForbidden(event.getClickedInventory(), event.getWhoClicked().getInventory())) {
            ItemStack itemStack = event.getWhoClicked().getInventory().getItem(event.getHotbarButton());
            if (itemStack != null && configuration.findByItemStack(itemStack) != null) {
                event.setCancelled(true);
            }
        }
    }

    private boolean isForbidden(Inventory source, Inventory destination) {
        if (destination.getType() == InventoryType.ENDER_CHEST) {
            return false;
        }

        return source != destination;
    }
}

package pl.joagger.brusheros.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.Inventory;
import pl.joagger.brusheros.BrushItem;
import pl.joagger.brusheros.BrushPluginConfiguration;

public class BrushKeepListener implements Listener {
    private final BrushPluginConfiguration configuration;

    public BrushKeepListener(BrushPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @EventHandler
    private void onDrop(PlayerDropItemEvent event) {
        BrushItem item = configuration.findByItemStack(event.getItemDrop().getItemStack());
        if (item != null && !event.getPlayer().hasPermission("brusheros.drop")) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    private void onItemClickMove(InventoryClickEvent event) {
        if (!isAllowed(event.getClickedInventory(), event.getWhoClicked().getInventory()))
            if (configuration.findByItemStack(event.getCursor()) != null || configuration.findByItemStack(event.getCurrentItem()) != null)
                event.setCancelled(true);
    }

    private boolean isAllowed(Inventory source, Inventory destination) {
        if (destination.getType() == InventoryType.ENDER_CHEST)
            return true;

        return source == destination;
    }
}

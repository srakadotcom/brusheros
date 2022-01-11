package pl.joagger.brusheros.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.joagger.brusheros.ClickableInventory;

public class InventoryListener implements Listener {
    @EventHandler
    private void onClick(InventoryClickEvent event) {
        if(event.getClickedInventory() == null)
             return;

        if(event.getClickedInventory().getHolder() instanceof ClickableInventory) {
            event.setCancelled(true);
            ((ClickableInventory) event.getClickedInventory().getHolder()).onClick(event);
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent event) {
        if(event.getInventory().getHolder() instanceof ClickableInventory)
            ((ClickableInventory) event.getInventory().getHolder()).onClose(event);
    }
}

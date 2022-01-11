package pl.joagger.brusheros;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryHolder;

public interface ClickableInventory extends InventoryHolder {
    void onClick(InventoryClickEvent event);

    void onClose(InventoryCloseEvent event);
}

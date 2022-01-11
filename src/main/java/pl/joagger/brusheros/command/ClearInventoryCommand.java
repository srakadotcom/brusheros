package pl.joagger.brusheros.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.joagger.brusheros.BrushPluginConfiguration;
import pl.joagger.brusheros.ClickableInventory;

import java.util.List;

public final class ClearInventoryCommand implements CommandExecutor {
    private final BrushPluginConfiguration configuration;

    public ClearInventoryCommand(BrushPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("Ta komenda jest dostepna tylko dla graczy.");
            return true;
        }

        player.openInventory(new ClearInventoryConfirm().getInventory());
        return true;
    }

    public class ClearInventoryConfirm implements ClickableInventory {
        private static final ItemStack CONFIRM_ITEM = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.LIME.getData());
        private static final ItemStack DECLINE_ITEM = new ItemStack(Material.STAINED_GLASS_PANE, 1, DyeColor.RED.getData());
        private static final ItemStack INFO_ITEM = new ItemStack(Material.PAPER);

        static {
            {
                ItemMeta confirm = CONFIRM_ITEM.getItemMeta();
                confirm.setDisplayName(ChatColor.GREEN + "Potwierdz wyczyszczenie ekwipunku");
                confirm.setLore(List.of(ChatColor.DARK_GRAY + "\u00bb " + ChatColor.GRAY + "Kliknij aby wyczyscic swoj ekwipunek.",
                        ChatColor.GRAY + "Czyszczenie nie obejmuje zakupionych brushow, oraz zbrojki."));
                CONFIRM_ITEM.setItemMeta(confirm);
            }

            {
                ItemMeta decline = DECLINE_ITEM.getItemMeta();
                decline.setDisplayName(ChatColor.RED + "Anuluj wyczyszczenie ekwipunku");
                decline.setLore(List.of(ChatColor.DARK_GRAY + "\u00bb " + ChatColor.GRAY + "Kliknij jezeli nie chcesz zeby twoj ekwipunek zostal wyczyszczony."));
                DECLINE_ITEM.setItemMeta(decline);
            }

            {
                ItemMeta info = INFO_ITEM.getItemMeta();
                info.setDisplayName(ChatColor.GRAY + "Informacja");
                info.setLore(List.of(ChatColor.GRAY + "Czyszczenie nie obejmuje drogich itemkow, takich jak brushe.", ChatColor.GRAY + "Rowniez nie obejmuje zbrojki."));
                INFO_ITEM.setItemMeta(info);
            }
        }

        private boolean cleaned;

        @Override
        public void onClick(InventoryClickEvent event) {
            if (event.getRawSlot() >= 0 && event.getRawSlot() <= 4) {
                if (event.getRawSlot() == 0 || event.getRawSlot() == 1) {

                    Inventory cleanedInventory = event.getWhoClicked().getInventory();
                    for(int currentSlot = 0; currentSlot < cleanedInventory.getSize(); currentSlot++) {
                        ItemStack currentItem = cleanedInventory.getItem(currentSlot);
                        if(currentItem == null)
                            continue;

                        if(configuration.findByItemStack(currentItem) != null)
                            continue;

                        cleanedInventory.setItem(currentSlot, null);
                    }

                    event.getWhoClicked().sendMessage(ChatColor.GREEN + "Twoj ekwpiunek zostal wyczyszczony!");
                    cleaned = true;
                }

                event.getWhoClicked().closeInventory();
            }
        }

        @Override
        public void onClose(InventoryCloseEvent event) {
            if (!cleaned)
                event.getPlayer().sendMessage(ChatColor.RED + "Anulowano czyszczenie ekwipunku!");
        }

        @Override
        public Inventory getInventory() {
            Inventory inventory = Bukkit.createInventory(this, InventoryType.HOPPER, "Potwierdz wyczyszczenie eq");
            inventory.setItem(0, CONFIRM_ITEM);
            inventory.setItem(1, CONFIRM_ITEM);
            inventory.setItem(2, INFO_ITEM);
            inventory.setItem(3, DECLINE_ITEM);
            inventory.setItem(4, DECLINE_ITEM);
            return inventory;
        }
    }
}

package pl.joagger.brusheros.listener;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.joagger.brusheros.BlockBreakUtil;
import pl.joagger.brusheros.BrushItem;
import pl.joagger.brusheros.BrushPluginConfiguration;

import java.util.stream.Collectors;

public class BrushListener implements Listener {
    private static final BlockFace[] AXIS = {BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST};

    private final BrushPluginConfiguration configuration;

    public BrushListener(BrushPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    public static BlockFace getDirection(Player player) {
        if(player.getLocation().getPitch() > 45) {
            return BlockFace.DOWN;
        } else if(player.getLocation().getPitch() < -45) {
            return BlockFace.UP;
        }
        return AXIS[Math.round(player.getLocation().getYaw() / 90f) & 0x3].getOppositeFace();
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onBrushUseBreak(BlockBreakEvent event) {
        if(event instanceof BrushBlockBreakEvent)
            return;

        if (event.getPlayer().getInventory().getItemInHand() == null) return;
        BrushItem item = configuration.findByItemStack(event.getPlayer().getInventory().getItemInHand());
        if (item == null)
            return;

        event.setCancelled(true);

        for (Block block : item.getArea().getAreaBlocks(event.getBlock().getLocation(), getDirection(event.getPlayer()))) {
            if (block != null && configuration.isAllowed(block.getType())) {
                BrushBlockBreakEvent brushEvent = new BrushBlockBreakEvent(block, event.getPlayer());
                Bukkit.getServer().getPluginManager().callEvent(brushEvent);
                if(!brushEvent.isCancelled())
                    block.breakNaturally();
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    private void onBrushUseInteract(PlayerInteractEvent event) {
        if (event.getItem() == null || (event.getAction() != Action.RIGHT_CLICK_BLOCK && event.getAction() != Action.LEFT_CLICK_BLOCK))
            return;

        BrushItem item = configuration.findByItemStackInteract(event.getPlayer().getInventory().getItemInHand());
        if (item == null)
            return;

        for (Block block : item.getArea().getAreaBlocks(event.getClickedBlock().getLocation(), getDirection(event.getPlayer()))) {
            if (block != null && configuration.isAllowed(block.getType())) {
                BrushBlockBreakEvent brushEvent = new BrushBlockBreakEvent(block, event.getPlayer());
                Bukkit.getServer().getPluginManager().callEvent(brushEvent);
                if(!brushEvent.isCancelled())
                    block.breakNaturally();
            }
        }
    }
}

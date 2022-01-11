package pl.joagger.brusheros.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;

public class BrushBlockBreakEvent extends BlockBreakEvent {
    public BrushBlockBreakEvent(Block theBlock, Player player) {
        super(theBlock, player);
    }
}

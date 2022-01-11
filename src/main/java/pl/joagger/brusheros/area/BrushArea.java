package pl.joagger.brusheros.area;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.List;

public interface BrushArea {
    private static List<Block> getBlocks(Location base, Matrix2x3D matrix) {
        List<Block> blocks = Lists.newArrayList();
        for (double x = (base.getBlockX() - matrix.xMin()); x <= (base.getBlockX() + matrix.xMax()); x++) {
            for (double y = (base.getBlockY() - matrix.yMin()); y <= (base.getBlockY() + matrix.yMax()); y++) {
                for (double z = (base.getBlockZ() - matrix.zMin()); z <= (base.getBlockZ() + matrix.zMax()); z++) {
                    Location loc = new Location(base.getWorld(), x, y, z);
                    Block b = loc.getBlock();
                    blocks.add(b);
                }
            }
        }

        return blocks;
    }

    default List<Block> getAreaBlocks(Location location, BlockFace face) {
        return getBlocks(location, getArea(face));
    }

    default Matrix2x3D getArea(BlockFace face) {
        throw new UnsupportedOperationException("Not implemented!");
    }
}

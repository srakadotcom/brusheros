package pl.joagger.brusheros.area;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomWallBrushArea implements BrushArea {
    private final Map<BlockFace, Matrix2x3D> matrix2x3DMap = new HashMap<>();

    public CustomWallBrushArea(ConfigurationSection section) {
        matrix2x3DMap.put(BlockFace.NORTH, new Matrix2x3D(section.getConfigurationSection("north")));
        matrix2x3DMap.put(BlockFace.SOUTH, new Matrix2x3D(section.getConfigurationSection("south")));
        matrix2x3DMap.put(BlockFace.WEST, new Matrix2x3D(section.getConfigurationSection("west")));
        matrix2x3DMap.put(BlockFace.EAST, new Matrix2x3D(section.getConfigurationSection("east")));
    }

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

    @Override
    public List<Block> getAreaBlocks(Location location, BlockFace face) {
        return getBlocks(location, matrix2x3DMap.get(face));
    }
}

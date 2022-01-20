package pl.joagger.brusheros.area;

import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;

public class WallBrushArea implements BrushArea {
    private final double x, y, z;
    private final double size;

    public WallBrushArea(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.size = x + y ;
    }

    public WallBrushArea(ConfigurationSection section) {
        this(section.getDouble("x"), section.getDouble("y"), section.getDouble("z"));
    }

    @Override
    public Matrix2x3D getArea(BlockFace face) {
        if (face == BlockFace.NORTH) {
            return new Matrix2x3D(x, y, 0, 0, z, 0);
        } else if (face == BlockFace.SOUTH) {
            return new Matrix2x3D(x, y, 0, 0, 0, z);
        } else if (face == BlockFace.EAST) {
            return new Matrix2x3D(0, z, 0, 0, x, y);
        } else if (face == BlockFace.WEST) {
            return new Matrix2x3D(z, 0, 0, 0, x, y);
        } else if (face == BlockFace.DOWN || face == BlockFace.UP) {
            return new Matrix2x3D(x, y, 0, z, x, y);
        } else {
            throw new IllegalArgumentException("Illegal block face.");
        }
    }
}

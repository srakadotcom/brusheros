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
        switch (face) {
            case NORTH -> {
                return new Matrix2x3D(x, y, 0, 0, z, 0);
            }
            case SOUTH -> {
                return new Matrix2x3D(x, y, 0, 0, 0, z);
            }
            case EAST -> {
                return new Matrix2x3D(0, z, 0, 0, x, y);
            }
            case WEST -> {
                return new Matrix2x3D(z, 0, 0, 0, x, y);
            }
            case DOWN, UP -> {
                return new Matrix2x3D(x, y, 0, z, x, y);
            }
            default -> throw new IllegalArgumentException("Illegal block face.");
        }
    }
}

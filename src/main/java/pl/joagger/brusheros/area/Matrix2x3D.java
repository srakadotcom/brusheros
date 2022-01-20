package pl.joagger.brusheros.area;


import org.bukkit.configuration.ConfigurationSection;

public final class Matrix2x3D {
    private final double xMin;
    private final double xMax;
    private final double yMin;
    private final double yMax;
    private final double zMin;
    private final double zMax;

    Matrix2x3D(
            double xMin, double xMax,
            double yMin, double yMax,
            double zMin, double zMax
    ) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zMin = zMin;
        this.zMax = zMax;
    }

    public Matrix2x3D(ConfigurationSection section) {
        this(
                Double.parseDouble(section.getString("x").split(":")[0]),
                Double.parseDouble(section.getString("x").split(":")[1]),
                Double.parseDouble(section.getString("y").split(":")[0]),
                Double.parseDouble(section.getString("y").split(":")[1]),
                Double.parseDouble(section.getString("z").split(":")[0]),
                Double.parseDouble(section.getString("z").split(":")[1])
        );
    }

    public double xMin() {
        return xMin;
    }

    public double xMax() {
        return xMax;
    }

    public double yMin() {
        return yMin;
    }

    public double yMax() {
        return yMax;
    }

    public double zMin() {
        return zMin;
    }

    public double zMax() {
        return zMax;
    }
}
package pl.joagger.brusheros.area;


import org.bukkit.configuration.ConfigurationSection;

public record Matrix2x3D(
        double xMin, double xMax,
        double yMin, double yMax,
        double zMin, double zMax
) {
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

    @Override
    public String toString() {
        return "[" +
                "xMin=" + xMin +
                ", xMax=" + xMax +
                ", yMin=" + yMin +
                ", yMax=" + yMax +
                ", zMin=" + zMin +
                ", zMax=" + zMax +
                ']';
    }
}
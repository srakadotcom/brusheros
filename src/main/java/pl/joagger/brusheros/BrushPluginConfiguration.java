package pl.joagger.brusheros;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class BrushPluginConfiguration {
    private final Set<BrushItem> brushItemList = new HashSet<>();
    private final EnumSet<Material> blockedMaterials = EnumSet.noneOf(Material.class);
    private boolean enableBrushDrop;

    public BrushItem findByItemStack(ItemStack itemStack) {
        if(itemStack == null)
            return null;

        for (BrushItem item : brushItemList)
            if (itemStack.getType() == item.getItemStack().getType()
                    && itemStack.hasItemMeta()
                    && Bukkit.getItemFactory().equals(itemStack.getItemMeta(), item.getItemStack().getItemMeta()))
                return item;
        return null;
    }

    public BrushItem findByItemStackInteract(ItemStack itemStack) {
        for (BrushItem item : brushItemList)
            if (!itemStack.getType().name().contains("_PICKAXE") &&
                    itemStack.getType() == item.getItemStack().getType()
                    && itemStack.hasItemMeta()
                    && Bukkit.getItemFactory().equals(itemStack.getItemMeta(), item.getItemStack().getItemMeta()))
                return item;
        return null;
    }

    public BrushItem findByName(String name) {
        for (BrushItem item : brushItemList)
            if (item.getName().equals(name))
                return item;
        return null;
    }

    public Collection<BrushItem> getBrushItems() {
        return Collections.unmodifiableCollection(brushItemList);
    }

    public void loadConfiguration(FileConfiguration configuration) {
        ConfigurationSection brushes = configuration.getConfigurationSection("brushes");
        if (brushes == null)
            throw new IllegalArgumentException("XD!");

        for (String key : brushes.getKeys(false)) {
            brushItemList.add(new BrushItem(
                    brushes.getConfigurationSection(key)
            ));
        }

        for(String material: configuration.getStringList("blocked")) {
            blockedMaterials.add(Material.getMaterial(material));
        }
        enableBrushDrop = configuration.getBoolean("enableBrushDrop");
    }

    public boolean isEnableBrushDrop() {
        return enableBrushDrop;
    }

    public boolean isAllowed(Material material) {
        return !blockedMaterials.contains(material);
    }
}

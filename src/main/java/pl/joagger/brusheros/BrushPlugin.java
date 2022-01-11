package pl.joagger.brusheros;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.joagger.brusheros.command.ClearInventoryCommand;
import pl.joagger.brusheros.command.GiveBrushCommand;
import pl.joagger.brusheros.listener.BrushKeepListener;
import pl.joagger.brusheros.listener.BrushListener;
import pl.joagger.brusheros.listener.InventoryListener;

public final class BrushPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        BrushPluginConfiguration configuration = new BrushPluginConfiguration();

        saveDefaultConfig();
        configuration.loadConfiguration(getConfig());
        getServer().getPluginManager().registerEvents(new BrushListener(configuration), this);
        if (!configuration.isEnableBrushDrop())
            getServer().getPluginManager().registerEvents(new BrushKeepListener(configuration), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        getCommand("givebrush").setExecutor(new GiveBrushCommand(configuration));
        getCommand("clear").setExecutor(new ClearInventoryCommand(configuration));
    }
}

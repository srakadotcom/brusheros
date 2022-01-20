package pl.joagger.brusheros;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import pl.joagger.brusheros.command.ClearInventoryCommand;
import pl.joagger.brusheros.command.GiveBrushCommand;
import pl.joagger.brusheros.listener.BrushKeepListener;
import pl.joagger.brusheros.listener.BrushListener;
import pl.joagger.brusheros.listener.InventoryListener;

public final class BrushPlugin extends JavaPlugin implements Listener {
    private final BrushPluginConfiguration configuration = new BrushPluginConfiguration();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        configuration.loadConfiguration(getConfig());
        getServer().getPluginManager().registerEvents(new BrushListener(configuration), this);
        getServer().getPluginManager().registerEvents(new BrushKeepListener(configuration), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);

        getCommand("givebrush").setExecutor(new GiveBrushCommand(configuration));
        getCommand("clear").setExecutor(new ClearInventoryCommand(configuration));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(command.getName().equals("brushreload")) {
            configuration.loadConfiguration(getConfig());
            sender.sendMessage(ChatColor.GREEN + "Pomyslnie przeladowano!");
        }
        return true;
    }
}

package pl.joagger.brusheros.command;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.joagger.brusheros.BrushItem;
import pl.joagger.brusheros.BrushPluginConfiguration;

import java.util.stream.Collectors;

public final class GiveBrushCommand implements CommandExecutor {
    private final BrushPluginConfiguration configuration;

    public GiveBrushCommand(BrushPluginConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Ta komenda jest dostepna tylko dla graczy.");
            return true;
        }

        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "Poprawne uzycie: /" + label + " (nazwa brusha)");
            sender.sendMessage(ChatColor.RED + "Dostepne brushe: " + configuration.getBrushItems().stream()
                    .map(BrushItem::getName)
                    .collect(Collectors.joining(", ")));
            return true;
        }

        BrushItem item = configuration.findByName(args[0]);
        if (item == null) {
            sender.sendMessage(ChatColor.RED + "Nie znaleziono brusha o takiej nazwie!");
            sender.sendMessage(ChatColor.RED + "Dostepne brushe: " + configuration.getBrushItems().stream()
                    .map(BrushItem::getName)
                    .collect(Collectors.joining(", ")));
            return true;
        }

        ((Player) sender).getInventory().addItem(item.getItemStack());
        sender.sendMessage(ChatColor.GREEN + "Pomyslnie nadano brushersa!");
        return true;
    }
}

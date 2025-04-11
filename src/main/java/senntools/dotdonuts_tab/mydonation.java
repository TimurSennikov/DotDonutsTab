package senntools.dotdonuts_tab;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class mydonation implements CommandExecutor{
    Dotdonuts_tab plugin;

    public mydonation(Dotdonuts_tab plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player player = (Player)sender;

        NamespacedKey key = new NamespacedKey(plugin, "dotdonuts");
        PersistentDataContainer container = player.getPersistentDataContainer();

        if(container.has(key, PersistentDataType.INTEGER)){
            Integer donation = container.get(key, PersistentDataType.INTEGER);
            sender.sendMessage(ChatColor.GRAY + "Вы задонатили " + ChatColor.DARK_GREEN + donation + plugin.reader.getServerCurrency() + ChatColor.RESET + "!");
        }
        else{
            sender.sendMessage("Вы ещё не донатили на сервер.");
        }
        return true;
    }
}
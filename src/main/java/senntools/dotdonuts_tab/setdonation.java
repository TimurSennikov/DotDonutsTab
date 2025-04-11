package senntools.dotdonuts_tab;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class setdonation implements CommandExecutor{
    Dotdonuts_tab plugin;

    public setdonation(Dotdonuts_tab plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof RemoteConsoleCommandSender)){sender.sendMessage(ChatColor.RED+"This command can only be used by RCON." + ChatColor.RESET); return true;}

        String name = args[0];
        int amount;

        try {
            amount = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED+"Usage: /setdonation [username] [amount]"+ChatColor.RESET);
            return true;
        }

        NamespacedKey key = new NamespacedKey(plugin, "dotdonuts");

        Player player = Bukkit.getPlayer(name);
        if(player == null){
            for(OfflinePlayer oplayer: Bukkit.getOfflinePlayers()){
                if(oplayer.getName().equals(name)){
                    sender.sendMessage("User is not online, will change amount on his login.");
                    plugin.deferredlist.addEntry(oplayer.getName() + ":" + amount);
                    return true;
                }
            }
            sender.sendMessage(ChatColor.RED+"User is not found on this server."+ChatColor.RESET);

            return true;
        }

        PersistentDataContainer container = player.getPersistentDataContainer();
        container.set(key, PersistentDataType.INTEGER, amount);

        plugin.eventmanager.updateGroup(player); // обновление от версии 1.1
        sender.sendMessage("Done! new " + name + "`s donation is " + amount + ".");
        return true;
    }
}
package senntools.dotdonuts_tab.servercommands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import senntools.dotdonuts_tab.Dotdonuts_tab;

public class setdonation implements CommandExecutor{
    Dotdonuts_tab plugin;

    public setdonation(Dotdonuts_tab plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if(!(sender instanceof RemoteConsoleCommandSender)){sender.sendMessage(ChatColor.RED+"This command can only be used by RCON." + ChatColor.RESET); return true;}
        if(args.length < 2){
            sender.sendMessage(ChatColor.RED+"Usage: /setdonation <player> <balance>" + ChatColor.RESET);
            return true;
        }

        String name = args[0];
        int amount;

        try {
            amount = Integer.parseInt(args[1]);
        }
        catch (NumberFormatException e) {
            sender.sendMessage(ChatColor.RED+"Usage: /setdonation [username] [amount]"+ChatColor.RESET);
            return true;
        }

        for(OfflinePlayer p: Bukkit.getOfflinePlayers()){
            if(p.getName().equals(name)){
                plugin.getBalanceReader().setPlayerBalance(p, amount);
                sender.sendMessage("Done! New " + p.getName() + " balance is now " + amount + plugin.getReader().getServerCurrency());
                return true;
            }
        }

        sender.sendMessage("Could not find player on server. Please ensure he joined at least one(1) time.");
        return true;
    }
}
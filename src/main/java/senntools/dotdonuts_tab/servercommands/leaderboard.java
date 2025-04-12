package senntools.dotdonuts_tab.servercommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import senntools.dotdonuts_tab.Dotdonuts_tab;
import senntools.dotdonuts_tab.Pair;
import senntools.dotdonuts_tab.PlayerTools;

import java.util.List;

public class leaderboard implements CommandExecutor{
    Dotdonuts_tab plugin;
    public leaderboard(Dotdonuts_tab plugin){this.plugin = plugin;}

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        List<Pair<String, Integer>> leaders = this.plugin.getBalanceReader().getAllBalances();
        for(Pair<String, Integer> p: leaders){
            if(p.getSecond() <= 0){break;}

            String color = PlayerTools.getPrefixByBalance(plugin, p.getSecond());
            sender.sendMessage(color + p.getFirst() + ChatColor.RESET + ": " + ChatColor.DARK_GREEN + p.getSecond() + plugin.getReader().getServerCurrency());
        }
        return true;
    }
}
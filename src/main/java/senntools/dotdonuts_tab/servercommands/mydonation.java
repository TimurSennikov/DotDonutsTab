package senntools.dotdonuts_tab.servercommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import senntools.dotdonuts_tab.Dotdonuts_tab;

public class mydonation implements CommandExecutor{
    Dotdonuts_tab plugin;

    public mydonation(Dotdonuts_tab plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        Player player = (Player)sender;

        Integer donation = plugin.getBalanceReader().getPlayerBalance(player);
        sender.sendMessage("Вы задонатили " + donation);
        return true;
    }
}
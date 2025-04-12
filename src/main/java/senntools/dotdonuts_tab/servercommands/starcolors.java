package senntools.dotdonuts_tab.servercommands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import senntools.dotdonuts_tab.ConfigEntryFormatter;
import senntools.dotdonuts_tab.ConfigReader;
import senntools.dotdonuts_tab.Pair;

import java.util.List;

public class starcolors implements CommandExecutor {
    ConfigReader reader;

    public starcolors(ConfigReader reader){
        this.reader = reader;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        String serverCurrency = this.reader.getServerCurrency();

        List<String> groups = this.reader.getDonateGroups();
        for(String group: groups){
            Pair<Integer, Integer> minmax = ConfigEntryFormatter.getMinMax(group);
            String color = group.split(":")[1];

            group = ChatColor.AQUA + "Группа от " + ChatColor.DARK_GREEN + minmax.getFirst() + serverCurrency + (minmax.getSecond() == Integer.MAX_VALUE ? " и выше" : (" до " + minmax.getSecond() + serverCurrency)) + "." + ChatColor.AQUA + " Код цвета для 'star color': " + color + color.replace("§", "") + ChatColor.RESET;
            sender.sendMessage(group);
        }

        return true;
    }
}
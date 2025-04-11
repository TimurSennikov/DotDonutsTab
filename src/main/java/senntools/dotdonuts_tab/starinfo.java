package senntools.dotdonuts_tab;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class starinfo implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        sender.sendMessage("dotdonuts версии 1.3, написан Беном (Ben_Sralden). Все претензии за то что что-то не работает к нему а не к спбогу.");
        return true;
    }
}

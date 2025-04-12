package senntools.dotdonuts_tab.servercommands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class starinfo implements CommandExecutor {
    private final double version = 1.4;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        sender.sendMessage("dotdonuts версии " + this.version + ", написан Беном (Ben_Sralden). Все претензии за то что что-то не работает к нему а не к спбогу.");
        return true;
    }
}
package senntools.dotdonuts_tab;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class star implements CommandExecutor{
    Dotdonuts_tab plugin;

    private boolean getOnOff(String s){
        return s.equalsIgnoreCase("on"); // вынес в отдельный метод чтоб менять если что легче было.
    }

    private void tabStar(CommandSender sender, String[] args){ // если меняем звезду в табе
        Player player = (Player)sender;

        PersistentDataContainer container = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(this.plugin, "tabstar");

        boolean state = this.getOnOff(args[1]);

        container.set(key, PersistentDataType.BOOLEAN, state);

        sender.sendMessage(ChatColor.GREEN + "Теперь ваша звезда в табе " + (state ? (ChatColor.GREEN + "отображается.") : (ChatColor.DARK_RED + "не отображается.") + ChatColor.RESET));
    }

    private void nickStar(CommandSender sender, String[] args){ // если меняем звезду в нике
        Player player = (Player)sender;

        PersistentDataContainer container = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(this.plugin, "nickstar");

        boolean state = this.getOnOff(args[1]);

        container.set(key, PersistentDataType.BOOLEAN, state);

        sender.sendMessage(ChatColor.GREEN + "Теперь перед вашим ником " + (state ? (ChatColor.GREEN + "отображается звезда.") : (ChatColor.DARK_RED + "не отображается звезда.") + ChatColor.RESET));
    }

    private void info(CommandSender sender, String[] args){
        Player player = (Player)sender;

        PersistentDataContainer container = player.getPersistentDataContainer();
        NamespacedKey tab = new NamespacedKey(this.plugin, "tabstar");
        NamespacedKey nick = new NamespacedKey(this.plugin, "nickstar");

        Boolean t = container.get(tab, PersistentDataType.BOOLEAN);
        Boolean n = container.get(nick, PersistentDataType.BOOLEAN);

        sender.sendMessage(ChatColor.AQUA + "Звезда в меню ТАБ: " + (t ? (ChatColor.GREEN + "включена") : (ChatColor.RED + "выключена")) + ChatColor.AQUA + "; Звезда перед ником: " + (n ? (ChatColor.GREEN + "включена") : (ChatColor.RED + "выключена")) + ".");
    }

    // методам сверху кормим два параметра потому что им больше нахуй не нужно

    public star(Dotdonuts_tab plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(args.length != 2 && args.length != 0){
            sender.sendMessage(ChatColor.RED + "Синтаксис: /star [tab/nick] [on/off]" + ChatColor.RESET);
            return true;
        }
        else if(args.length == 0){
            this.info(sender, args);
            return true;
        }

        if(args[0].equalsIgnoreCase("tab")){
            this.tabStar(sender, args);
        }
        else if(args[0].equalsIgnoreCase("nick")){
            this.nickStar(sender, args);
        }

        plugin.eventmanager.updateGroup((Player)sender);

        return true;
    }
}
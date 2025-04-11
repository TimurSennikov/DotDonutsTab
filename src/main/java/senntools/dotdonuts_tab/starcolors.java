package senntools.dotdonuts_tab;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

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
            int min=0, max=0;
            String color = group.split(":")[1];

            try{
                String[] tokens = group.split(":")[0].split("-");

                if(tokens.length == 1){
                    min = Integer.parseInt(tokens[0]);
                    max = Integer.MAX_VALUE;
                }

                else if(tokens.length == 2){
                    min = Integer.parseInt(group.split(":")[0].split("-")[0]);
                    max = Integer.parseInt(group.split(":")[0].split("-")[1]);
                }
                else{
                    System.err.println("Cannot process group " + group + "; invalid format detected."); // фикс от версии 1.1
                    sender.sendMessage(ChatColor.RED + "Неправильные рамки баланса в группе. Скажите об этой ошибке админам." + ChatColor.RESET);
                    return true;
                }
            }catch(NumberFormatException e){System.err.println("invalid group info, please check your config."); sender.sendMessage("Не получилось запарсить информацию о группе. Передайте админам что у плагина неправильный конфиг."); return true;}

            group = ChatColor.AQUA + "Группа от " + ChatColor.DARK_GREEN + min + serverCurrency + (max == Integer.MAX_VALUE ? " и выше" : (" до " + max + serverCurrency)) + "." + ChatColor.AQUA + " Код цвета для 'star color': " + color + color.replace("§", "") + ChatColor.RESET;
            sender.sendMessage(group);
        }

        return true;
    }
}
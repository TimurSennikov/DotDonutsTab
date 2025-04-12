package senntools.dotdonuts_tab;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class PlayerTools {
    public static String getPlayerPrefix(Dotdonuts_tab plugin, Player player){
        NamespacedKey color = new NamespacedKey(plugin, "starcolor");
        PersistentDataContainer container = player.getPersistentDataContainer();

        List<String> l = plugin.reader.getDonateGroups();

        Integer balance = plugin.balanceReader.getPlayerBalance(player);

        for(String group: l){
            Pair<Integer, Integer> minmax = ConfigEntryFormatter.getMinMax(group);
            if(minmax == null){System.err.println("minmax is null, Check your config. (EventManager)"); return "";}

            if(balance >= minmax.getFirst() && balance <= minmax.getSecond()){
                String col = container.get(color, PersistentDataType.STRING);
                if(col == null || col.equals("DEFAULT")){
                    col = group.split(":")[1];
                }

                return col + "★ " + ChatColor.RESET;
            }
            else{
                continue;
            }
        }

        return "";
    }

    public static String getPrefixByBalance(Dotdonuts_tab plugin, Integer balance){
        List<String> l = plugin.reader.getDonateGroups();
        for(String group: l){
            Pair<Integer, Integer> minmax = ConfigEntryFormatter.getMinMax(group);
            if(balance >= minmax.getFirst() && balance <= minmax.getSecond()){return group.split(":")[1] + "★ " + ChatColor.RESET;}
            else{continue;}
        }
        return "";
    }
}
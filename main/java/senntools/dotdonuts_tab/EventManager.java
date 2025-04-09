package senntools.dotdonuts_tab;

import com.sun.org.apache.xpath.internal.operations.Bool;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;

import me.neznamy.tab.api.event.plugin.TabLoadEvent;
import me.neznamy.tab.api.nametag.NameTagManager;
import me.neznamy.tab.api.tablist.TabListFormatManager;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

class BoolPair{
    public Boolean first;
    public Boolean second;
} // да, я знаю что есть Pair и Templates, но первый сюда не хочет импортироваться, а второй я не хочу исаользовать в силу узконаправленности класса BoolPair.

public class EventManager { // используется чтоб не срать в onLoad.
    Dotdonuts_tab plugin;

    ConfigReader reader;
    DeferredList deferredlist;

    public EventManager(Dotdonuts_tab plugin, ConfigReader reader, DeferredList deferredlist){
        this.plugin = plugin;

        this.reader = reader;
        this.deferredlist = deferredlist;
    }

    private String getPlayerPrefix(Player player){
        NamespacedKey key = new NamespacedKey(plugin, "dotdonuts");
        PersistentDataContainer container = player.getPersistentDataContainer();

        List<String> l = plugin.reader.getDonateGroups();

        Integer balance = container.get(key, PersistentDataType.INTEGER);
        if(balance == null){System.err.println("balance is null."); return null;}

        for(String group: l){

            int min, max;
            try{
                min = Integer.parseInt(group.split(":")[0].split("-")[0]);
                max = Integer.parseInt(group.split(":")[0].split("-")[1]);
            }catch(NumberFormatException e){System.err.println("invalid group info, please check your config."); return null;}

            if(balance >= min && balance <= max){
                return group.split(":")[1] + "★ " + ChatColor.RESET;
            }
        }

        return null;
    }

    private BoolPair getTabAndNameStates(Player player){ // возвращает пару булевских значений, которые отвечают аз включение / выключение донатерской звезды в нике и табе соответственно.
        NamespacedKey tab = new NamespacedKey(plugin, "tabstar");
        NamespacedKey nick = new NamespacedKey(plugin, "nickstar");
        PersistentDataContainer container = player.getPersistentDataContainer();

        if(!container.has(tab, PersistentDataType.BOOLEAN)){container.set(tab, PersistentDataType.BOOLEAN, true);}
        if(!container.has(nick, PersistentDataType.BOOLEAN)){container.set(nick, PersistentDataType.BOOLEAN, true);}

        BoolPair pair = new BoolPair();
        pair.first = container.get(tab, PersistentDataType.BOOLEAN);
        pair.second = container.get(nick, PersistentDataType.BOOLEAN);

        return pair;
    }

    private void processDeferredList(Player player){
        Integer amount = plugin.deferredlist.findEntry(player.getName());
        if(amount != null){
            NamespacedKey key = new NamespacedKey(plugin, "dotdonuts");
            PersistentDataContainer container = player.getPersistentDataContainer();

            container.set(key, PersistentDataType.INTEGER, amount);
        }
    }

    private void setupPlayer(Player player){
        TabAPI api = TabAPI.getInstance();
        if(api == null){System.err.println("TabAPI is null."); return;}

        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());
        if(tabPlayer == null){System.err.println("TabPlayer is null."); return;}

        String prefix = this.getPlayerPrefix(player);
        if(prefix == null){System.out.println("No prefix found for " + player.getName() + "."); return;} // не донатер или ошибка (если второе - печатается в консоль.)

        BoolPair stars = this.getTabAndNameStates(player);

        TabListFormatManager listFormatManager = api.getTabListFormatManager();
        NameTagManager nameTagManager = api.getNameTagManager();

        TabPlayer ply = api.getPlayer(player.getUniqueId());
        if(stars.first){
            listFormatManager.setPrefix(ply, prefix);
        }
        else{
            listFormatManager.setPrefix(ply, listFormatManager.getOriginalPrefix(ply));
        }

        if(stars.second){
            nameTagManager.setPrefix(ply, prefix);
        }
        else{
            nameTagManager.setPrefix(ply, nameTagManager.getOriginalPrefix(ply));
        }
    }

    public void playerLoad(PlayerLoadEvent event){
        Player player = (Player)event.getPlayer().getPlayer();

        this.processDeferredList(player);
        this.setupPlayer(player);
    }

    void updateGroup(Player player){
        this.setupPlayer(player);
    }
}
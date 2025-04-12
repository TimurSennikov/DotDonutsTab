package senntools.dotdonuts_tab;

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

public class EventManager { // используется чтоб не засорять в onLoad.
    Dotdonuts_tab plugin;

    ConfigReader reader;

    public EventManager(Dotdonuts_tab plugin, ConfigReader reader){
        this.plugin = plugin;

        this.reader = reader;
    }

    private Pair<Boolean, Boolean> getTabAndNameStates(Player player){ // возвращает пару булевских значений, которые отвечают аз включение / выключение донатерской звезды в нике и табе соответственно.
        NamespacedKey tab = new NamespacedKey(plugin, "tabstar");
        NamespacedKey nick = new NamespacedKey(plugin, "nickstar");
        PersistentDataContainer container = player.getPersistentDataContainer();

        if(!container.has(tab, PersistentDataType.BOOLEAN)){container.set(tab, PersistentDataType.BOOLEAN, true);}
        if(!container.has(nick, PersistentDataType.BOOLEAN)){container.set(nick, PersistentDataType.BOOLEAN, true);}

        Pair<Boolean, Boolean> pair = new Pair<>(container.get(tab, PersistentDataType.BOOLEAN), container.get(nick, PersistentDataType.BOOLEAN));

        return pair;
    }

    private void setupPlayerFields(Player player){
        NamespacedKey color = new NamespacedKey(plugin, "starcolor");
        PersistentDataContainer container = player.getPersistentDataContainer();

        if(!container.has(color, PersistentDataType.STRING)){container.set(color, PersistentDataType.STRING, "DEFAULT");}
    }

    private void setupPlayerColorProperty(Player player){
        NamespacedKey color=new NamespacedKey(plugin, "starcolor");
        PersistentDataContainer container = player.getPersistentDataContainer();

        Integer amount = plugin.balanceReader.getPlayerBalance(player);
        String col = container.get(color, PersistentDataType.STRING);
        if(col == null || col.equals("DEFAULT")){return;} // цвет не задан.

        List<String> availcolors = plugin.reader.getColorsByBalance(amount);
        if(!availcolors.contains(col)){container.set(color, PersistentDataType.STRING, "DEFAULT");}
    }

    private void setupPlayer(Player player){
        TabAPI api = TabAPI.getInstance();
        if(api == null){System.err.println("TabAPI is null."); return;}

        TabPlayer tabPlayer = api.getPlayer(player.getUniqueId());
        if(tabPlayer == null){System.err.println("TabPlayer is null."); return;}

        String prefix = PlayerTools.getPlayerPrefix(this.plugin, player);

        if(prefix == null){System.out.println("No prefix found for " + player.getName() + "."); return;} // не донатер или ошибка (если второе - печатается в консоль.)

        Pair<Boolean, Boolean> stars = this.getTabAndNameStates(player);

        TabListFormatManager listFormatManager = api.getTabListFormatManager();
        NameTagManager nameTagManager = api.getNameTagManager();

        TabPlayer ply = api.getPlayer(player.getUniqueId());
        if(stars.getFirst()){
            listFormatManager.setPrefix(ply, prefix);
        }
        else{
            listFormatManager.setPrefix(ply, listFormatManager.getOriginalPrefix(ply));
        }

        if(stars.getSecond()){
            nameTagManager.setPrefix(ply, prefix);
        }
        else{
            nameTagManager.setPrefix(ply, nameTagManager.getOriginalPrefix(ply));
        }
    }

    public void playerLoad(PlayerLoadEvent event){
        Player player = (Player)event.getPlayer().getPlayer();

        this.getTabAndNameStates(player);

        this.setupPlayerFields(player);

        this.setupPlayerColorProperty(player);
        this.setupPlayer(player);
    }

    public void updateGroup(Player player){
        this.setupPlayerColorProperty(player);
        this.setupPlayer(player);
    }
}
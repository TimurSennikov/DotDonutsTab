package senntools.dotdonuts_tab;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BalanceReader {
    File balanceFile;
    FileConfiguration config;

    public BalanceReader(File path, String filename){
        this.balanceFile = new File(path, filename);
        this.config = YamlConfiguration.loadConfiguration(this.balanceFile);
    }

    public Integer getPlayerBalance(Player player){
        return this.config.getInt("balance." + player.getName(), 0); // возвращаем баланс игрока или 0 чтобы избежать ошибок.
    }

    public Integer getPlayerBalance(OfflinePlayer player){
        return this.config.getInt("balance." + player.getName(), 0);
    }

    public void setPlayerBalance(Player player, Integer balance){
        if(player == null || balance == null){System.err.println("[Dotdonuts_tab] SetPlayerBalance: got null data!"); return;}

        this.config.set("balance." + player.getName(), balance);

        try {
            this.config.save(this.balanceFile);
        }
        catch(IOException e){
            System.err.println("[Dotdonuts_tab] Could not save balance file!");
        }
    }

    public void setPlayerBalance(OfflinePlayer player, Integer balance){
        if(player == null || balance == null){System.err.println("[Dotdonuts_tab] SetPlayerBalance: got null data!"); return;}
        String name = player.getName();

        this.config.set("balance." + name, balance);
        try{
            this.config.save(this.balanceFile);
        }
        catch(IOException e){
            System.err.println("[Dotdonuts_tab] Could not save balance file!");
        }
    }

    public List<Pair<String, Integer>> getAllBalances(){
        List<Pair<String, Integer>> l = new ArrayList<>();
        for(OfflinePlayer player: Bukkit.getOfflinePlayers()){ // для всех игроков
            l.add(new Pair<String, Integer>(player.getName(), this.getPlayerBalance(player))); // пара значений 'ник: 0/баланс'.
        }
        l.sort(new Comparator<Pair<String, Integer>>() {
            @Override
            public int compare(Pair<String, Integer> o1, Pair<String, Integer> o2){
                return o1.getSecond().compareTo(o2.getSecond()) * -1;
            }
        });

        return l;
    }
}
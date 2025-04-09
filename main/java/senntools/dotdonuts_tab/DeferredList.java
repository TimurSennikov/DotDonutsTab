package senntools.dotdonuts_tab;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DeferredList {
    File deferredListFile;
    FileConfiguration config;

    public DeferredList(Plugin plugin){
        File plugindir = plugin.getDataFolder();
        if(!plugindir.exists()){plugindir.mkdirs();}

        this.deferredListFile = new File(plugindir, "deferredList.yml");

        this.config = YamlConfiguration.loadConfiguration(this.deferredListFile);
    }

    public List<String> getList(){
        return this.config.getStringList("deferredlist");
    }

    public void removeEntry(String entry){
        List<String> l = this.config.getStringList("deferredlist");
        List<String> newl = l;

        for(String s: l){
            if(s.startsWith(entry)){
                newl.remove(s);
            }
        }

        l = newl;

        this.config.set("deferredlist", l);

        try{
            this.config.save(this.deferredListFile);
        }
        catch (IOException e){
            System.out.println("[DONDONUTS] Could not save deferred list.");
        }
    }

    public void addEntry(String entry){
        List<String> l = this.config.getStringList("deferredlist");
        l.add(entry);
        this.config.set("deferredlist", l);

        try{
            this.config.save(this.deferredListFile);
        }
        catch (IOException e){
            System.out.println("[DONDONUTS] Could not save deferred list.");
        }
    }

    public Integer findEntry(String name){
        List<String> l = this.config.getStringList("deferredlist");

        for(String s: l){
            if(s.startsWith(name)){
                try {
                    Integer i = Integer.parseInt(s.split(":")[1]);
                    return i;
                }
                catch (NumberFormatException e){
                    this.removeEntry(s); // удаляем невалидную энтри
                    continue;
                }
            }
        }

        return null;
    }
}
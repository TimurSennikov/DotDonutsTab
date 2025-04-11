package senntools.dotdonuts_tab;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigReader{
    Plugin plugin;
    FileConfiguration config;

    public ConfigReader(Plugin plugin){
        this.plugin = plugin;

        File path = new File(plugin.getDataFolder() + File.separator + "/");
        if (!path.exists()) {
            path.mkdirs();
        }
        File configFile = new File(plugin.getDataFolder(), "config.yml");
        if (!configFile.exists()){try{configFile.createNewFile();}catch (IOException e){System.err.println("[DONGROUPS] Could not create config file!"); return;}}
        this.config = YamlConfiguration.loadConfiguration(configFile);

        System.out.println("[DONGROUPS] " + this.getDonateGroups().size() + " groups loaded by ConfigReader.");
    }

    public FileConfiguration getConfig(){ // раньше здесь был жир
        return config;
    }

    public List<String> getDonateGroups(){ // используется для получения списка групп донатов.
        return config.getStringList("dongroups");
    }

    public List<String> getColorsByBalance(Integer balance){ // добавлено в версии 1.2
        List<String> colors=new ArrayList<String>(), groups=new ArrayList<String>();

        for(String group: this.getDonateGroups()){
            Pair<Integer, Integer> minmax = ConfigEntryFormatter.getMinMax(group);
            if(minmax == null){System.err.println("minmax is null. (ConfigReader)"); return colors;}

            if(balance >= minmax.getFirst()){
                colors.add(group.split(":")[1]);
            }
        }

        return colors;
    }

    public String getServerCurrency(){ // формат деняг.
        String currency = config.getString("serverCurrency");

        return currency == null ? "" : currency;
    }
}
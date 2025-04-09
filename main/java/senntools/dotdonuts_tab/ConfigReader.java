package senntools.dotdonuts_tab;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
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
        if (!configFile.exists()){try{configFile.createNewFile();}catch (IOException e){System.err.println("[DONGROUPS] Could not create config file!");}}
        this.config = YamlConfiguration.loadConfiguration(configFile);

        System.out.println("[DONGROUPS] " + this.getDonateGroups().size() + " groups loaded by ConfigReader.");
    }

    public FileConfiguration getConfig(){ // раньше здесь был жир
        return config;
    }

    public List<String> getDonateGroups(){ // используется для получения списка групп донатов.
        return config.getStringList("dongroups");
    }

    public String getServerCurrency(){ // формат деняг.
        String currency = config.getString("serverCurrency");

        return currency == null ? "" : currency;
    }
}
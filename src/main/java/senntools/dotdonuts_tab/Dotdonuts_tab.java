package senntools.dotdonuts_tab;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;

import org.bukkit.plugin.java.JavaPlugin;
import senntools.dotdonuts_tab.servercommands.*;

public final class Dotdonuts_tab extends JavaPlugin {
    ConfigReader reader;
    BalanceReader balanceReader;
    EventManager eventmanager;

    public ConfigReader getReader(){
        return this.reader;
    }

    public BalanceReader getBalanceReader(){
        return this.balanceReader;
    }

    public EventManager getEventManager(){
        return this.eventmanager;
    }

    @Override
    public void onEnable() {
        this.reader = new ConfigReader(this);
        this.balanceReader = new BalanceReader(getDataFolder(), "balances.yml");
        this.eventmanager = new EventManager(this, this.reader);

        this.getCommand("setdonation").setExecutor(new setdonation(this));
        this.getCommand("star").setExecutor(new star(this));
        this.getCommand("mydonation").setExecutor(new mydonation(this));
        this.getCommand("starcolors").setExecutor(new starcolors(this.reader));
        this.getCommand("starinfo").setExecutor(new starinfo());
        this.getCommand("leaderboard").setExecutor(new leaderboard(this));

        System.out.println("Starting up dotdonutstab.");

        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class, this.eventmanager::playerLoad); // при присоединении игрока
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
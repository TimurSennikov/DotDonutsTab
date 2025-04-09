package senntools.dotdonuts_tab;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.event.player.PlayerLoadEvent;

import me.neznamy.tab.api.event.plugin.TabLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class Dotdonuts_tab extends JavaPlugin {
    ConfigReader reader;
    DeferredList deferredlist;

    EventManager eventmanager;

    @Override
    public void onEnable() {
        this.getCommand("setdonation").setExecutor(new setdonation(this));
        this.getCommand("star").setExecutor(new star(this));
        this.getCommand("mydonation").setExecutor(new mydonation(this));

        this.reader = new ConfigReader(this);
        this.deferredlist = new DeferredList(this);

        this.eventmanager = new EventManager(this, this.reader, this.deferredlist);

        System.out.println("Starting up dotdonutstab.");

        TabAPI.getInstance().getEventBus().register(PlayerLoadEvent.class, this.eventmanager::playerLoad); // при присоединении игрока
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
package org.crealityworks.booster;

import java.sql.SQLException;
import java.util.HashMap;

import org.bukkit.plugin.java.JavaPlugin;
import org.crealityworks.booster.events.EvtListener;
import org.crealityworks.booster.routines.Routine;
import org.crealityworks.booster.sql.SQLConnectionsManager;

public class Main extends JavaPlugin {
	
	// In minutes
	public static HashMap<String, Integer> players = new HashMap<String, Integer>();
	
	@Override
	public void onEnable() {
		this.saveDefaultConfig();
		if(dbExists()) {
			registerCommands();
			registerEvents();
			startRoutines();
			createTables();
			getLogger().info("Money system plugin by zwoosks has been enabled.");
		} else {
			this.getLogger().info(Utils.chat("&cCouldn't connect to the database. Disabling the plugin..."));
			this.getServer().getPluginManager().disablePlugin(this);
		}
	}
	
	@Override
	public void onDisable() {
		getLogger().info("Money system plugin by zwoosks has been unloaded.");
	}
	
	private void createTables() {
		try {
			SQLConnectionsManager.registerTables(this.getConfig());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private void registerCommands() {
		this.getCommand("boostsystem").setExecutor(new Commands(this));
	}
	
	private void registerEvents() {
		this.getServer().getPluginManager().registerEvents(new EvtListener(this), this);
	}
	
	private void startRoutines() {
		Routine r = new Routine(this);
		r.mainRoutine();
	}
	
	private boolean dbExists() {
		return SQLConnectionsManager.databaseExists(this.getConfig());
	}

}
package org.crealityworks.booster.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.crealityworks.booster.Main;
import org.crealityworks.booster.sql.SQLConnectionsManager;

public class EvtListener implements Listener {
	
	private Main plugin;
	
	public EvtListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(player.hasPermission("mboost.active")) {
			int got = SQLConnectionsManager.getPlayer(player.getUniqueId().toString(), plugin.getConfig());
			if(got != -1) {
				Main.players.put(player.getUniqueId().toString(), got);
			} else {
				Main.players.put(player.getUniqueId().toString(), 0);
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		if(Main.players.containsKey(player.getUniqueId().toString())) {
			int localTime = Main.players.get(player.getUniqueId().toString());
			if(SQLConnectionsManager.getPlayer(player.getUniqueId().toString(), plugin.getConfig()) != -1) {
				SQLConnectionsManager.updatePlayer(player.getUniqueId().toString(), localTime, plugin.getConfig());
			} else {
				SQLConnectionsManager.insertPlayer(player.getUniqueId().toString(), localTime, plugin.getConfig());
			}
			Main.players.remove(player.getUniqueId().toString());
		}
	}
	
}
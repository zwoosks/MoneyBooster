package org.crealityworks.booster.routines;

import java.math.BigDecimal;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.crealityworks.booster.Main;
import org.crealityworks.booster.sql.SQLConnectionsManager;

import com.earth2me.essentials.api.Economy;

public class Routine {
	
	private Main plugin;
	
	public Routine(Main plugin) {
		this.plugin = plugin;
	}
	
	public void mainRoutine() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for(Map.Entry<String, Integer> p : Main.players.entrySet()) {
					String uuid = p.getKey();
					int time = p.getValue();
					Player player = Bukkit.getServer().getPlayer(UUID.fromString(uuid));
					if(player.hasPermission("mboost.active")) {
						Main.players.put(uuid, time + 1);
						// now check if he has 60 minutes (or more) and give the money
						if(time + 1 >= 60) {
							try {
								Main.players.put(uuid, 0);
								BigDecimal toGive = new BigDecimal(plugin.getConfig().getString("vars.rewardPerHour"));
								BigDecimal newDecimal = toGive.add(Economy.getMoneyExact(player.getName()));
								Economy.setMoney(player.getName(), newDecimal);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						Main.players.remove(uuid);
						SQLConnectionsManager.deletePlayer(uuid, plugin.getConfig());
					}
					
				}
			}
		}, 30*20, 20*60);
	}
	
}
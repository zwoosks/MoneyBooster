package org.crealityworks.booster;

import java.math.BigDecimal;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.earth2me.essentials.api.Economy;

public class Commands implements CommandExecutor {
	
	private Main plugin;
	
	public Commands(Main plugin) {
		this.plugin = plugin;
		
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("boostsystem")) {
			if(sender instanceof Player) {
				try {
					Player player = (Player) sender;
					if(args[0].equalsIgnoreCase("hour")) {
						if(player.hasPermission("mboost.boost")) {
							BigDecimal money = Economy.getMoneyExact(player.getName());
							BigDecimal required = new BigDecimal(plugin.getConfig().getString("vars.commandCost"));
							int res = money.compareTo(required);
							if(res == 0 || res == 1) {
								if(!Main.players.containsKey(player.getUniqueId().toString())) {
									BigDecimal newTotal = money.subtract(required);
									Economy.setMoney(player.getName(), newTotal);
									Main.players.put(player.getUniqueId().toString(), 0);
									CommandSender cs = Bukkit.getConsoleSender();
									String duration = plugin.getConfig().getString("vars.duration") + "d";
									Bukkit.getServer().dispatchCommand(cs, "lp user " + player.getName() + " permission settemp mboost.active true " + duration);
									player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.done").replace("%player%", player.getName()).replace("%cost%", required.toString())));
								} else {
									player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.alreadyActive")));
								}
							} else {
								player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.noEnoughMoney").replace("%requiered%", plugin.getConfig().getString("vars.commandCost"))));
							}
						} else {
							player.sendMessage(Utils.chat(plugin.getConfig().getString("messages.noPerms").replace("%player%", player.getName()).replace("%perm%", "mboost.boost")));
						}
					} else {
						List<String> helpMsg = plugin.getConfig().getStringList("messages.help");
						for(String s : helpMsg) {
							player.sendMessage(Utils.chat(s.replace("%player%", player.getName())));
						}
					}
				} catch(Exception ex) {
					List<String> helpMsg = plugin.getConfig().getStringList("messages.help");
					for(String s : helpMsg) {
						sender.sendMessage(Utils.chat(s));
					}
				}
						
			}
		}
		return true;
	}
	
	
	
}
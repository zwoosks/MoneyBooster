package org.crealityworks.booster.sql;

import org.bukkit.configuration.file.FileConfiguration;

public class Credentials {
	
	private FileConfiguration config;
	private String base = "mysql.";
	
	public Credentials(FileConfiguration config) {
		this.config = config;
	}
	
	public String username() {
		return config.getString(base + "user");
	}
	
	public String password() {
		return config.getString(base + "password");
	}
	
	public String address() {
		return config.getString(base + "address");
	}
	
	public String port() {
		return config.getString(base + "port");
	}
	
	public String database() {
		return config.getString(base + "database");
	}
	
}
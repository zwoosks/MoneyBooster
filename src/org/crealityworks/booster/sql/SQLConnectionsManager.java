package org.crealityworks.booster.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.crealityworks.booster.Utils;

public class SQLConnectionsManager {
	
	public static boolean databaseExists(FileConfiguration config) {
		Credentials c = new Credentials(config);
		final String username = c.username();
		final String password = c.password();
		final String port = c.port();
		final String database = c.database();
		final String address = c.address();
		
		final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + database;
		
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){}
			try{
				if(conn!=null)
					conn.close();
					return true;
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean registerTables(FileConfiguration config) throws SQLException {
		
		Credentials c = new Credentials(config);
		final String username = c.username();
		final String password = c.password();
		final String port = c.port();
		final String database = c.database();
		final String address = c.address();
		
		final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + database;
		
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			stmt = conn.createStatement();
			String sql = "CREATE TABLE IF NOT EXISTS `mb_pl` (" + 
					"  `uuid` VARCHAR(45) NOT NULL," + 
					"  `minutes` INT NOT NULL," + 
					"  PRIMARY KEY (`uuid`));";
			stmt.executeUpdate(sql);
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){}
			try{
				if(conn!=null)
					conn.close();
					return true;
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean insertPlayer(String uuid, int minutes, FileConfiguration config) {
		Bukkit.getServer().broadcastMessage(Utils.chat("&cINSERT"));
		Credentials c = new Credentials(config);
		final String username = c.username();
		final String password = c.password();
		final String port = c.port();
		final String database = c.database();
		final String address = c.address();
		
		final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + database;
		
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			String sql = "INSERT INTO `mb_pl` (`uuid`, `minutes`) VALUES (?, ?);";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, uuid);
			ps.setInt(2, minutes);
			ps.execute();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){}
			try{
				if(conn!=null)
					conn.close();
					return true;
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return false;
	}
	
	public static int getPlayer(String uuid, FileConfiguration config) {
		Bukkit.getServer().broadcastMessage(Utils.chat("&cGETPLAYER"));
		int toReturn = -1;
		Credentials c = new Credentials(config);
		final String username = c.username();
		final String password = c.password();
		final String port = c.port();
		final String database = c.database();
		final String address = c.address();
		
		final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + database;
		
		Connection conn = null;
		Statement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			String sql = "SELECT * from `mb_pl` WHERE `uuid`='" + uuid + "';";
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()) {
				toReturn = rs.getInt("minutes");
			}
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){}
			try{
				if(conn!=null)
					conn.close();
					return toReturn;
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return toReturn;
	}
	
	public static boolean updatePlayer(String uuid, int minutes, FileConfiguration config) {
		Bukkit.getServer().broadcastMessage(Utils.chat("&cUPDATE"));
		Credentials c = new Credentials(config);
		final String username = c.username();
		final String password = c.password();
		final String port = c.port();
		final String database = c.database();
		final String address = c.address();
		
		final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + database;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			String sql = "UPDATE `mb_pl` SET `minutes`=? WHERE `uuid`=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, minutes);
			stmt.setString(2, uuid);
			stmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){}
			try{
				if(conn!=null)
					conn.close();
					return true;
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return false;
	}
	
	public static boolean deletePlayer(String uuid, FileConfiguration config) {
		Bukkit.getServer().broadcastMessage(Utils.chat("&cUPDATE"));
		Credentials c = new Credentials(config);
		final String username = c.username();
		final String password = c.password();
		final String port = c.port();
		final String database = c.database();
		final String address = c.address();
		
		final String DB_URL = "jdbc:mysql://" + address + ":" + port + "/" + database;
		
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(DB_URL, username, password);
			String sql = "DELETE FROM `mb_pl` WHERE `uuid`=?;";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, uuid);
			stmt.executeUpdate();
		}catch(SQLException se){
			se.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try{
				if(stmt!=null) conn.close();
			}catch(SQLException se){}
			try{
				if(conn!=null)
					conn.close();
					return true;
			}catch(SQLException se){
				se.printStackTrace();
			}
		}
		return false;
	}
	
}
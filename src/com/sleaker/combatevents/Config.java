package com.sleaker.combatevents;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class Config {
	private static Logger log = Logger.getLogger("Minecraft");
	
	//Time in seconds to tag a player as 'in-combat'
	private static int combatTime = 10;
	private static Configuration config;
	//Whether pets should toggle combat or not
	private static boolean petTriggersCombat = true;
	private static Set<String> denyCommands = new HashSet<String>();
	
	/**
	 * Initialize the Configuration from file
	 * 
	 * @param plugin
	 */
	public static void initialize(CombatEventsCore plugin) {
		//Check to see if there is a configuration file.
		File yml = new File(plugin.getDataFolder()+"/config.yml");

		if (!yml.exists()) {
			new File(plugin.getDataFolder().toString()).mkdir();
			try {
				yml.createNewFile();
			}
			catch (IOException ex) {
				log.info(plugin.plugName + " - Cannot create configuration file. And none to load, using defaults.");
			}
		}   
		
		config = plugin.getConfiguration();
		if (config.getKeys(null).isEmpty()) {
			config.setProperty("pet-triggers-combat", petTriggersCombat);
			config.setProperty("combat-time", combatTime);
			config.setProperty("disabled-commands", null);
		}
		
		petTriggersCombat = config.getBoolean("pet-triggers-combat", petTriggersCombat);
		combatTime = config.getInt("combat-time", combatTime);
		denyCommands.addAll(config.getStringList("disabled-commands", null));
		
		config.save();
	}
	
	public static int getCombatTime() {
		return combatTime;
	}
	public static void setCombatTime(int combatTime) {
		Config.combatTime = combatTime;
	}
	public static boolean isPetTriggersCombat() {
		return petTriggersCombat;
	}
	public static void setPetTriggersCombat(boolean petTriggersCombat) {
		Config.petTriggersCombat = petTriggersCombat;
	}
	
	public static void addDeniedCommand(String command) {
		Config.denyCommands.add(command);
	}
	
	public static void removeDeniedCommand(String command) {
		Config.denyCommands.remove(command);
	}
	public static boolean isDeniedCommand(String command) {
		return Config.denyCommands.contains(command);
	}
}

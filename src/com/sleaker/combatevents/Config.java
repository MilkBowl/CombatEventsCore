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
	private static boolean targetTriggersCombat = true;
	private static Set<String> denyCommands = new HashSet<String>();
	private static String denyCommandMessage = "You can not use that command while in combat!";
	private static String leaveCombatMessage = "You have left combat!";
	private static String enterCombatMessage = "You have entered combat!";

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
			//Default Options
			config.setProperty("pet-triggers-combat", petTriggersCombat);
			config.setProperty("target-triggers-combat", targetTriggersCombat);
			config.setProperty("combat-time", combatTime);
			config.setProperty("disabled-commands", null);
			//Default Messages
			config.setProperty("messages.deny-command", denyCommandMessage);
			config.setProperty("messages.enter-combat", enterCombatMessage);
			config.setProperty("messages.leave-combat", leaveCombatMessage);
		}
		//Load the Options
		petTriggersCombat = config.getBoolean("pet-triggers-combat", petTriggersCombat);
		targetTriggersCombat = config.getBoolean("target-triggers-combat", targetTriggersCombat);
		combatTime = config.getInt("combat-time", combatTime);
		denyCommands.addAll(config.getStringList("disabled-commands", null));
		//Load the messages
		denyCommandMessage = config.getString("messages.deny-command", denyCommandMessage);
		enterCombatMessage = config.getString("messages.enter-combat", enterCombatMessage);
		leaveCombatMessage = config.getString("messages.leave-combat", leaveCombatMessage);

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

	public static Set<String> getDenyCommands() {
		return denyCommands;
	}

	public static String getDenyCommandMessage() {
		return denyCommandMessage;
	}

	public static String getLeaveCombatMessage() {
		return leaveCombatMessage;
	}

	public static String getEnterCombatMessage() {
		return enterCombatMessage;
	}

	public static boolean isTargetTriggersCombat() {
		return targetTriggersCombat;
	}

}

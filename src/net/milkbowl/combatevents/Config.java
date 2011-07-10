package net.milkbowl.combatevents;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import org.bukkit.util.config.Configuration;

public class Config {
	private static Logger log = Logger.getLogger("Minecraft");

	private static Configuration config;
	//Time in seconds to tag a player as 'in-combat'
	private static int combatTime = 10;
	private static double targetTriggerRange = 20;
	//Whether pets should toggle combat or not
	private static boolean petTriggersCombat = true;
	private static boolean targetTriggersCombat = true;
	
	private static Set<String> denyCommands = new HashSet<String>();
	
	private static boolean enableCombatMessages = true;
	private static String denyCommandMessage = "You can not use that command while in combat!";
	private static String leaveCombatMessage = "You have left combat!";
	private static String enterCombatMessage = "You have entered combat!";
	private static String campMessage = "You will not get anymore rewards until you move to a new location!";
	
	//Camper detection settings
	private static boolean antiCamp = true;
	private static int campTime = 60;
	private static int campRange = 10;
	private static int campKills = 3;

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
			config.setProperty("target-trigger-range", targetTriggerRange);
			//Default Messages
			config.setProperty("messages.enter-leave-enabled", enableCombatMessages);
			config.setProperty("messages.deny-command", denyCommandMessage);
			config.setProperty("messages.enter-combat", enterCombatMessage);
			config.setProperty("messages.leave-combat", leaveCombatMessage);
			config.setProperty("messages.camp-message", campMessage);
			//Camp settings
			config.setProperty("camp.anti-camp-enabled", antiCamp);
			config.setProperty("camp.timer", campTime);
			config.setProperty("camp.range", campRange);
			config.setProperty("camp.kills-before-trigger", campKills);
		
		}
		//Load the Options
		petTriggersCombat = config.getBoolean("pet-triggers-combat", petTriggersCombat);
		targetTriggersCombat = config.getBoolean("target-triggers-combat", targetTriggersCombat);
		combatTime = config.getInt("combat-time", combatTime);
		denyCommands.addAll(config.getStringList("disabled-commands", null));
		targetTriggerRange = config.getDouble("target-trigger-range", targetTriggerRange);
		//Load the messages
		enableCombatMessages = config.getBoolean("messages.enter-leave-enabled", enableCombatMessages);
		denyCommandMessage = config.getString("messages.deny-command", denyCommandMessage);
		enterCombatMessage = config.getString("messages.enter-combat", enterCombatMessage);
		leaveCombatMessage = config.getString("messages.leave-combat", leaveCombatMessage);
		campMessage = config.getString("messages.camp-message", campMessage);
		//Load camping settings
		antiCamp = config.getBoolean("camp.anti-camp-enabled", antiCamp);
		campTime = config.getInt("camp.timer", campTime);
		campRange = config.getInt("camp.range", campRange);
		campKills = config.getInt("camp.kills-before-trigger", campKills);
		if (campKills < 2 ) {
			log.info("[CombatEventsCore] - camp.kills-before-trigger must be at least 2. Setting to default value.");
			campKills = 3;
			config.setProperty("camp.kills-before-trigger", 3);
		}
		
		config.save();
	}

	public static String getCampMessage() {
		return campMessage;
	}

	public static boolean isEnableCombatMessages() {
		return enableCombatMessages;
	}

	public static boolean isAntiCamp() {
		return antiCamp;
	}

	public static int getCampTime() {
		return campTime;
	}

	public static int getCampRange() {
		return campRange;
	}

	public static int getCampKills() {
		return campKills;
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
	
	public static double getTargetTriggerRange() {
		return targetTriggerRange;
	}

}

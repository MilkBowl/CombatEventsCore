package net.milkbowl.combatevents;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import net.milkbowl.administrate.AdminHandler;
import net.milkbowl.administrate.Administrate;
import net.milkbowl.combatevents.listeners.CombatEntityListener;
import net.milkbowl.combatevents.listeners.CombatPlayerListener;
import net.milkbowl.combatevents.tasks.LeaveCombatTask;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class CombatEventsCore extends JavaPlugin {
	private static Logger log = Logger.getLogger("Minecraft");
	private Map<String, Camper> campMap = new HashMap<String, Camper>();
	private Map<String, CombatPlayer> inCombat = Collections.synchronizedMap(new HashMap<String, CombatPlayer>());

	private CombatEntityListener entityListener = new CombatEntityListener(this);
	private CombatPlayerListener playerListener = new CombatPlayerListener(this);
	public AdminHandler admins = null;

	String plugName = "[CombatEvents]";

	/*
	 * Defines a reason for the player entering combat
	 */
	public enum CombatReason {
		TARGETED_BY_MOB, DAMAGED_BY_MOB, ATTACKED_MOB, ATTACKED_PLAYER, DAMAGED_BY_PLAYER, PET_TOOK_DAMAGE, PET_ATTACKED, CUSTOM
	}

	public enum LeaveCombatReason {
		QUIT, KICK, ERROR, TIMED, DEATH, TARGET_DIED, CUSTOM
	}

	public enum KillType {
		NORMAL, PROJECTILE, CAMPING, CUSTOM
	}

	@Override
	public void onLoad() {
		Config.initialize(this);
	}

	@Override
	public void onDisable() {
		log.info(plugName + " - v" + this.getDescription().getVersion() + " disabled!");
	}

	@Override
	public void onEnable() {

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_TARGET, entityListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Highest, this);
		pm.registerEvent(Event.Type.PLAYER_QUIT, playerListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_KICK, playerListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, playerListener, Priority.High, this);

		//setup our optional dependencies
		setupOptionals();
		log.info(plugName + " - v" + this.getDescription().getVersion() + " enabled!");
	}

	private void setupOptionals() {
		if (admins == null) {
			Plugin admin = this.getServer().getPluginManager().getPlugin("Administrate");
			if (admin != null) {
				admins = ((Administrate) admin).getAdminHandler();
				log.info(plugName + " - Successfully hooked into Administrate v" + admin.getDescription().getVersion());
			}
		} 
	}

	/**
	 * Add the player to the inCombat mapping with necessary info
	 * If the player is already in-combat and we are re-adding them
	 * only reset the time and their inventory, not their initial reason, unless it's a new one
	 * 
	 */
	public void enterCombat(Player player, CombatPlayer cPlayer, Entity entity, CombatReason reason) {
		if (!inCombat.containsKey(player.getName())) {
			inCombat.put(player.getName(), cPlayer);
			if (Config.isEnableCombatMessages())
				player.sendMessage(Config.getEnterCombatMessage());
		} else if (inCombat.get(player.getName()).getReason(entity) == null){
			//Add a new reason and remake the task for leaving combat
			LeaveCombatTask leaveTask = new LeaveCombatTask(player, LeaveCombatReason.TIMED, this);
			CombatPlayer thisCPlayer = inCombat.get(player.getName());
			thisCPlayer.setInventory(cPlayer.getInventory());
			thisCPlayer.addReason(entity, reason);
			//Cancel our leave combat task and remake a new one
			getServer().getScheduler().cancelTask(thisCPlayer.getTaskId());
			thisCPlayer.setTaskId(getServer().getScheduler().scheduleAsyncDelayedTask(this, leaveTask, Config.getCombatTime() * 20));
		} else {
			//Since this entity is already in the map lets just refresh their inventory and the timer
			LeaveCombatTask leaveTask = new LeaveCombatTask(player, LeaveCombatReason.TIMED, this);
			CombatPlayer thisCPlayer = inCombat.get(player.getName());
			thisCPlayer.setInventory(cPlayer.getInventory());
			//Cancel our leave combat task and remake a new one
			getServer().getScheduler().cancelTask(thisCPlayer.getTaskId());
			thisCPlayer.setTaskId(getServer().getScheduler().scheduleAsyncDelayedTask(this, leaveTask, Config.getCombatTime() * 20));
		}
	}

	/**
	 * removes a player from combat
	 * 
	 * @param player
	 * @return
	 */
	public CombatPlayer leaveCombat(Player player) {
		if (Config.isEnableCombatMessages())
			player.sendMessage(Config.getLeaveCombatMessage());
		//Cancel the task
		if (this.getServer().getScheduler().isQueued(getCombatTask(player)))
			this.getServer().getScheduler().cancelTask(getCombatTask(player));
		return inCombat.remove(player.getName());
	}

	public CombatPlayer getCombatPlayer (Player player) {
		return inCombat.get(player.getName());
	}

	public boolean isInCombat(Player player) {
		return inCombat.containsKey(player.getName());
	}

	public int getCombatTask(Player player) {
		return inCombat.get(player.getName()).getTaskId();
	}

	public void setCombatTask(Player player, int taskId) {
		inCombat.get(player.getName()).setTaskId(taskId);
	}

	public void setCombatReason(Player player, Entity entity, CombatReason reason) {
		inCombat.get(player.getName()).addReason(entity, reason);
	}

	public CombatReason getCombatReason (Player player, Entity entity) {
		return inCombat.get(player.getName()).getReason(entity);
	}

	public Map<String, Camper> getCampMap() {
		return campMap;
	}
}

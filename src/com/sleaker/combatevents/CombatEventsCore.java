package com.sleaker.combatevents;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

import net.milkbowl.administrate.AdminHandler;
import net.milkbowl.administrate.Administrate;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class CombatEventsCore extends JavaPlugin {
	private static Logger log = Logger.getLogger("Minecraft");
	private CombatEntityListener entityListener = new CombatEntityListener(this);
	private CombatPlayerListener playerListener = new CombatPlayerListener(this);
	public AdminHandler admins = null;

	private static Map<LivingEntity, LivingEntity> killMap = new ConcurrentHashMap<LivingEntity, LivingEntity>();
	private static Map<String, CombatPlayer> inCombat = new ConcurrentHashMap<String, CombatPlayer>();
	String plugName = "[CombatEvents]";

	/*
	 * Defines a reason for the player entering combat
	 */
	public enum CombatReason {
		TARGETED_BY_MOB, DAMAGED_BY_MOB, ATTACKED_PLAYER, DAMAGED_BY_PLAYER, PET_TOOK_DAMAGE, PET_ATTACKED, CUSTOM
	}

	public enum LeaveCombatReason {
		QUIT, KICK, ERROR, TIMED, DEATH, CUSTOM
	}

	@Override
	public void onDisable() {
		log.info(plugName + " - v" + this.getDescription().getVersion() + " disabled!");
	}

	@Override
	public void onEnable() {

		Config.initialize(this);

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

	public LivingEntity getAttacker(LivingEntity  entity) {
		return killMap.get(entity);
	}

	public boolean removeKilled(LivingEntity  entity) {
		if (!killMap.containsKey(entity))
			return false;
		else
			killMap.remove(entity);

		return true;
	}

	public boolean addAttacker(LivingEntity killed, LivingEntity attacker) {
		if (killMap.containsKey(killed))
			return false;
		else
			killMap.put(killed, attacker);

		return true;
	}


	/**
	 * Add the player to the inCombat mapping with necessary info
	 * If the player is already in-combat and we are re-adding them
	 * only reset
	 * 
	 */
	public void enterCombat(Player player, CombatPlayer cPlayer) {
		if (!inCombat.containsKey(player)) {
			inCombat.put(player.getName(), cPlayer);
			player.sendMessage(Config.getEnterCombatMessage());
		} else {
			CombatPlayer thisCPlayer = inCombat.get(player);
			thisCPlayer.setCombatTime(System.currentTimeMillis());
			thisCPlayer.setInventory(cPlayer.getInventory());
			thisCPlayer.setReason(cPlayer.getReason());
		}
	}

	/**
	 * 
	 * @param player
	 * @return
	 */
	public CombatPlayer leaveCombat(Player player) {
		player.sendMessage(Config.getLeaveCombatMessage());
		return inCombat.remove(player.getName());
	}

	public CombatPlayer getCombatPlayer(String player) {
		return inCombat.get(player);
	}

	public CombatPlayer getCombatPlayer (Player player) {
		return getCombatPlayer(player.getName());
	}

	public boolean isInCombat(String player) {
		return inCombat.containsKey(player);
	}

	public boolean isInCombat(Player player) {
		return isInCombat(player.getName());
	}
}

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
    public AdminHandler admins = null;
    
	private static Map<LivingEntity, LivingEntity> killMap = new ConcurrentHashMap<LivingEntity, LivingEntity>();
	private static Map<String, CombatReason> inCombat = new ConcurrentHashMap<String, CombatReason>();
	String plugName = "[CombatEvents]";

	/*
	 * Defines a reason for the player entering combat
	 */
	public enum CombatReason {
		TARGETED_BY_MOB, DAMAGED_BY_MOB, DAMAGED_BY_PLAYER, PET_TOOK_DAMAGE, PET_ATTACKED
	}
	
	@Override
	public void onDisable() {
		log.info(plugName + " - v" + this.getDescription().getVersion() + " disabled!");
	}

	@Override
	public void onEnable() {

		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvent(Event.Type.ENTITY_DAMAGE, entityListener, Priority.Monitor, this);
		pm.registerEvent(Event.Type.ENTITY_DEATH, entityListener, Priority.Highest, this);
		
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
	
	public void setInCombat(String player, CombatReason reason) {
		inCombat.put(player, reason);
	}
	public void setInCombat(Player player, CombatReason reason) {
		setInCombat(player.getName(), reason);
	}
	
	public boolean isInCombat(String player) {
		return inCombat.containsKey(player);
	}
	
	public boolean isInCombat(Player player) {
		return isInCombat(player.getName());
	}
	
	public CombatReason getCombatReason(Player player) {
		return inCombat.get(player);
	}
	
	public CombatReason leaveCombat(String player) {
		return inCombat.remove(player);
	}
	
	public CombatReason leaveCombat(Player player) {
		return leaveCombat(player.getName());
	}
}

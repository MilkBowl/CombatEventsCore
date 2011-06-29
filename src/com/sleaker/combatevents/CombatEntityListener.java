package com.sleaker.combatevents;

import java.util.logging.Logger;

import net.milkbowl.administrate.AdminHandler;

import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Tameable;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageByProjectileEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityListener;
import org.bukkit.event.entity.EntityTargetEvent;

import com.sleaker.combatevents.CombatEventsCore.CombatReason;

public class CombatEntityListener extends EntityListener {
	private static Logger log = Logger.getLogger("Minecraft");

	private CombatEventsCore plugin;

	CombatEntityListener(CombatEventsCore plugin) {
		this.plugin = plugin;
	}


	public void OnEntityTarget(EntityTargetEvent event) {
		if (event.isCancelled() || !(event.getTarget() instanceof Player) || !(event.getEntity() instanceof Creature) )
			return;

		Player player = (Player) event.getTarget();
		Creature cEntity = (Creature) event.getEntity();
		//If this target is a Player and within the proper distance fire our EnterCombatEvent
		//TODO: Configure option to set Target Distance knocking a player in combat
		if (Utility.getDistance(player.getLocation(), cEntity.getLocation()) <= 20) {
			if (throwPlayerEnterCombatEvent(player)) 
				plugin.setInCombat(player, CombatReason.TARGETED_BY_MOB);
		}
	}

	public void onEntityDamage(EntityDamageEvent event) {
		//Disregard this event?
		if (event.isCancelled() || !isValidEntity(event.getEntity()))
			return;

		//Convert the entity.
		LivingEntity cEntity = (LivingEntity) event.getEntity();

		//Reasons to pop us into combat
		if (cEntity instanceof Tameable) {
			//Lets get the owner and make sure it's not null
			Player player = getOwner((Tameable)cEntity);
			if (player != null)
				//Try to throw the new event, and add the player if it doesn't get cancelled
				if (throwPlayerEnterCombatEvent(player))
					plugin.setInCombat(player, CombatReason.PET_TOOK_DAMAGE);
		} else if (event instanceof EntityDamageByEntityEvent) {
			EntityDamageByEntityEvent subEvent = (EntityDamageByEntityEvent) event;
			if (subEvent.getDamager() instanceof Tameable) {
				Player player = getOwner((Tameable) subEvent.getDamager());
				if (player != null)
					//Try to throw the new event, and add the player if it doesn't get cancelled
					if (throwPlayerEnterCombatEvent(player))
						plugin.setInCombat(player, CombatReason.PET_ATTACKED);
			}
		} else if (event instanceof EntityDamageByProjectileEvent) {
			EntityDamageByProjectileEvent subEvent = (EntityDamageByProjectileEvent) event;
			if (subEvent.getDamager() instanceof Tameable) {
				Player player = getOwner((Tameable) subEvent.getDamager());
				if (player != null)
					//Try to throw the new event, and add the player if it doesn't get cancelled
					if (throwPlayerEnterCombatEvent(player))
						plugin.setInCombat(player, CombatReason.PET_ATTACKED);
			}
		}

		//Lets only Log this event if it's going to kill the attacked
		if (cEntity.getHealth() - event.getDamage() <= 0 ) {
			if (event instanceof EntityDamageByEntityEvent) {
				EntityDamageByEntityEvent thisEvent = (EntityDamageByEntityEvent) event;

				if (thisEvent.getDamager() instanceof LivingEntity) {
					LivingEntity attacker = (LivingEntity) thisEvent.getDamager();
					//Check if this is a player and we have an admin plugin enabled - otherwise just add to the map
					if (attacker instanceof Player && plugin.admins != null) {
						if (!AdminHandler.isGod(((Player) attacker).getName()) && !AdminHandler.isInvisible(((Player) attacker).getName()))
							plugin.addAttacker(cEntity, attacker);
					} else 
						plugin.addAttacker(cEntity, attacker);

				}
			} else if (event instanceof EntityDamageByProjectileEvent ){
				EntityDamageByProjectileEvent thisEvent = (EntityDamageByProjectileEvent) event;
				if (thisEvent.getDamager() instanceof LivingEntity) {
					LivingEntity attacker = (LivingEntity) thisEvent.getDamager();
					//Check if this is a player and we have an admin plugin enabled - otherwise just add to the map
					if (attacker instanceof Player && plugin.admins != null) {
						if (!AdminHandler.isGod(((Player) attacker).getName()) && !AdminHandler.isInvisible(((Player) attacker).getName()))
							plugin.addAttacker(cEntity, attacker);
					} else
						plugin.addAttacker(cEntity, attacker);
				}
			}
		}	
	}

	public void onEntityDeath (EntityDeathEvent event) {
		//Reasons to disregard this event
		if ( !isValidEntity(event.getEntity()) )
			return;

		LivingEntity cEntity = (LivingEntity) event.getEntity();

		//Fire our event if the entity dying is in the killMap
		if ( plugin.getAttacker(cEntity) != null ) {
			log.info("[CombatEvents] - Starting new Custom Event, EntityKilledByEntity");
			EntityKilledByEntityEvent kEvent = new EntityKilledByEntityEvent(plugin.getAttacker(cEntity), cEntity, event.getDrops());
			plugin.getServer().getPluginManager().callEvent(kEvent);
			//Reset our drops.
			event.getDrops().addAll(kEvent.getDrops());
			plugin.removeKilled(cEntity);
		}
	}

	private boolean throwPlayerEnterCombatEvent(Player player) {
		//Make the new event object and call it.
		PlayerEnterCombatEvent cEvent = new PlayerEnterCombatEvent(player);
		plugin.getServer().getPluginManager().callEvent(cEvent);
		return !cEvent.isCancelled();
	}

	private Player getOwner (Tameable tEntity) {
		if (tEntity.isTamed())
			if (tEntity.getOwner() instanceof Player) 
				return (Player) tEntity.getOwner();

		return null;
	}

	private boolean isValidEntity (Entity thisEntity) {
		if ( !(thisEntity instanceof LivingEntity) )
			return false;

		return true;
	}
}

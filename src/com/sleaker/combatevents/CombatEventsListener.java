package com.sleaker.combatevents;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import com.sleaker.combatevents.events.EntityKilledByEntityEvent;
import com.sleaker.combatevents.events.PlayerEnterCombatEvent;
import com.sleaker.combatevents.events.PlayerLeaveCombatEvent;

public class CombatEventsListener extends CustomEventListener implements Listener {

	public CombatEventsListener() {

	}

	/**
	 * Called when an Entity kills another Entity
	 * 
	 * @param event
	 */
	public void onEntityKilledByEntityEvent(EntityKilledByEntityEvent event) {

	}

	/**
	 * Called when a player enters combat
	 * 
	 * @param event
	 */
	public void onPlayerEnterCombatEvent(PlayerEnterCombatEvent event) {

	}

	/**
	 * Called when a player leaves combat
	 * 
	 * @param event
	 */
	public void onPlayerLeaveCombatEvent(PlayerLeaveCombatEvent event) {

	}

	public void onCustomEvent(Event event) {
		if (event instanceof EntityKilledByEntityEvent) {
			onEntityKilledByEntityEvent((EntityKilledByEntityEvent) event);
		} else if (event instanceof PlayerEnterCombatEvent) {
			onPlayerEnterCombatEvent((PlayerEnterCombatEvent) event);
		} else if (event instanceof PlayerLeaveCombatEvent) {
			onPlayerLeaveCombatEvent((PlayerLeaveCombatEvent) event);
		}
	}
}

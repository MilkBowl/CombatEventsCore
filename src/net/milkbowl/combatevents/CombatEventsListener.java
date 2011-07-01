package net.milkbowl.combatevents;

import net.milkbowl.combatevents.events.EntityKilledByEntityEvent;
import net.milkbowl.combatevents.events.PlayerEnterCombatEvent;
import net.milkbowl.combatevents.events.PlayerLeaveCombatEvent;

import org.bukkit.event.CustomEventListener;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;


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

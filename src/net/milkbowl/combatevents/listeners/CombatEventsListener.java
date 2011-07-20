package net.milkbowl.combatevents.listeners;

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
	 * Overridable
	 * 
	 * @param event
	 */
	public void onEntityKilledByEntity(EntityKilledByEntityEvent event) {

	}

	/**
	 * Called when a player enters combat
	 * Overridable
	 * 
	 * @param event
	 */
	public void onPlayerEnterCombat(PlayerEnterCombatEvent event) {

	}

	/**
	 * Called when a player leaves combat
	 * Overridable
	 * 
	 * @param event
	 */
	public void onPlayerLeaveCombat(PlayerLeaveCombatEvent event) {

	}

	/**
	 * This is the custom event handler, do not override this
	 * if you are implementing the custom events into your plugin.
	 * 
	 */
	public void onCustomEvent(Event event) {
		if (event instanceof EntityKilledByEntityEvent) {
			onEntityKilledByEntity((EntityKilledByEntityEvent) event);
		} else if (event instanceof PlayerEnterCombatEvent) {
			onPlayerEnterCombat((PlayerEnterCombatEvent) event);
		} else if (event instanceof PlayerLeaveCombatEvent) {
			onPlayerLeaveCombat((PlayerLeaveCombatEvent) event);
		}
	}
}

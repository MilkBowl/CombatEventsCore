package net.milkbowl.combatevents.events;

import net.milkbowl.combatevents.CombatReason;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;


/**
 * This event is triggered whenever a player enters combat, such as when being damaged, or attacking
 * another creature.
 * 
 * @author sleak
 *
 */
@SuppressWarnings("serial")
public class PlayerEnterCombatEvent extends Event {

	private Player player;
	private CombatReason reason;

	public PlayerEnterCombatEvent(Player player, CombatReason reason) {
		super("PlayerEnterCombatEvent");
		this.player = player;
		this.reason = reason;
	}

	/**
	 * gets the Player that is entering combat.
	 * 
	 * @return the Player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * Gets the reason that this player is entering combat
	 * 
	 * 
	 * @return a CombatReason
	 */
	public CombatReason getReason() {
		return this.reason;
	}
}

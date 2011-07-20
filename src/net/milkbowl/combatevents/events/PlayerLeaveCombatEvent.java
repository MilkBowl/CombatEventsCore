package net.milkbowl.combatevents.events;

import net.milkbowl.combatevents.CombatReason;
import net.milkbowl.combatevents.LeaveCombatReason;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;


@SuppressWarnings("serial")
public class PlayerLeaveCombatEvent extends Event implements Cancellable {
	private boolean cancel = false;
	Player player;
	LeaveCombatReason leaveReason;
	CombatReason[] reasons;

	public PlayerLeaveCombatEvent(Player player, LeaveCombatReason leaveReason, CombatReason[] reasons) {
		super("PlayerLeaveCombatEvent");
		this.player = player;
		this.leaveReason = leaveReason;
		this.reasons = reasons;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	/**
	 * This event can only be cancelled if a player leaves combat
	 * because the timer expired, or because the event was generated through another plugin.
	 * 
	 * 
	 * @param boolean cancel
	 */
	public void setCancelled(boolean cancel) {
		if (leaveReason.equals(LeaveCombatReason.TIMED) || leaveReason.equals(LeaveCombatReason.CUSTOM))
			this.cancel = cancel;
	}

	/**
	 * Gets the reason that the player is leaving combat.
	 * 
	 * @return LeaveCombatReason
	 */
	public LeaveCombatReason getReason() {
		return leaveReason;
	}

	/**
	 * Gets the player associated with the event
	 * 
	 * @return Player
	 */
	public Player getPlayer() {
		return player;
	}
	
	/**
	 * Gets an array of all reasons that the player entered combat with.
	 * 
	 * 
	 * @return array of CombatReason
	 */
	public CombatReason[] getCombatReasons() {
		return reasons;
	}
}

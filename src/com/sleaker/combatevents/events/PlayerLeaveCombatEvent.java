package com.sleaker.combatevents.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import com.sleaker.combatevents.CombatEventsCore.LeaveCombatReason;

@SuppressWarnings("serial")
public class PlayerLeaveCombatEvent extends Event implements Cancellable {
	private boolean cancel = false;
	Player player;
	LeaveCombatReason reason;

	public PlayerLeaveCombatEvent(Player player, LeaveCombatReason reason) {
		super("PlayerLeaveCombatEvent");
		this.player = player;
		this.reason = reason;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	/**
	 * Make sure to only allow canceling this event if the player is still connected to the server.
	 * 
	 */
	public void setCancelled(boolean cancel) {
		if (reason.equals(LeaveCombatReason.TIMED) || reason.equals(LeaveCombatReason.CUSTOM))
			this.cancel = cancel;
	}

	public LeaveCombatReason getReason() {
		return reason;
	}

	public void setReason(LeaveCombatReason reason) {
		this.reason = reason;
	}

	public Player getPlayer() {
		return player;
	}
}

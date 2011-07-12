package net.milkbowl.combatevents.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import net.milkbowl.combatevents.CombatEventsCore.CombatReason;
import net.milkbowl.combatevents.CombatEventsCore.LeaveCombatReason;

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
	 * Make sure to only allow canceling this event if the player is still connected to the server.
	 * 
	 */
	public void setCancelled(boolean cancel) {
		if (leaveReason.equals(LeaveCombatReason.TIMED) || leaveReason.equals(LeaveCombatReason.CUSTOM))
			this.cancel = cancel;
	}

	public LeaveCombatReason getReason() {
		return leaveReason;
	}

	public void setReason(LeaveCombatReason reason) {
		this.leaveReason = reason;
	}

	public Player getPlayer() {
		return player;
	}
	
	public CombatReason[] getCombatReasons() {
		return reasons;
	}
}

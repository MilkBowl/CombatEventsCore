package com.sleaker.combatevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

import com.sleaker.combatevents.CombatEventsCore.CombatReason;


@SuppressWarnings("serial")
public class PlayerEnterCombatEvent extends Event implements Cancellable {
	
	private Player player;
	private boolean cancel = false;
	private CombatReason reason;
	
	PlayerEnterCombatEvent(Player player, CombatReason reason) {
		super("PlayerEnterCombatEvent");
		this.player = player;
		this.reason = reason;
	}

	public Player getPlayer() {
		return player;
	}
	
	public CombatReason getReason() {
		return this.reason;
	}

	@Override
	public boolean isCancelled() {
		return cancel;
	}

	@Override
	public void setCancelled(boolean cancel) {
		this.cancel = cancel;
	}
	
}

package com.sleaker.combatevents;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;


@SuppressWarnings("serial")
public class PlayerEnterCombatEvent extends Event implements Cancellable {
	
	private Player player;
	private boolean cancel = false;
	
	PlayerEnterCombatEvent(Player player) {
		super("PlayerEnterCombatEvent");
		this.player = player;
	}

	public Player getPlayer() {
		return player;
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

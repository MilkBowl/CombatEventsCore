package net.milkbowl.combatevents.events;

import net.milkbowl.combatevents.CombatReason;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;



@SuppressWarnings("serial")
public class PlayerEnterCombatEvent extends Event {

	private Player player;
	private CombatReason reason;

	public PlayerEnterCombatEvent(Player player, CombatReason reason) {
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
}

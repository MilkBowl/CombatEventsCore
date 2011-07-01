package net.milkbowl.combatevents.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import net.milkbowl.combatevents.CombatEventsCore.CombatReason;


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

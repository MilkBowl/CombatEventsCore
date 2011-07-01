package net.milkbowl.combatevents.tasks;

import java.util.concurrent.Callable;

import net.milkbowl.combatevents.events.PlayerLeaveCombatEvent;

import org.bukkit.entity.Player;

import net.milkbowl.combatevents.CombatEventsCore;
import net.milkbowl.combatevents.CombatEventsCore.LeaveCombatReason;

public class LeaveCombatSync implements Callable<Boolean> {

	Player player;
	LeaveCombatReason reason;
	CombatEventsCore plugin;

	public LeaveCombatSync(Player player, LeaveCombatReason reason, CombatEventsCore plugin) {
		this.reason = reason;
		this.player = player;
		this.plugin = plugin;
	}

	@Override
	public Boolean call() throws Exception {
		PlayerLeaveCombatEvent event = new PlayerLeaveCombatEvent(player, reason);
		plugin.getServer().getPluginManager().callEvent(event);
		if (!event.isCancelled()) 
			plugin.leaveCombat(player);

		return event.isCancelled();
	}
}

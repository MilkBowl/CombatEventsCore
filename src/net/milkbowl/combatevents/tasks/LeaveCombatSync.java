package net.milkbowl.combatevents.tasks;

import java.util.concurrent.Callable;

import net.milkbowl.combatevents.events.PlayerLeaveCombatEvent;

import org.bukkit.entity.Player;

import net.milkbowl.combatevents.CombatEventsCore;
import net.milkbowl.combatevents.CombatReason;
import net.milkbowl.combatevents.LeaveCombatReason;

public class LeaveCombatSync implements Callable<Boolean> {

	Player player;
	LeaveCombatReason reason;
	CombatEventsCore plugin;
	CombatReason[] cReasons;

	public LeaveCombatSync(Player player, LeaveCombatReason reason, CombatReason[] cReasons, CombatEventsCore plugin) {
		this.reason = reason;
		this.player = player;
		this.plugin = plugin;
		this.cReasons = cReasons;
	}

	@Override
	public Boolean call() throws Exception {
		PlayerLeaveCombatEvent event = new PlayerLeaveCombatEvent(player, reason, cReasons);
		plugin.getServer().getPluginManager().callEvent(event);
		if (!event.isCancelled()) 
			plugin.leaveCombat(player);

		return event.isCancelled();
	}
}

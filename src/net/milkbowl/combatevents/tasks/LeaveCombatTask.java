package net.milkbowl.combatevents.tasks;

import java.util.concurrent.Future;

import org.bukkit.entity.Player;

import net.milkbowl.combatevents.CombatEventsCore;
import net.milkbowl.combatevents.CombatEventsCore.CombatReason;
import net.milkbowl.combatevents.CombatEventsCore.LeaveCombatReason;

public class LeaveCombatTask implements Runnable {
	Player player;
	CombatEventsCore plugin;
	LeaveCombatReason reason;
	CombatReason[] cReasons;

	public LeaveCombatTask(Player player, LeaveCombatReason reason, CombatReason[] cReasons, CombatEventsCore plugin) {
		this.plugin = plugin;
		this.player = player;
		this.reason = reason;
		this.cReasons = cReasons;
	}

	@Override
	public void run() {
		if (plugin.isInCombat(player)) {
			Future<Boolean> cancel = plugin.getServer().getScheduler().callSyncMethod(plugin, new LeaveCombatSync(player, reason, cReasons, plugin));
			try {
				if (!cancel.get())
					return;
				else {
					//Player was not allowed to leave combat so we need to re-create a new task to force them to try leaving combat again.
					plugin.getCombatPlayer(player).setTaskId(plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new LeaveCombatTask(player, LeaveCombatReason.TIMED, cReasons, plugin), 20));
				}
			} catch (Exception e) {

			}
		}
	}
}
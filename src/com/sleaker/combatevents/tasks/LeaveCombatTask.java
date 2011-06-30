package com.sleaker.combatevents.tasks;

import java.util.concurrent.Future;

import org.bukkit.entity.Player;

import com.sleaker.combatevents.CombatEventsCore;
import com.sleaker.combatevents.CombatEventsCore.LeaveCombatReason;

public class LeaveCombatTask implements Runnable {
	Player player;
	CombatEventsCore plugin;
	LeaveCombatReason reason;
	
	public LeaveCombatTask(Player player, LeaveCombatReason reason, CombatEventsCore plugin) {
		this.plugin = plugin;
		this.player = player;
		this.reason = reason;
	}
	
	@Override
	public void run() {
		if (plugin.isInCombat(player)) {
			Future<Boolean> cancel = plugin.getServer().getScheduler().callSyncMethod(plugin, new LeaveCombatSync(player, reason, plugin));
			try {
				if (!cancel.get())
					return;
				else {
					
					//TODO: Player was not allowed to leave combat so we need to re-create a new task to force them to try leaving combat again.
					
				}
			} catch (Exception e) {
				
			}
		}
	}
}
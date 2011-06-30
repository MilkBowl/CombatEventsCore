package com.sleaker.combatevents;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.sleaker.combatevents.CombatEventsCore.LeaveCombatReason;

public class CombatPlayerListener extends PlayerListener {
	CombatEventsCore plugin;

	CombatPlayerListener(CombatEventsCore plugin) {
		this.plugin = plugin;
	}

	public void onPlayerQuit (PlayerQuitEvent event) {
		//Cleanup the player if they are kicked.
		if (plugin.isInCombat(event.getPlayer())) {
			if (throwPlayerLeaveCombatEvent(event.getPlayer(), LeaveCombatReason.QUIT))
				plugin.leaveCombat(event.getPlayer());
		}
	}

	public void onPlayerKick (PlayerKickEvent event) {
		if (plugin.isInCombat(event.getPlayer()))
			if (throwPlayerLeaveCombatEvent(event.getPlayer(), LeaveCombatReason.KICK))
				plugin.leaveCombat(event.getPlayer());
	}
	
	private boolean throwPlayerLeaveCombatEvent(Player player, LeaveCombatReason reason) {
		PlayerLeaveCombatEvent event = new PlayerLeaveCombatEvent(player, reason);
		plugin.getServer().getPluginManager().callEvent(event);
		//Return if the event was successful
		return !event.isCancelled();
	}
}

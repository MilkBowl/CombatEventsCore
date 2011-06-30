package com.sleaker.combatevents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sleaker.combatevents.CombatEventsCore.CombatReason;

public class CombatPlayer {
	private Player player;
	private CombatReason reason;
	private ItemStack[] inventory;
	private long combatTime;

	CombatPlayer(Player player, CombatReason reason) {
		this.player = player;
		this.inventory = player.getInventory().getContents();
		this.reason = reason;
		this.combatTime = System.currentTimeMillis();
	}
	
	public CombatReason getReason() {
		return reason;
	}

	public void setReason(CombatReason reason) {
		this.reason = reason;
	}

	public ItemStack[] getInventory() {
		return inventory;
	}

	public void setInventory(ItemStack[] inventory) {
		this.inventory = inventory;
	}

	public Player getPlayer() {
		return player;
	}

	public long getCombatTime() {
		return combatTime;
	}

	public void setCombatTime(long combatTime) {
		this.combatTime = combatTime;
	}	
}

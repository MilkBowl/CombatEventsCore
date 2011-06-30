package com.sleaker.combatevents;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sleaker.combatevents.CombatEventsCore.CombatReason;

public class CombatPlayer {
	private Player player;
	private CombatReason reason;
	private int taskId;
	private ItemStack[] inventory;

	CombatPlayer(Player player, CombatReason reason) {
		this.player = player;
		this.inventory = player.getInventory().getContents();
		this.reason = reason;
	}
	
	public CombatReason getReason() {
		return reason;
	}

	public void setReason(CombatReason reason) {
		this.reason = reason;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
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
}

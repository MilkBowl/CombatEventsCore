package net.milkbowl.combatevents;

import net.milkbowl.combatevents.tasks.LeaveCombatTask;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.combatevents.CombatEventsCore.CombatReason;
import net.milkbowl.combatevents.CombatEventsCore.LeaveCombatReason;

public class CombatPlayer {
	private Player player;
	private CombatReason reason;
	private ItemStack[] inventory;
	private int taskId;

	CombatPlayer(Player player, CombatReason reason, CombatEventsCore plugin) {
		this.player = player;
		this.inventory = player.getInventory().getContents();
		this.reason = reason;
		//if we create a new object always force the player into combat so make a new scheduler for leaving combat
		taskId = plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new LeaveCombatTask(player, LeaveCombatReason.TIMED, plugin), Config.getCombatTime() * 20);
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

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}	
}

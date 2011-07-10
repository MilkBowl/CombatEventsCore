package net.milkbowl.combatevents;

import java.util.HashMap;
import java.util.Map;

import net.milkbowl.combatevents.tasks.LeaveCombatTask;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import net.milkbowl.combatevents.CombatEventsCore.CombatReason;
import net.milkbowl.combatevents.CombatEventsCore.LeaveCombatReason;

public class CombatPlayer {
	private Player player;
	private Map<Entity, CombatReason> reasons;
	private ItemStack[] inventory;
	private int taskId;

	public CombatPlayer(Player player, CombatReason reason, Entity entity, CombatEventsCore plugin) {
		this.player = player;
		this.inventory = player.getInventory().getContents();
		reasons = new HashMap<Entity, CombatReason>(2);
		reasons.put(entity, reason);
		//if we create a new object always force the player into combat so make a new scheduler for leaving combat
		taskId = plugin.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new LeaveCombatTask(player, LeaveCombatReason.TIMED, plugin), Config.getCombatTime() * 20);
	}

	public Map<Entity, CombatReason> getReasons() {
		return reasons;
	}

	public void addReason(Entity entity, CombatReason reason) {
		this.reasons.put(entity, reason);
	}
	
	public CombatReason getReason(Entity entity) {
		return this.reasons.get(entity);
	}

	public void removeReason(Entity entity) {
		this.reasons.remove(entity);
	}
	
	public void clearReasons() {
		this.reasons.clear();
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

package net.milkbowl.combatevents.events;

import java.util.List;

import net.milkbowl.combatevents.KillType;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("serial")
public class EntityKilledByEntityEvent extends Event {
	private LivingEntity attacker;
	private List<ItemStack> drops;
	private LivingEntity killed;
	private KillType killType;

	public EntityKilledByEntityEvent(LivingEntity attacker, LivingEntity killed, List<ItemStack> drops, KillType killType) {
		super ("EntityKilledByEntityEvent");
		this.attacker = attacker;
		this.killed = killed;
		this.killType = killType;
		this.drops = drops;	
	}

	public LivingEntity getAttacker() {
		return attacker;
	}

	public LivingEntity getKilled() {
		return killed;
	}

	public List<ItemStack> getDrops() {
		return drops;
	}

	public void setDrops(List<ItemStack> drops) {
		this.drops = drops;
	}
	
	public KillType getKillType() {
		return this.killType;
	}
}

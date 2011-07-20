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

	/**
	 * Gets the attacking entity object
	 * 
	 * @return entity that is attacking
	 */
	public LivingEntity getAttacker() {
		return attacker;
	}

	/**
	 * Gets the entity that is dying
	 * 
	 * @return dead entity
	 */
	public LivingEntity getKilled() {
		return killed;
	}

	/**
	 * Gets all the items the entity will drop once the event has finished.
	 *
	 * @return <List> ItemStack of entities drops
	 */
	public List<ItemStack> getDrops() {
		return drops;
	}

	/**
	 * Sets the drops for this entity
	 * Editing these drops will effect EntityKilledEvents drops directly for all
	 * standard EntityKilledEvents that come after
	 * 
	 * 
	 * @param drops
	 */
	public void setDrops(List<ItemStack> drops) {
		this.drops = drops;
	}

	/**
	 * Type of kill that this is considered.
	 * 
	 * @return the type of kill.
	 */
	public KillType getKillType() {
		return this.killType;
	}
}

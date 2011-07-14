package net.milkbowl.combatevents;

/*
 * Defines a reason for the player entering combat
 */
public enum CombatReason {
	TARGETED_BY_MOB, 
	DAMAGED_BY_MOB, 
	ATTACKED_MOB, 
	ATTACKED_PLAYER, 
	DAMAGED_BY_PLAYER, 
	PET_TOOK_DAMAGE, 
	PET_ATTACKED, 
	CUSTOM;
}

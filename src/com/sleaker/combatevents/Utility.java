package com.sleaker.combatevents;

import org.bukkit.Location;

public class Utility {
	
	public static double getDistance(Location loc1, Location loc2) {
		double xDistSq = Math.pow(loc1.getX() - loc2.getX(), 2);
		double yDistSq = Math.pow(loc1.getY() - loc2.getY(), 2);
		double zDistSq = Math.pow(loc1.getZ() - loc2.getZ(), 2);
		return Math.sqrt(xDistSq + yDistSq + zDistSq);
	}
}

package net.milkbowl.combatevents;

import org.bukkit.Location;
import org.bukkit.entity.Chicken;
import org.bukkit.entity.Cow;
import org.bukkit.entity.CreatureType;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Ghast;
import org.bukkit.entity.Giant;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Slime;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Squid;
import org.bukkit.entity.Wolf;
import org.bukkit.entity.Zombie;

public class Utility {

	public static double getDistance(Location loc1, Location loc2) {
		double xDistSq = Math.pow(loc1.getX() - loc2.getX(), 2);
		double yDistSq = Math.pow(loc1.getY() - loc2.getY(), 2);
		double zDistSq = Math.pow(loc1.getZ() - loc2.getZ(), 2);
		return Math.sqrt(xDistSq + yDistSq + zDistSq);
	}

	public static CreatureType getCType(LivingEntity cEntity) {
		if (cEntity instanceof Chicken)
			return CreatureType.CHICKEN;
		else if (cEntity instanceof Cow)
			return CreatureType.COW;
		else if (cEntity instanceof Creeper)
			return CreatureType.CREEPER;
		else if (cEntity instanceof Ghast)
			return CreatureType.GHAST;
		else if (cEntity instanceof Giant)
			return CreatureType.GIANT;
		else if (cEntity instanceof Pig)
			return CreatureType.PIG;
		else if (cEntity instanceof PigZombie)
			return CreatureType.PIG_ZOMBIE;
		else if (cEntity instanceof Sheep)
			return CreatureType.SHEEP;
		else if (cEntity instanceof Skeleton)
			return CreatureType.SKELETON;
		else if (cEntity instanceof Slime)
			return CreatureType.SLIME;
		else if (cEntity instanceof Spider)
			return CreatureType.SPIDER;
		else if (cEntity instanceof Squid)
			return CreatureType.SQUID;
		else if (cEntity instanceof Wolf)
			return CreatureType.WOLF;
		else if (cEntity instanceof Zombie)
			return CreatureType.ZOMBIE;
		else
			return null;
	}

	public static boolean isMonster(CreatureType cType) {
		if (cType.equals(CreatureType.CREEPER) || cType.equals(CreatureType.GHAST) || cType.equals(CreatureType.GIANT) || cType.equals(CreatureType.PIG_ZOMBIE) || cType.equals(CreatureType.SKELETON) || cType.equals(CreatureType.SLIME) || cType.equals(CreatureType.ZOMBIE) )
			return true;
		else
			return false;
	}
}

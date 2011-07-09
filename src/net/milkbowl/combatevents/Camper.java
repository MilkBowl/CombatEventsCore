package net.milkbowl.combatevents;

import org.bukkit.Location;

public class Camper {

	private Location spawnerLoc;
	private int kills = 1;
	private int taskId;

	public Camper(Location spawnerLoc) {
		this.spawnerLoc = spawnerLoc;
	}

	public void addKill() {
		this.kills += 1;
	}

	public Location getSpawner() {
		return this.spawnerLoc;
	}

	public int getKills() {
		return kills;
	}

	public int getCampTask() {
		return taskId;
	}

	public void setCampTask(int id) {
		this.taskId = id;
	}
	
}

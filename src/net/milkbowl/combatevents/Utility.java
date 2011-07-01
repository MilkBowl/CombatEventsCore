package net.milkbowl.combatevents;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Utility {

	public static double getDistance(Location loc1, Location loc2) {
		double xDistSq = Math.pow(loc1.getX() - loc2.getX(), 2);
		double yDistSq = Math.pow(loc1.getY() - loc2.getY(), 2);
		double zDistSq = Math.pow(loc1.getZ() - loc2.getZ(), 2);
		return Math.sqrt(xDistSq + yDistSq + zDistSq);
	}

	public class SendMessage implements Runnable {
		String player;
		String message;
		CombatEventsCore plugin;

		SendMessage (String player, String message, CombatEventsCore plugin) {
			this.player = player;
			this.message = player;
			this.plugin = plugin;
		}

		@Override
		public void run() {
			Player player = plugin.getServer().getPlayer(this.player);
			if (player == null)
				return;
			else
				player.sendMessage(message);
		}
	}
}

package com.valorin.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;

public class ToLogLocation {
  public static void to(Player winner,Player loser,Location winnerLocation,Location loserLocation) {
	if (winnerLocation == null || loserLocation == null || winner == null || loser == null) {
	  return;
	}
	new BukkitRunnable() {
	  @Override
	  public void run() {
	    winner.teleport(winnerLocation);
	    loser.teleport(loserLocation);
	  }
	}.runTask(Dantiao.getInstance());
  }
}

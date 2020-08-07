package com.valorin.teleport;

import static com.valorin.configuration.DataFile.areas;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;

public class ToLobby {
  public static void to(Player p) {
	if (areas.getString("Dantiao-LobbyPoint.World") == null) {
	  return;
	}
	if (p == null) {
	  return;
	}
	new BukkitRunnable() {
	  @Override
	  public void run() {
	    Bukkit.dispatchCommand(p, "dt lobby");
	  }
	}.runTask(Dantiao.getInstance());
  }
}

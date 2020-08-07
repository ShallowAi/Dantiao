package com.valorin.event.game;

import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;
import com.valorin.teleport.ToLobby;

public class CompulsoryTeleport implements Listener {
	public static Map<String,Location> players = new HashMap<String,Location>();
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void onRespawn(PlayerRespawnEvent e) {
	  Player p = e.getPlayer();
	  String pn = p.getName();
	  if (players.containsKey(pn)) {
		  new BukkitRunnable() {
			  public void run() {
				  new BukkitRunnable() {
					  public void run() {
						  if (areas.getString("Dantiao-LobbyPoint.World") != null) {
						      ToLobby.to(p);
						      sm("&b已将你带回单挑大厅！",p);
						      if (p != null && !p.isDead()) {
						          sm("&b已将你带回单挑大厅！",p);
						      }
						  } else {
						      p.teleport(players.get(pn));
						  }
						  players.remove(pn);
					  }
				  }.runTask(Dantiao.getInstance());
			  }
		  }.runTaskLaterAsynchronously(Dantiao.getInstance(), 20);
	  }
    }
}

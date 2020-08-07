package com.valorin.event.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.valorin.Dantiao;
import com.valorin.arenas.Arena;
import com.valorin.arenas.ArenasManager;

import static com.valorin.configuration.languagefile.MessageSender.sm;

public class Teleport implements Listener {
	@EventHandler
	public void onLeaveGameWorld(PlayerTeleportEvent e) {//突然传送到别的世界去了
	  Player p = e.getPlayer();
	  String pn = p.getName();
	  ArenasManager ah = Dantiao.getInstance().getArenasHandler();
	  if (ah.isPlayerBusy(pn)) {
		Arena arena = ah.getArena(ah.getPlayerOfArena(pn));
		if (!arena.canTeleport()) {
		  if (!e.getTo().getWorld().equals(p.getLocation().getWorld())) {
		    e.setCancelled(true);
		    sm("&c[x]发生非法传送，已制止",p);
		  }
		}
	  }
    }
}

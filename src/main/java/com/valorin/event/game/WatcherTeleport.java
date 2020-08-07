package com.valorin.event.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.valorin.Dantiao;
import com.valorin.arenas.Arena;
import com.valorin.arenas.ArenasManager;

import static com.valorin.configuration.languagefile.MessageSender.sm;

public class WatcherTeleport implements Listener {
	@EventHandler
	public void onLeaveGameWorld(PlayerTeleportEvent e) {//观战者在观赛过程中自己打指令(或其他方法)发生了传送 
	  Player p = e.getPlayer();
	  String pn = p.getName();
	  ArenasManager ah = Dantiao.getInstance().getArenasHandler();
	  if (ah.getArena(ah.getWatcherOfArena(pn)) != null)
	  {
		Arena arena = ah.getArena(ah.getWatcherOfArena(pn));
		if (arena.getEnable())
		{
		  if (!arena.getWatchersTeleport()) {
		    arena.removeWatcher(pn);
		    sm("&7由于传送，你退出了观战状态...",p);
		  }
		}
	  }
    }
}

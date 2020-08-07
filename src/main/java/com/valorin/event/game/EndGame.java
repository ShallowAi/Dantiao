package com.valorin.event.game;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.valorin.Dantiao;
import com.valorin.arenas.Arena;
import com.valorin.arenas.ArenasManager;
import com.valorin.arenas.Finisher;

public class EndGame implements Listener {
	//一方选手死亡，无论因素，判定“非平局结束比赛”
	@EventHandler(priority=EventPriority.HIGHEST)
    public void onDTDeath(PlayerDeathEvent e) {
	  Player p = e.getEntity();
	  String pn = p.getName();
	  ArenasManager ah = Dantiao.getInstance().getArenasHandler();
	  if (ah.isPlayerBusy(pn)) {//属于比赛玩家，对方取胜
		String an = ah.getPlayerOfArena(pn);
		Arena a = ah.getArena(an);
		String winner = a.getTheOtherPlayer(pn);
		Finisher.normalEnd(an, winner, pn, false);
	  }
    }
	//一方玩家下线，无论因素，判定“非平局结束比赛”
	@EventHandler
    public void onDTLeaveGame(PlayerQuitEvent e) {
	  Player p = e.getPlayer();
	  String pn = p.getName();
	  ArenasManager ah = Dantiao.getInstance().getArenasHandler();
	  if (ah.isPlayerBusy(pn)) {//属于比赛玩家，对方取胜
		String an = ah.getPlayerOfArena(pn);
		Arena a = ah.getArena(an);
		String winner = a.getTheOtherPlayer(pn);
		sm("&b对方下线了！系统判定你胜利！",Bukkit.getPlayerExact(winner));
		Finisher.normalEnd(an, winner, pn, false);
	  }
    }
}

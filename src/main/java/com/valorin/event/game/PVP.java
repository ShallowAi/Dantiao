package com.valorin.event.game;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.valorin.Dantiao;
import com.valorin.arenas.Arena;
import com.valorin.arenas.ArenasManager;

public class PVP implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void pvp(EntityDamageByEntityEvent e) {
	  if (e.getEntity() instanceof Player) {
		Player p = (Player)e.getEntity();
		String pn = p.getName();
		ArenasManager ah = Dantiao.getInstance().getArenasHandler();
		Arena a = ah.getArena(ah.getPlayerOfArena(pn));
		if (!ah.isPlayerBusy(pn)) {
		  return;
		}
		String theother = a.getTheOtherPlayer(pn);
		if (e.getDamager() instanceof Player) {
		  if (((Player)e.getDamager()).getName().equals(theother)) {//锁定对打双方
			boolean isp1 = a.isp1(theother);
			a.addHit(isp1);
			if (a.getHit(isp1) == 2) {
			  a.addExp(isp1, 1);
			}
			if (a.getHit(isp1) == 3) {
			  a.addExp(isp1, 2);
			}
			if (a.getHit(isp1) == 4) {
			  a.addExp(isp1, 3);
			}
			if (a.getHit(isp1) >= 5) {
			  a.addExp(isp1, 4);
			}
			
			a.addDamage(isp1, e.getDamage());
			if (e.getDamage() > a.getMaxDamage(isp1)) {
			  a.setMaxDamage(isp1, e.getDamage());
			}
		  }
		}
	  }
	}
}

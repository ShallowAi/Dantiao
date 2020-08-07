package com.valorin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.valorin.Dantiao;
import com.valorin.dan.DansHandler;

public class Chat implements Listener{
	@EventHandler
	public void showDan(AsyncPlayerChatEvent e) {
      if (Dantiao.getInstance().getConfig().getBoolean("BanShowingDan")) {
    	return;
      }
	  Player p = e.getPlayer();
	  String pn = p.getName();
	  DansHandler dh = Dantiao.getInstance().getDansHandler();
	  e.setFormat(dh.getPlayerDan(pn).getDanName().replace("&", "§")+e.getFormat());
	}
}

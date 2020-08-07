package com.valorin.event.sign;

import static com.valorin.configuration.languagefile.MessageSender.gm;

import org.bukkit.Bukkit;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
public class ClickSign implements Listener{
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
	  if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
	    if ((e.getClickedBlock().getState() instanceof Sign)) {
	      Sign s = (Sign)e.getClickedBlock().getState();
	      String content = s.getLine(0);
		  if (content.equals("§9"+gm("[单挑匹配]",null))) {
			Bukkit.dispatchCommand(e.getPlayer(), "dt st");
		  }
	    }
	  }
	}
}

package com.valorin.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.valorin.Dantiao;

public class CommandTypeAmountStatistics implements Listener {
	@EventHandler
	public void onCommandTypeAmountStatistics(PlayerCommandPreprocessEvent e) {
	  if (e.getMessage().startsWith("/dt ") || e.getMessage().equals("/dt")) {
		Dantiao.getInstance().getCommandTypeAmountData().add();
	  }
    }
}

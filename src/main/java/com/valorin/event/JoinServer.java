package com.valorin.event;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.valorin.Dantiao.getInstance;

public class JoinServer implements Listener{
	@EventHandler
	public void refreshPlayerSet(PlayerJoinEvent e) {
		Player player = e.getPlayer();
	    getInstance().getPlayerSet().checkPlayer(player);
	    getInstance().getEnergy().checkPlayer(player.getName());
	}
}

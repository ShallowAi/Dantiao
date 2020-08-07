package com.valorin.event.requests;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.valorin.Dantiao;

public class LeaveServer implements Listener {
	@EventHandler
    public void onDTLeaveGame(PlayerQuitEvent e) {
	  String pn = e.getPlayer().getName();
	  Dantiao.getInstance().getRequestsHandler().clearRequests(pn,1,null);
    }
}

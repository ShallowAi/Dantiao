package com.valorin.event;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import com.valorin.Dantiao;
import com.valorin.event.game.CompulsoryTeleport;
import com.valorin.event.game.EndGame;
import com.valorin.event.game.Move;
import com.valorin.event.game.PVP;
import com.valorin.event.game.Protection;
import com.valorin.event.game.Teleport;
import com.valorin.event.game.TypeCommands;
import com.valorin.event.gui.RecordsGUI;
import com.valorin.event.gui.ShopGUI;
import com.valorin.event.gui.StartGUI;
import com.valorin.event.requests.LeaveServer;
import com.valorin.event.sign.ClickSign;
import com.valorin.event.sign.CreateSign;
import com.valorin.event.ClickPlayer;

public class EventRegister {
  public static void registerEvents() {
	Listener[] listeners = {new EndGame(),
			                new Protection(),
			                new PVP(),
			                new Teleport(),
			                new ArenaCreate(),
			                new Chat(),
			                new RecordsGUI(),
			                new ShopGUI(),
			                new StartGUI(),
			                new TypeCommands(),
			                new Move(),
			                new JoinServer(),
			                new LeaveServer(),
			                new ClickSign(),
			                new CreateSign(),
			                new CheckVersion(),
			                new CommandTypeAmountStatistics(),
			                new CompulsoryTeleport(),
			                new ClickPlayer()};
	for (Listener listener : listeners) {
	  Bukkit.getPluginManager().registerEvents(listener, Dantiao.getInstance());
	}
  }
}

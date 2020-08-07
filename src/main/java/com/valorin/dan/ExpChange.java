package com.valorin.dan;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.valorin.api.event.DanExpChangedEvent;

import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.savepd;

public class ExpChange {
  public static void changeExp(Player player,int exp) {
	int before = pd.getInt(player.getName());
	DanExpChangedEvent event = new DanExpChangedEvent(player,before,exp);
	Bukkit.getServer().getPluginManager().callEvent(event);
	if (!event.isCancelled()) {
	  pd.set(player.getName()+".Exp",exp);
	  savepd();
	}
  }
}

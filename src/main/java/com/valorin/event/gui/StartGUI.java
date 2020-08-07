 package com.valorin.event.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import com.valorin.Dantiao;
import com.valorin.arenas.Starter;
import com.valorin.inventory.special.Start;
import com.valorin.inventory.special.StartInvHolder;
import com.valorin.queue.SearchingQueue;

public class StartGUI implements Listener {
	@EventHandler
	public void page(InventoryClickEvent e) {
	  Player opener = (Player) e.getWhoClicked();
	  String opener_name = opener.getName();
	  Inventory inventory = e.getInventory();
	  if (inventory == null) {
		return;
	  }
	  if (!(inventory.getHolder() instanceof StartInvHolder)) {
		return;
	  }
	  StartInvHolder holder = (StartInvHolder) inventory.getHolder();
	  e.setCancelled(true);
	  if (e.getRawSlot() != 13) {
		return;
	  }
	  if (holder.getStatus() == 0) {
		SearchingQueue queue = Dantiao.getInstance().getSearchingQueue();
		String waiter_name = queue.getWaiter();
	    if (waiter_name == null) {
		  queue.setWaiter(opener_name);
	      Start.startSearch(holder);
	    } else {
	      queue.setWaiter(null);
		  Player waiter = Bukkit.getPlayerExact(waiter_name);
		  new Starter(opener,waiter,null,null);
	    }
	  }
	}
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
	  Inventory inventory = e.getInventory();
	  if (inventory == null) {
		return;
	  }
	  if (!(inventory.getHolder() instanceof StartInvHolder)) {
		return;
	  }
	  StartInvHolder holder = (StartInvHolder) inventory.getHolder();
	  SearchingQueue queue = Dantiao.getInstance().getSearchingQueue();
	  if (holder.getStatus() == 1) {
		queue.setWaiter(null);
		Start.finishSearch(holder, true);
	  }
	}
}

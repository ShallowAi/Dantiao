package com.valorin.inventory.special;

import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.valorin.Dantiao;
import com.valorin.itemstack.GUIItems;

public class Start {
  public static void openInv(String opener_name) {
	Player opener = Bukkit.getPlayerExact(opener_name);
	StartInvHolder holder = new StartInvHolder(opener_name);
	Inventory inventory = Bukkit.createInventory(holder, 27, 
				gm("&0&l全服匹配 &9&l[right]",opener));
	holder.setInventory(inventory);
	ItemStack glass = GUIItems.getGlass(0);
	for (int i = 0;i < 27;i++) {
      if (i != 13) {
    	inventory.setItem(i, glass);
      }
	}
	inventory.setItem(13, GUIItems.getStart(opener_name));
	opener.openInventory(inventory);
	holder.setStatus(0);
  }
  
  public static void startSearch(StartInvHolder holder) {
	if (holder.getTimer() != null) {
	  return;
	}
	holder.setStatus(1);
	refresh(holder);
	BukkitTask timer = new BukkitRunnable() {
	  public void run() {
		holder.setSec(holder.getSec() + 1);
	    refresh(holder);
	  }
	}.runTaskTimerAsynchronously(Dantiao.getInstance(), 20, 20);
	holder.setTimer(timer);
	ItemStack glass = GUIItems.getGlass(15);
	for (int i = 0;i < 27;i++) {
      if (i != 13) {
        holder.getInventory().setItem(i, glass);
      }
	}
  }
  
  public static void finishSearch(StartInvHolder holder,boolean isActive) {
	if (holder.getTimer() == null) {
      return;
	}
	Player opener = holder.getOpener();
	holder.getTimer().cancel();
	holder.setTimer(null);
	if (isActive) {
	  sm("&7已中断匹配...",opener);
	}
  }
  
  public static void refresh(StartInvHolder holder) {
    holder.getInventory().setItem(13, GUIItems.updataStart(holder));
  }
}

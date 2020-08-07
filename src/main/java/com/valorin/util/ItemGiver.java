package com.valorin.util;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemGiver {
  private boolean isreceive;
  private int freeSlot;
  
  public boolean getIsReceive() {
	return isreceive;
  }
  
  public ItemGiver(Player p,ItemStack item) {
	if (isFree(p)) {
	  p.getInventory().setItem(freeSlot,item);
	  isreceive = true;
	  sm("&a[v]物品已发送",p);
	} else {
	  isreceive = false;
	  sm("&c[x]背包满了，无法获取物品，请先为你的背包留出空位！",p);
	}
  }
  
  public boolean isFree(Player p) {
	Inventory inv = p.getInventory();
	for (int i = 0;i < 36;i++) {
	  if (inv.getItem(i) == null || inv.getItem(i).equals(Material.AIR)) {
		freeSlot = i;
		return true;
	  }
	}
	return false;
  }
}

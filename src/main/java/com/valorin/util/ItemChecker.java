package com.valorin.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemChecker {
    private boolean hasItem = false;
    
    public ItemChecker(Player p,String lore) {
      Inventory inv = p.getInventory();
  	  for (int slot = 0;slot < 36;slot++) {
  	    if (inv.getItem(slot) != null) {
  	  	  ItemStack itemStack = inv.getItem(slot);
  	  	  if (itemStack.hasItemMeta()) {
  	  		if (itemStack.getItemMeta().getLore() != null) {
  	  		  if (itemStack.getItemMeta().getLore().contains(lore.replace("&", "ยง"))) {
  	  			hasItem = true;
  	  			break;
  	  		  }
  	  		}
  	  	  }
  	  	}
  	  }
    }
    
    public ItemChecker(Player p,Material material) {
      Inventory inv = p.getInventory();
      for (int slot = 0;slot < 36;slot++) {
    	if (inv.getItem(slot) != null) {
    	  ItemStack itemStack = inv.getItem(slot);
    	  if (itemStack.getType().equals(material)) {
    		hasItem = true;
	  		break;
    	  }
    	}
      }
    }
    
    public boolean isHasItem() {
      return hasItem;
    }
}

package com.valorin.util;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class ItemTaker {
    private int slot = -1;
    private int amount = 0;
    
    public ItemTaker(Player p,String lore,int amount) {
      Inventory inv = p.getInventory();
	  for (int slot = 0;slot < 36;slot++) {
	    if (inv.getItem(slot) != null) {
	  	  ItemStack itemStack = inv.getItem(slot);
	  	  if (itemStack.hasItemMeta()) {
	  		if (itemStack.getItemMeta().getLore() != null) {
	  		  if (itemStack.getItemMeta().getLore().contains(lore)) {
	  			if (itemStack.getAmount() >= amount) {
	  			  this.slot = slot;
	  			  this.amount = amount;
	  			  break;
	  			}
	  		  }
	  		}
	  	  }
	  	}
	  }
    }
    
    public int getSlot() {
      return slot;
    }
    
    public void consume(Player p) {
      ItemStack item = p.getInventory().getItem(slot);
      
      ItemStack newItem = new ItemStack(item);
      if (item.getAmount() > amount) {
    	  newItem.setAmount(item.getAmount() - amount);
      } else {
    	  newItem.setType(Material.AIR);
      }
      
      p.getInventory().setItem(slot, newItem);
    }
}

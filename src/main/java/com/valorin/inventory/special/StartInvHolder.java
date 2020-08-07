package com.valorin.inventory.special;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.scheduler.BukkitTask;

public class StartInvHolder implements InventoryHolder {
	Inventory inventory;
	String opener_name;
	int status;//0代表静默，1代表搜寻中
	int sec;
	BukkitTask timer;
	
	public StartInvHolder(String opener_name) {
	  this.opener_name = opener_name;
	}
			
	@Override
	public Inventory getInventory() {
	  return inventory;
	}
	
	public void setInventory(Inventory inventory) {
	  this.inventory = inventory;
	}
	
	public String getOpenerName() {
      return opener_name;
	}
	
	public Player getOpener() {
	  return Bukkit.getPlayerExact(opener_name);
	}
	
	public int getStatus() {
	  return status;
	}
	
	public void setStatus(int status) {
	  this.status = status;
	}
	
	public int getSec() {
      return sec;
	}
	
	public void setSec(int sec) {
	  this.sec = sec;
	}
	
	public BukkitTask getTimer() {
	  return timer;
	}
	
	public void setTimer(BukkitTask timer) {
	  this.timer = timer;
	}
}

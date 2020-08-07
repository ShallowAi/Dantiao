package com.valorin.util;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;

public class SyncBroadcast {
    public static void bc(String message) {
    	new BukkitRunnable() {
    		public void run() {
    			try {
    			    Bukkit.broadcastMessage(message);
    			} catch (Exception e) { e.printStackTrace(); }
    		}
    	}.runTask(Dantiao.getInstance());
    }
}

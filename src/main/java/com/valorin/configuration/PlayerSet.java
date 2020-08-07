package com.valorin.configuration;

import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.pdFile;
import static com.valorin.configuration.DataFile.savepd;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerSet {
	private List<String> playerNameList = new ArrayList<String>();
	public List<String> get() {
		return playerNameList;
	}
	public PlayerSet() {
		playerNameList = new ArrayList<String>();
		if (pdFile.exists()) {
		    pd.getKeys(false).forEach(key->{playerNameList.add(key);});
		}
		
		checkAll();
    }
	
	private void checkAll() {
	    if (Bukkit.getOnlinePlayers().size() != 0) {
	        for (Player player : Bukkit.getOnlinePlayers()) {
	            if (!playerNameList.contains(player.getName())) {
	                pd.set(player.getName()+".Name", player.getName());
	                playerNameList.add(player.getName());
	            }
		    }
	        savepd();
	    }
	}
	
	public void checkPlayer(Player player) {
		if (!playerNameList.contains(player.getName())) {
			pd.set(player.getName()+".Name", player.getName());
            savepd();
            playerNameList.add(player.getName());
		}
	}
}

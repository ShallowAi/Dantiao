package com.valorin.energy;

import static com.valorin.Dantiao.getInstance;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.valorin.Dantiao;
import com.valorin.api.event.EnergyChangedEvent;
import com.valorin.configuration.PlayerSet;

public class Energy {
  private Map<String, Double> energy = new HashMap<String, Double>();
  private List<String> playerList;
  private double maxEnergy;
  private BukkitTask timer;
  private boolean enable;
  
  public boolean getEnable() {
	return enable;
  }
  
  public double getEnergy(String pn) {
	if (energy.containsKey(pn)) {
      BigDecimal bg = new BigDecimal(energy.get(pn));
	  double value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	  return value;
	}
	else {
	  new PlayerSet();
	  return 0.0;
	}
  }
  
  public void setEnergy(String pn,double amount) {
	Player p = null;
	for (Player serverPlayer : Bukkit.getOnlinePlayers()) {
	  if (serverPlayer.getName().equals(pn)) {
		p = serverPlayer;
	  }
	}
	if (p == null) {
	  return;
	}
	EnergyChangedEvent event = new EnergyChangedEvent(p, getEnergy(pn), amount);
	Bukkit.getServer().getPluginManager().callEvent(event);
	if (!event.isCancelled()) {
	  if (amount < 0) {
		energy.put(pn, 0.0);
	  } else {
	    energy.put(pn, amount);
	  }
	}
  }
  
  public double getMaxEnergy() {
	return maxEnergy;
  }
  
  public Map<String, Double> getMap() {
	return energy;
  }
  
  public void close() {
	if (!enable) {
	  return;
	}
	energy.clear();
	timer.cancel();
  }
  
  public double getNeed() {
    FileConfiguration config = getInstance().getConfig(); 
    double need = config.getDouble("Energy.Need");
    if (need == 0) {
      need = 150;
    }
    return need;
  }
  
  private void addEnergy(String pn) {
	double energy = this.energy.get(pn);
	FileConfiguration config = getInstance().getConfig();
	double maxEnergy,perSecondEnergy;
	if (config.getDouble("Energy.Max") == 0) {
	  maxEnergy = 300;
	} else {
	  maxEnergy = config.getDouble("Energy.Max");
	}
	if (config.getDouble("Energy.PerSecond") == 0) {
	  perSecondEnergy = 1;
	} else {
	  perSecondEnergy = config.getDouble("Energy.PerSecond");
	}
	if (maxEnergy - energy <= perSecondEnergy) {
	  this.energy.put(pn, maxEnergy);
	} else {
	  this.energy.put(pn, this.energy.get(pn) + perSecondEnergy);
	}
  }
  
  public void checkPlayer(String playerName) {
	if (!energy.keySet().contains(playerName)) {
		energy.put(playerName, maxEnergy);
	}
  }
  
  public Energy() {
	FileConfiguration config = getInstance().getConfig();
	if (config.getBoolean("Energy.Enable")) {
	  enable = true;
	  double maxEnergy;
	  if (config.getDouble("Energy.Max") == 0) {
		maxEnergy = 300;
	  } else {
	    maxEnergy = config.getDouble("Energy.Max");
	  }
	  this.maxEnergy = maxEnergy;
	
	  playerList = getInstance().getPlayerSet().get();
	  
	  energy.clear();
	  for (String playerName : playerList) {
	    energy.put(playerName, maxEnergy);
	  }
	  timer = new BukkitRunnable() {
	    @Override
	    public void run() {
	      for (String playerName : playerList) {
		    addEnergy(playerName);
		  }
	    }
	  }.runTaskTimerAsynchronously(Dantiao.getInstance(), 20, 20);
	} else {
	  enable = false;
	}
  }
}

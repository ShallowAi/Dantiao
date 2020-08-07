package com.valorin.arenas;

import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.areasFile;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

public class ArenasManager {

	public static List<Arena> arenas = new ArrayList<Arena>();
    public static List<String> busyArenasName = new ArrayList<String>();

    public static List<String> getArenasName() {//获取areas.yml中所有竞技场
      List<String> arenaList = new ArrayList<String>();
      if (!areasFile.exists()) {return arenaList;}
  	  ConfigurationSection section = areas.getConfigurationSection("Arenas");
  	  if (section == null) {return arenaList;}
  	  section.getKeys(false).forEach(key->{arenaList.add(key);});
  	  return arenaList;
    }
    public static Location getArenasPointA(String arena) {//获取某个竞技场的位置
  	    World world = Bukkit.getWorld(areas.getString("Arenas."+arena+".A.World"));
  	    double x = areas.getDouble("Arenas."+arena+".A.X"),
  	    y = areas.getDouble("Arenas."+arena+".A.Y"),
  	    z = areas.getDouble("Arenas."+arena+".A.Z");
  	    float yaw = (float)areas.getDouble("Arenas."+arena+".A.YAW"),pitch = (float)areas.getDouble("Arenas."+arena+".A.PITCH");
  	    return new Location(world,x,y,z,yaw,pitch);
    }
    
    public static Location getArenasPointB(String arena) {//获取某个竞技场的位置
  	    World world = Bukkit.getWorld(areas.getString("Arenas."+arena+".B.World"));
  	    double x = areas.getDouble("Arenas."+arena+".B.X"),
  	    y = areas.getDouble("Arenas."+arena+".B.Y"),
  	    z = areas.getDouble("Arenas."+arena+".B.Z");
  	    float yaw = (float)areas.getDouble("Arenas."+arena+".B.YAW"),pitch = (float)areas.getDouble("Arenas."+arena+".B.PITCH");
  	    return new Location(world,x,y,z,yaw,pitch);
    }
    
    public Arena getArena(String name) {
      if (name == null) {
    	return null;
      }
      if (arenas.size() == 0) {
    	return null;
      }
      for (Arena arena : arenas) {
    	if (arena.getName().equals(name)) {
    	  return arena;
    	}
      }
	  return null;
    }
    
    public void addArena(String name) {
      arenas.add(new Arena(name));
    }
    
    public void removeArena(String name) {
      arenas.remove(getArena(name));
    }
    
    public String getPlayerOfArena(String pn) {
      if (arenas.size() == 0) {
    	return null;
      }
      for (Arena arena : arenas) {
    	if (arena.getEnable()) {
    	  if (arena.getp1().equals(pn) || arena.getp2().equals(pn)) {
    	    return arena.getName();
    	  }
    	}
      }
      return null;
    }
    
    public String getWatcherOfArena(String pn) {
      if (arenas.size() == 0) {
    	return null;
      }
      for (Arena arena : arenas) {
    	if (arena.getEnable()) {
    	  if (arena.getWatchers().contains(pn)) {
    	    return arena.getName();
    	  }
    	}
      }
      return null;
    }
    
    public String getTheOtherPlayer(String pn) {
      String arenaName = getPlayerOfArena(pn);
      if (arenaName != null) {
    	Arena arena = getArena(arenaName);
    	if (arena.getp1().equals(pn)) {
    	  return arena.getp2();
    	} else {
    	  return arena.getp1();
    	}
      }
      return null;
    }
    
    public boolean isContainPlayer(String arenaName,String pn) {//某个指定竞技场是否包括某玩家
      if (getArena(arenaName) == null) {
        return false;
      }
      if (getArena(arenaName).getEnable()) {
	    if (getArena(arenaName).getp1().equals(pn) || getArena(arenaName).getp2().equals(pn)) {
		  return true;
	    }
      }
      return false;
	}
    
    public boolean isPlayerBusy(String pn) {//某玩家是否在比赛
      if (arenas.size() == 0) {
    	return false;
      }
      for (Arena arena : arenas) {
    	if (isContainPlayer(arena.getName(), pn)) {
          return true;
    	}
      }
      return false;
    }
    
    public ArenasManager()  {
      if (getArenasName().size() == 0) {
    	return;
      }
      for (String arena : getArenasName()) {
    	arenas.add(new Arena(arena));
      }
    }
}

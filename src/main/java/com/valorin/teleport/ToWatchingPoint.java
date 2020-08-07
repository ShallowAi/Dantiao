package com.valorin.teleport;

import static com.valorin.configuration.DataFile.areas;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ToWatchingPoint {
  public static void to(Player p,String arenaName) {
	if (areas.getString("Arenas."+arenaName+".WatchingPoint.World") == null) {
	  return;
	}
	if (p == null) {
	  return;
	}
	String world = areas.getString("Arenas."+arenaName+".WatchingPoint.World");
    int x = areas.getInt("Arenas."+arenaName+".WatchingPoint.X");
	int y = areas.getInt("Arenas."+arenaName+".WatchingPoint.Y");
	int z = areas.getInt("Arenas."+arenaName+".WatchingPoint.Z");
	int yaw = areas.getInt("Arenas."+arenaName+".WatchingPoint.YAW");
	int pitch = areas.getInt("Arenas."+arenaName+".WatchingPoint.PITCH");
	p.teleport(new Location(Bukkit.getWorld(world),x,y,z,yaw,pitch));
  }
}

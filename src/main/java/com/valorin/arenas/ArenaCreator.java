package com.valorin.arenas;

import org.bukkit.Location;

public class ArenaCreator {
  Location pointA,pointB;
  String creator;
  
  public Location getPointA() {
	return pointA;
  }
  
  public Location getPointB() {
	return pointB;
  }
  
  public String getCreator() {
	return creator;
  }
  
  public void setPointA(Location pointA) {
	this.pointA = pointA;
  }
  
  public void setPointB(Location pointB) {
	this.pointB = pointB;
  }
  
  public ArenaCreator(String creator) {
    this.creator = creator;
  }
}

package com.valorin.specialtext;

import org.bukkit.entity.Player;

public class Dec {
  public static String[] str = {"",
		                  "        ",
		                  "&8&m------------------------------------------------------------",
		                  "                    ",
		                  "              ",
		                  "&e&m--------------------------------",
		                  "                    ",
		                  "           "};
  
  public static String getStr(int n) {
	if (n > str.length) {
	  return null;
	}
	return str[n].replace("&", "§");
  }
  
  public static void sm(Player p,int n) {
	p.sendMessage(getStr(n));
  }
}

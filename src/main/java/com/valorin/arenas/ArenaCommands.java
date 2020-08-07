package com.valorin.arenas;

import java.util.List;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;

import static com.valorin.configuration.DataFile.areas;

public class ArenaCommands {
    public static void ExecuteArenaCommands(String arenaName,Player p1,Player p2) {
      new BukkitRunnable() {
	    @Override
	    public void run() {
    	  if (areas.getStringList("Arenas."+arenaName+".Commands").size() != 0) {
	        List<String> commands = areas.getStringList("Arenas."+arenaName+".Commands");
	        for (String command : commands) {
	    	  String way = command.split("\\|")[0];
	    	  String c = command.split("\\|")[1];
	    	  if (way.equalsIgnoreCase("op")) {
	    		try {
	    	      if (p1.isOp()) {
	    		    Bukkit.dispatchCommand(p1, c.replace("{player}", p1.getName()));
	    	      } else {
	    		    p1.setOp(true);
	    		    Bukkit.dispatchCommand(p1, c.replace("{player}", p1.getName()));
	    		    p1.setOp(false);
	    	      }
	    		} catch (Exception e) {
	    		  e.printStackTrace();
	    		  sm("&c[x]执行&eOP&c身份的竞技场指令时发生了错误，可能是管理员添加的指令不妥，请截此图联系管理员",p1,p2);
	    		}
	    	    if (p2.isOp()) {
	    		  Bukkit.dispatchCommand(p2, c.replace("{player}", p2.getName()));
	    	    } else {
	    		  p2.setOp(true);
	    		  Bukkit.dispatchCommand(p2, c.replace("{player}", p2.getName()));
	    		  p2.setOp(false);
	    	    }
	    	  }
	    	  if (way.equalsIgnoreCase("player")) {
	    		try {
	    	      Bukkit.dispatchCommand(p1, c.replace("{player}", p1.getName()));
	    	      Bukkit.dispatchCommand(p2, c.replace("{player}", p2.getName()));
	    		} catch (Exception e) {
	    		  e.printStackTrace();
	    		  sm("&c[x]执行&e玩家&c身份的竞技场指令时发生了错误，可能是管理员添加的指令不妥，请截此图联系管理员",p1,p2);
	    		}
	    	  }
	    	  if (way.equalsIgnoreCase("console")) {
	    		try {
	    	      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), c.replace("{player1}", p1.getName()).replace("{player2}", p2.getName()));
	    		} catch (Exception e) {
		    	  e.printStackTrace();
		    	  sm("&c[x]执行&e后台&c身份的竞技场指令时发生了错误，可能是管理员添加的指令不妥，请截此图联系管理员",p1,p2);
		    	}
	    	  }
	        }
	      }
        }
	  }.runTask(Dantiao.getInstance());
    }
}

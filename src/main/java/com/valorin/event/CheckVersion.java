package com.valorin.event;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;
import com.valorin.network.Update;

public class CheckVersion implements Listener{
	@EventHandler
	public void check(PlayerJoinEvent e) {
	  Player p = e.getPlayer();
	  if (!p.isOp()) {
		  return;
	  }
	  if (!getConfig().getBoolean("CheckVersion")) {
		  return;
	  }
	  Update update = Dantiao.getInstance().getUpdate();
	  new BukkitRunnable() {
		  public void run() {
		      if (p != null) {
		    	  sendUpdateInfo(update,p);
		      }
		  }
	  }.runTaskLaterAsynchronously(Dantiao.getInstance(), 60);
	}
	
	public static void sendUpdateInfo(Update update,CommandSender sender) {
		if (update.getState() == 1) {
	    	  String version = update.getVersion();
	    	  String versionNow = Dantiao.getVersion();
	    	  List<String> messageList = update.getContext();
		      if (!update.isNew()) {
		    	  sender.sendMessage("");
		    	  sender.sendMessage("§8§l[§bDantiao§8§l]");
		    	  sender.sendMessage("§f- §a决斗插件发现最新版本：§d"+version+"§a可替换现在的旧版本§c"+versionNow);
			      if (messageList.size() != 0) {
			          sender.sendMessage("§7更新内容:");
			          for (String message : messageList) {
			        	  sender.sendMessage("§f- "+message);
			          }
			      }
		      } else {
		    	  sender.sendMessage("");
		    	  sender.sendMessage("§8§l[§bDantiao§8§l] §7决斗插件已更新到最新版本§a"+versionNow);
		    	  sender.sendMessage("");
		      }
	      }
	      if (update.getState() == 2) {
			  sm("&7版本更新内容因为超时而获取失败，建议输入/dt checkv手动获取",null);
		  }
	      if (update.getState() == 3) {
	    	  sm("&7版本更新内容因为某些未知原因（详见后台报错信息）而获取失败，建议输入/dt checkv手动获取",null);
		  }
	}
	
	public static FileConfiguration getConfig() {
	  return Dantiao.getInstance().getConfig();
	}
}

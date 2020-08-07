package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.arenas.Starter;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.InServerCommands;
import com.valorin.energy.Energy;
import com.valorin.request.RequestsHandler;

public class RequestAccept extends SubCommand implements InServerCommands{

	public RequestAccept() {
		super("accept");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player a = (Player)sender;
	  String an = a.getName();
	  RequestsHandler rh = getInstance().getRequestsHandler();
	  if (rh.getSenders(an).size() == 0) {
		sm("&c[x]你没有任何未处理的请求！",a);
		return true;
	  }
	  Energy e = Dantiao.getInstance().getEnergy();
	  if (e.getEnable()) {
	    if (e.getEnergy(an) < e.getNeed()) {
		  sm("&c[x]你的精力值不足！请休息一会",a);
		  return true;
	    }
	  }
	  if (args.length == 1) {
		if (rh.getSenders(an).size() == 1) {
		  String sn = rh.getSenders(an).get(0);
		  Player s = Bukkit.getPlayerExact(sn);
		  rh.removeRequest(sn, an);
		  sm("&a[v]已接受请求",a);
		  sm("&a[v]对方接受了你的请求！",s);
		  if (e.getEnable())
		  {
		    e.setEnergy(sn, e.getEnergy(sn) - e.getNeed());
		    e.setEnergy(an, e.getEnergy(an) - e.getNeed());
		  }
		  new Starter(a, s, null, null);
		} else {
		  sm("&6发现有&e多个&6待处理的请求，请选择处理 [right]",a);
		  for (String sn : rh.getSenders(an))
		  {
			BigDecimal bg = new BigDecimal((rh.getTime(sn, an) - System.currentTimeMillis())/1000);
			double time = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			a.sendMessage("§b"+sn+" §d"+(0-time)+gm("秒前",a));
		  }
		  sm("&6输入 &f/dt accept <玩家名> &6选择处理",a);
		}
		return true;
	  } else {
		String sn = args[1];
		Player s = Bukkit.getPlayerExact(sn);
		if (!rh.getSenders(an).contains(sn)) {
		  sm("&c[x]不存在的请求",a);
		  return true;
		} else {
		  rh.removeRequest(sn, an);
		  sm("&a[v]已接受请求",a);
		  sm("&a[v]对方接受了你的请求！",s);
		  if (e.getEnable())
		  {
			e.setEnergy(sn, e.getEnergy(sn) - e.getNeed());
			e.setEnergy(an, e.getEnergy(an) - e.getNeed());
		  }
		  new Starter(a, s, null, null);
		}
	  }
	  return true;
	}

}

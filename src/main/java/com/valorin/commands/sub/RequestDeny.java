package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.math.BigDecimal;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;
import com.valorin.request.RequestsHandler;
import com.valorin.commands.way.InServerCommands;

public class RequestDeny extends SubCommand implements InServerCommands{
 
	public RequestDeny() {
		super("deny");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player d = (Player)sender;
	  String dn = d.getName();
	  RequestsHandler rh = getInstance().getRequestsHandler();
	  if (rh.getSenders(dn).size() == 0) {
		sm("&c[x]你没有任何未处理的请求！",d);
		return true;
	  }
	  if (args.length == 1) {
		if (rh.getSenders(dn).size() == 1) {
		  String sn = rh.getSenders(dn).get(0);
		  Player s = Bukkit.getPlayerExact(sn);
		  rh.removeRequest(sn, dn);
		  sm("&a[v]已拒绝请求",d);
		  sm("&c[x]对方拒绝了你的请求",s);
		} else {
		  sm("&6发现有&e多个&6待处理的请求，请选择处理 [right]",d);
		  for (String sn : rh.getSenders(dn))
		  {
			BigDecimal bg = new BigDecimal((rh.getTime(sn, dn) - System.currentTimeMillis())/1000);
			double time = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
			d.sendMessage("§b"+sn+" §7("+time+gm("秒前",d)+")");
		  }
		  sm("&6输入 &f/dt deny <玩家名> &6选择处理",d);
		}
		return true;
	  } else {
		String sn = args[1];
		Player s = Bukkit.getPlayerExact(sn);
		if (!rh.getSenders(dn).contains(sn)) {
		  sm("&c[x]不存在的请求",d);
		  return true;
		} else {
		  rh.removeRequest(sn, dn);
		  sm("&a[v]已拒绝请求",d);
		  sm("&c[x]对方拒绝了你的请求",s);
		}
	  }
	  return true;
	}

}

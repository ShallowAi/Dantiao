package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.blacklist;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.commands.SubCommand;
import com.valorin.request.RequestsHandler;
import com.valorin.commands.way.InServerCommands;
import com.valorin.energy.Energy;
import com.valorin.specialtext.Click;
import com.valorin.specialtext.Dec;
import com.valorin.timetable.TimeChecker;

public class RequestSend extends SubCommand implements InServerCommands{

	public RequestSend() {
		super("send");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = (Player)sender;
	  String sn = p.getName();
	  if (args.length != 2) {
		sm("&7正确用法：/dt send <玩家名>",p);
		return true;
	  }
	  if (getInstance().getArenasHandler().isPlayerBusy(p.getName())) {//OP比赛时输入
		return true;
	  }
	  String rn = args[1];
	  /*
	   * 判断步骤：
	   * 1、对方是否在线
	   * 2、对方是否是自己，我 打 我 自 己
	   * 3、双方是否存在黑名单
	   * 4、是否处于规定时间段
	   * 5、双方是否处于规定世界 【可关】
	   * 6、精力值是否够 【可关】
	   * 7、是否重复发送
	   * 8、是否对方已发送给你
	   */
	  boolean isOnline = false;
	  for (Player player : Bukkit.getOnlinePlayers()) {
	    if (player.getName().equals(rn)) {
		  isOnline = true;
	    }
	  }
	  if (!isOnline) {
	    sm("&c[x]目标玩家不在线！",p);
	    return true;
	  }
	  if (rn.equals(sn)) {
		sm("&c[x]不能向自己发送请求！",p);
		return true;
	  }
	  List<String> blist = blacklist.getStringList("BlackList");
	  if (blist.contains(sn)) {
		sm("&c[x]您已被禁赛！",p);
		return true;
	  }
	  if (blist.contains(rn)) {
		sm("&c[x]对方已被禁赛！",p);
		return true;
	  }
	  if (!TimeChecker.isInTheTime(p, false)) {
		sm("&c[x]此时间段不开放邀请赛功能，输入/dt timetable查看",p);
		return true;
	  }
	  if (getInstance().getConfig().getBoolean("WorldLimit.Enable")) {
	    List<String> worldlist = getInstance().getConfig().getStringList("WorldLimit.Worlds");
	    if (worldlist != null) {
	      if (!worldlist.contains(p.getWorld().getName())) {
	        sm("&c[x]你所在的世界已被禁止比赛",p);
	        return true;
	      }
	      if (!worldlist.contains(Bukkit.getPlayerExact(rn).getWorld().getName())) {
	        sm("&c[x]对方所处世界已被禁止比赛",p);
	        return true;
	      }
	    }
	    return true;
	  }
	  Energy e = Dantiao.getInstance().getEnergy();
	  if (e.getEnable()) {
	    if (e.getEnergy(p.getName()) < e.getNeed()) {
		  sm("&c[x]你的精力值不足！请休息一会",p);
		  return true;
	    }
	    if (e.getEnergy(p.getName()) < e.getNeed()) {
		  sm("&c[x]对方的精力值不足！请稍后再申请",p);
		  return true;
	    }
	  }
	  if (getInstance().getArenasHandler().isPlayerBusy(rn)) {
		sm("&c[x]对方正在比赛！请等一下再向他发送请求",p);
		return true;
	  }
	  RequestsHandler rh = getInstance().getRequestsHandler();
	  if (rh.getReceivers(sn).contains(rn)) {
		sm("&c[x]你已经向对方发过申请了，请不要重复发送！",p);
	    return true;
	  }
	  if (rh.getRequest(rn, sn) != null) {
		sm("&c[x]对方已经向你发送了申请，无需重复向TA发送申请，请先处理",p);
	    return true;
	  }
	  
	  Player r = Bukkit.getPlayerExact(rn);
	  rh.addRequest(sn, rn);
	  Dec.sm(r, 2);
	  r.sendMessage(Dec.getStr(4)+
			  gm("&e收到一则单挑请求&7(来自&b{player}&7)",Bukkit.getPlayerExact(rn),"player",new String[]{sn}));
	  Dec.sm(r, 0);
	  Click.sendRequest(sn,rn);
	  Dec.sm(r, 0);
	  Dec.sm(r, 2);
	  
	  sm("&a[v]请求发送完毕！等待对方处理，有效时间60秒",p);
	  return true;
	}

}

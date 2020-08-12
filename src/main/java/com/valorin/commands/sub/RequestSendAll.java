package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.blacklist;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.List;

import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.InServerCommands;
import com.valorin.energy.Energy;
import com.valorin.itemstack.PlayerItems;
import com.valorin.request.RequestsHandler;
import com.valorin.specialtext.Click;
import com.valorin.specialtext.Dec;
import com.valorin.timetable.TimeChecker;
import com.valorin.util.ItemTaker;
import com.valorin.util.ItemGiver;

public class RequestSendAll extends SubCommand implements InServerCommands{

	public RequestSendAll() {
		super("sendall");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = (Player)sender;
	  String sn = p.getName();
	  if (args.length == 1) {
	    if (getInstance().getArenasHandler().isPlayerBusy(p.getName())) {//OP决斗时输入
		  return true;
	    }
	    List<String> blist = blacklist.getStringList("BlackList");
	    if (blist.contains(sn)) {
		  sm("&c[x]您已被禁赛！",p);
		 return true;
	    }
	    if (!TimeChecker.isInTheTime(p, false)) {
		  sm("&c[x]此时间段不开放邀请赛功能，输入/dt timetable查看",p);
		  return true;
	    }
	    Energy e = Dantiao.getInstance().getEnergy();
	    if (e.getEnable()) {
	      if (e.getEnergy(p.getName()) < e.getNeed()) {
		    sm("&c[x]你的精力值不足！请休息一会",p);
		    return true;
	      }
	    }
	    ItemTaker ic = new ItemTaker(p,"§4§1§1§4§2§0§9§1§1§5§2",1);
	    if (ic.getSlot() == -1) {
	    	sm("&c[x]本操作需要消耗一个全服邀请函",p);
	    } else {
	    	ic.consume(p);
	    	sendAll(p);
	    }
	    return true;
	  } else {
		if (!sender.hasPermission("dt.admin")) {
	        sm("&c[x]无权限！",p);
	        return true;
	    }
		if (args[1].equalsIgnoreCase("getitem")) {
			new ItemGiver(p, PlayerItems.getInvitation(p));
			return true;
		}
		return true;
	  }
	}
    
	private void sendAll(Player sender) {
		String sn = sender.getName();
		TextComponent txt = Click.invitationToAll(sn);
		int count = 0;
	    for (Player receiver : Bukkit.getOnlinePlayers()) {
	    	if (receiver.getUniqueId().equals(sender.getUniqueId())) { //跳过自己
	    	   continue;
	    	}
	    	String rn = receiver.getName();
	    	List<String> blist = blacklist.getStringList("BlackList");
	  	    if (blist.contains(rn)) { //如果对方处于黑名单中，跳过
	  		    continue;
	  	    }
	  	    if (getInstance().getConfig().getBoolean("WorldLimit.Enable")) {
	  	        List<String> worldlist = getInstance().getConfig().getStringList("WorldLimit.Worlds");
	  	        if (worldlist != null) {
	  	            if (!worldlist.contains(receiver.getWorld().getName())) {//如果对方不处于白名单世界中，跳过
	  	                continue;
	  	            }
	  	        }
	  	    }
	  	    if (getInstance().getArenasHandler().isPlayerBusy(rn)) {//对方正在决斗，不可以发送
	  	      continue;
	  	    }
	  	    RequestsHandler rh = getInstance().getRequestsHandler();
		    if (rh.getReceivers(sn).contains(rn)) { //已发送过申请了
		      continue;
		    }
	  	    Dec.sm(receiver, 2);
	  	    receiver.sendMessage(Dec.getStr(7)+
				  gm("&e玩家&7{player}&e向全服玩家下了单挑战书",null,"player",new String[]{sn}));
	  	    Dec.sm(receiver, 0);
	  	    receiver.spigot().sendMessage(txt);
	  	    Dec.sm(receiver, 0);
	  	    Dec.sm(receiver, 2);
	  	    rh.addRequest(sn, rn); //发送申请
	  	    count++;
	    }
	    sm("&a[v]已有{amount}个玩家收到了你的单挑请求，请等待接受",sender,"amount",new String[]{""+count});
	}
}

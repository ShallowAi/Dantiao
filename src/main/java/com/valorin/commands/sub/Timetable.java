package com.valorin.commands.sub;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.commands.SubCommand;

public class Timetable extends SubCommand{

	public Timetable() {
		super("timetable");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  List<String> ts = Dantiao.getInstance().getTimeTable().getSearching();
	  List<String> ti = Dantiao.getInstance().getTimeTable().getInvite();
      if (ts.size() != 0) {
    	sm("&b全服匹配开放时间：",p);
        for (String s : ts) {
      	  sender.sendMessage(s);
        }
      } else {
    	sm("&b全服匹配开放时间：全天无限制",p);
      }
      
      if (ti.size() != 0) {
    	sm("&b邀请功能开放时间：",p);
        for (String s : ti) {
      	  sender.sendMessage(s);
        }
      } else {
    	sm("&b邀请功能开放时间：全天无限制",p);
      }
	  return true;
	}
}

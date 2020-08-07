package com.valorin.commands.sub;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.arenas.Starter;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;

public class Game extends SubCommand implements AdminCommands{

	public Game() {
		super("game");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
      if (sender instanceof Player)
      { p = (Player)sender; }
      if (args.length != 4) {
		sm("&7正确格式：/dt game <竞技场名称> <玩家1> <玩家2>",p);
		return true;
	  }
      String pn1 = args[2];
      String pn2 = args[3];
      if (Bukkit.getPlayerExact(pn1) == null || Bukkit.getPlayerExact(pn2)  == null) {
        sm("&c[x]玩家名输入有误！请检查两个玩家是否都在线！",p);
        return true;
      }
      if (pn1.equals(pn2)) {
    	sm("&c[x]请输入两个不同的玩家名",p);
    	return true;
      }
      new Starter(Bukkit.getPlayerExact(pn1),Bukkit.getPlayerExact(pn2),args[1],p);
      sm("&a[v]已强制开始",p);
	  return true;
	}
}

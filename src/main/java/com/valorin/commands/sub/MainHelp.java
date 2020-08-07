package com.valorin.commands.sub;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;

public class MainHelp {
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
      if (sender instanceof Player)
      { p = (Player)sender; }
      sm("", p);
      sender.sendMessage("§b§lDan§3§l§otiao §c§l单挑插件V2正式版§cBy-Valorin §d§l"+Dantiao.getInstance().getDescription().getVersion());
	  sender.sendMessage("§e=============================================");
	  sm("", p);
	  sm("  &f&l>> &6/dt help(h) &f查看玩家帮助&a[v]",p,false);
	  sm("  &f&l>> &6/dt adminhelp(ah) &f查看管理员帮助&a[v]",p,false);
	  sm("", p);
	  sender.sendMessage("§e=============================================");
	  sender.sendMessage("§a§lWeb:§9§nhttps://www.mcbbs.net/thread-818429-1-1.html");
	  sm("", p);
	  return true;
	}
}

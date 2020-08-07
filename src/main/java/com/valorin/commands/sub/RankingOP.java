package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.saveAreas;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;
import com.valorin.ranking.HD;
import com.valorin.ranking.Ranking;

public class RankingOP extends SubCommand implements AdminCommands{

	public RankingOP() {
		super("hd");
	}

	public void sendHelp(Player p) {
		sm("",p);
		sm("&3&lDan&b&l&oTiao &f&l>> &a管理员帮助：排行榜全息图操作",p,false);
		sm("&b/dt hd win &f- &a创建/移动：全息图-胜场排行榜",p,false);
		sm("&b/dt hd winremove &f- &a删除：全息图-胜场排行榜",p,false);
		sm("&b/dt hd kd &f- &a创建/移动：全息图-KD值排行榜",p,false);
		sm("&b/dt hd kdremove &f- &a删除：全息图-KD值排行榜",p,false);
		sm("&b/dt hd refresh &f- &a强制刷新：所有全息图",p,false);
		sm("",p);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  if (args.length == 1) {
		sendHelp(p);
		return true;
	  }
	  HD hd = getInstance().getHD();
	  if (!hd.isEnabled()) {
		sm("&c[x]未发现HD全息插件！无法使用此功能！",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("win"))
	  {
		if (p == null) {
		  sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
		  return true;
		}
		if (areas.getString("Dantiao-HD-Win.World") != null) {
          sm("&b移动全息图...",p);
        } else {
          sm("&b创建全息图...",p);
        }
		Location loc = p.getLocation();
		areas.set("Dantiao-HD-Win.World", p.getWorld().getName());
		areas.set("Dantiao-HD-Win.X", loc.getX());
		areas.set("Dantiao-HD-Win.Y", loc.getY());
		areas.set("Dantiao-HD-Win.Z", loc.getZ());
		saveAreas();
		hd.load(1);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("winremove"))
	  {
		if (areas.getString("Dantiao-HD-Win.World") == null) {
		  sm("&c[x]该全息图本来就不存在",p);
		  return true;
		}
		areas.set("Dantiao-HD-Win", null);
		hd.unload(1);
		saveAreas();
		sm("&c[v]全息图删除完毕",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("kd"))
	  {
		if (p == null) {
		  sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
		  return true;
		}
		if (areas.getString("Dantiao-HD-KD.World") != null) {
          sm("&b移动全息图...",p);
        } else {
          sm("&b创建全息图...",p);
        }
		Location loc = p.getLocation();
		areas.set("Dantiao-HD-KD.World", p.getWorld().getName());
		areas.set("Dantiao-HD-KD.X", loc.getX());
		areas.set("Dantiao-HD-KD.Y", loc.getY());
		areas.set("Dantiao-HD-KD.Z", loc.getZ());
		saveAreas();
		hd.load(2);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("kdremove"))
	  {
		if (areas.getString("Dantiao-HD-KD.World") == null) {
		  sm("&c[x]该全息图本来就不存在",p);
		  return true;
		}
		areas.set("Dantiao-HD-KD", null);
		hd.unload(2);
		saveAreas();
		return true;
	  }
	  if (args[1].equalsIgnoreCase("refresh"))
	  {
		if (areas.getString("Dantiao-HD-Win.World") == null && areas.getString("Dantiao-HD-KD.World") == null) {
		  sm("&c[x]无任何全息图！",p);
		  return true;
		}
		Ranking ranking = getInstance().getRanking();
		ranking.reloadRanking();
		if (areas.getString("Dantiao-HD-Win.World") != null) {
		  hd.unload(1);
		  hd.load(1);
		}
		if (areas.getString("Dantiao-HD-KD.World") != null) {
		  hd.unload(2);
		  hd.load(2);
		}
		if (p != null) {
		  sm("&a[v]全息图刷新完毕！",p);
		}
		return true;
	  }
	  sendHelp(p);
	  return true;
	}

}

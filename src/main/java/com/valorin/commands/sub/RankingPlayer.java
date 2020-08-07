package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.ranking;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.math.BigDecimal;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;
import com.valorin.ranking.Ranking;

public class RankingPlayer extends SubCommand{

	public RankingPlayer() {
		super("rank");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  Ranking r = getInstance().getRanking();
	  if (args.length == 1) {
		if (p == null) {
		  sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
		  return true;
	    }
		sm("&6单挑排行榜信息 [right]",p,false);
		sm("&b胜场数排名：第&d{ranking}&b名",p,"ranking",new String[]{""+r.getWin(p)},false);
		sm("&bKD比值排名：第&d{ranking}&b名",p,"ranking",new String[]{""+r.getKD(p)},false);
		sm("&e查看其他人的？输入 &f/dt rank win或kd &e查看全服排名",p,false);
		return true;
	  } else {
		if (args[1].equalsIgnoreCase("win")) {
		  sm("&b[star1]单挑-胜场排行榜[star2]",p);
	      if (ranking.getStringList("Win").size() != 0) {
	    	List<String> winlist = ranking.getStringList("Win");
	    	int max = 0;
	    	if (winlist.size() > 10) {
	    	  max = 10;
	    	} else {
	    	  max = winlist.size();
	    	}
	    	for (int i = 0;i < max;i++) {
	    	  String color = "§b";
	    	  if (i == 0) { color = "§e§l"; } if (i == 1) { color = "§6§l"; } if (i == 2) { color = "§b§l"; }
	    	  p.sendMessage(color+"No."+(i+1)+" §f"+winlist.get(i).split("\\|")[0]+" §a("+winlist.get(i).split("\\|")[1]+")");
	    	}
	    	int rank = r.getWin(p);
	      	if (rank != 0) {
	      	  for (int i = 0;i < winlist.size();i++) {
	      		if (winlist.get(i).split("\\|")[0].equals(p.getName())) {
	      		  sm("&b我的排名：&e{ranking} (胜利{amount}场)",p,"ranking amount",new String[]{""+rank,winlist.get(i).split("\\|")[1]});
	      		}
	      	  }
	      	} else {
	      	  sm("&b我的排名：&e暂无",p);
	        }
	      } else {
	    	sm("&c该排行榜没有数据",p);
	      }
		  return true;
		}
		if (args[1].equalsIgnoreCase("kd")) {
		  sm("&b[star1]单挑-KD比值排行榜[star2]",p);
	      if (ranking.getStringList("KD").size() != 0) {
	    	List<String> kdlist = ranking.getStringList("KD");
	    	int max = 0;
	    	if (kdlist.size() > 10) {
	    	  max = 10;
	    	} else {
	    	  max = kdlist.size();
	    	}
	    	for (int i = 0;i < max;i++) {
	    	  String color = "§b";
	    	  if (i == 0) { color = "§e§l"; } if (i == 1) { color = "§6§l"; } if (i == 2) { color = "§b§l"; }
	    	  BigDecimal bg = new BigDecimal(kdlist.get(i).split("\\|")[1]);
        	  double kd = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	    	  p.sendMessage(color+"No."+(i+1)+" §f"+kdlist.get(i).split("\\|")[0]+" §a("+kd+")");
	    	}
	    	int rank = r.getWin(p);
	      	if (rank != 0) {
	      	  for (int i = 0;i < kdlist.size();i++) {
	      		if (kdlist.get(i).split("\\|")[0].equals(p.getName())) {
	      		  BigDecimal bg = new BigDecimal(kdlist.get(i).split("\\|")[1]);
            	  double kd = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	      		  sm("&b我的排名：&e{ranking} (比值{kd})",p,"ranking kd",new String[]{""+rank,""+kd});
	      		}
	      	  }
	      	} else {
	      	  sm("&b我的排名：&e暂无",p);
	        }
	      } else {
	    	sm("&c该排行榜没有数据",p);
	      }
		  return true;
		}
	  }
	  return true;
	}

}

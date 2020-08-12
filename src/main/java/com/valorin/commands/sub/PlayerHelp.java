package com.valorin.commands.sub;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;

public class PlayerHelp extends SubCommand{

	public PlayerHelp() {
		super("help", "h");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
      if (sender instanceof Player)
      { p = (Player)sender; }
      sm("",p);
      sm("&3&lDan&b&l&oTiao &f&l>> &a玩家帮助",p,false);
      sm("&b/dt rank(r) &f- &a查看决斗排行榜",p,false);
      sm("&b/dt lobby(l) &f- &a传送至大厅",p,false);
      sm("&b/dt start(st) &f- &a打开匹配面板搜寻对手",p,false);
      sm("&b/dt timetable &f- &a查看匹配功能开放时间段",p,false);
      sm("&b/dt arenasinfo(ainfo) &f- &a查看所有竞技场信息",p,false);
      sm("&b/dt watch <竞技场名称> &f- &a观战某个正在决斗的竞技场",p,false);
      sm("&b/dt send <玩家名> &f- &a向某个玩家发送决斗请求",p,false);
      sm("&b/dt accept &f- &a接受某个玩家的决斗请求",p,false);
      sm("&b/dt deny &f- &a拒绝某个玩家的决斗请求",p,false);
      sm("&b/dt sendall &f- &a向全服发起决斗请求(需要特定邀请函)",p,false);
      sm("&b/dt quit(q) &f- &a认输(决斗时使用)",p,false);
      sm("&b/dt shop(s) &f- &a打开决斗积分商城",p,false);
      sm("&b/dt records(r) &f- &a打开决斗记录面板",p,false);
      sm("&b/dt points(p) me &f- &a查看我的决斗积分",p,false);
      sm("&b/dt changelang &f- &a查看所有语言文件",p,false);
      sm("&b/dt changelang <语言文件> &f- &e切换语言",p,false);
      sm("",p);
	  return true;
	}

}

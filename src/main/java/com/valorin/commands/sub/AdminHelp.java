package com.valorin.commands.sub;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;

public class AdminHelp extends SubCommand implements AdminCommands{

	public AdminHelp() {
		super("adminhelp", "ah");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
      if (sender instanceof Player)
      { p = (Player)sender; }
      sm("",p);
      sm("&3&lDan&b&l&oTiao &f&l>> &a管理员帮助",p,false);
      sm("&b&l/dt arenas(a) &f- &a查看竞技场操作的相关帮助",p,false);
      sm("&b&l/dt shop(s) help &f- &a查看决斗积分&n商城&a操作的相关帮助",p,false);
      sm("&b&l/dt points(p) &f- &a查看决斗积分操作的相关帮助",p,false);
      sm("&b&l/dt blacklist(b) &f- &a查看黑名单操作的相关帮助",p,false);
      sm("&b&l/dt energy(e) &f- &a查看精力值操作的相关帮助",p,false);
      sm("&b&l/dt hd &f- &a查看决斗排行榜操作的相关帮助",p,false);
      sm("&b&l/dt exp &f- &a查看段位经验操作的相关帮助",p,false);
      sm("&b/dt stop <竞技场名称> &f- &a强制结束某个竞技场的决斗",p,false);
      sm("&b/dt lobby set &f- &a设置服务器的决斗大厅传送点",p,false);
      sm("&b/dt lobby delete &f- &a取消大厅传送点",p,false);
      sm("&b/dt game <竞技场名称> <玩家1> <玩家2> &f- &a强行开启一场决斗",p,false);
      sm("&b/dt sendall getitem &f- &a获取一个决斗全服邀请函道具",p,false);
      sm("&b/dt checkv &f- &a手动获取决斗插件最新版本信息（即检查更新）",p,false);
      sm("",p);
	  return true;
	}

}

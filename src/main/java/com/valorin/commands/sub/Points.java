package com.valorin.commands.sub;

import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.savepd;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;

public class Points extends SubCommand{

	public Points() {
		super("points","p");
	}

	public void sendHelp(Player p) {
		sm("",p);
		sm("&3&lDan&b&l&oTiao &f&l>> &a管理员帮助：单挑积分操作",p,false);
		sm("&b/dt points(p) add <玩家名> <数额> &f- &a为某玩家添加积分",p,false);
		sm("&b/dt points(p) take <玩家名> <数额> &f- &a扣除某玩家的积分",p,false);
		sm("&b/dt points(p) set <玩家名> <数额> &f- &a设定某玩家的积分",p,false);
		sm("&b/dt points(p) view <玩家名> &f- &a查看某玩家的积分余额",p,false);
		sm("",p);
	}
	
	public boolean isNum(String str)
	{
	  if (str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$")) {
		if (Double.valueOf(str) < 0) {
		  return false;
		}
		return true;
	  }
	  return false;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
	  Player p = null;
	  if (sender instanceof Player) { p = (Player)sender; }
	  if (args.length == 1) {
		sendHelp(p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("me")) {
		if (!(sender instanceof Player)) {
          sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
          return true;
        }
		double me = pd.getDouble(p.getName()+".Points");
		sm("&6我的单挑积分余额 [right] {amount}",p,"amount",new String[]{""+me});
		return true;
	  }
	  if (!sender.hasPermission("dt.admin")) {
    	sm("&c[x]无权限！",p);
    	return true;
      }
	  if (args[1].equalsIgnoreCase("add")) {
		if (args.length != 4) {
		  sm("&7正确格式：/dt p add <玩家名> <数额>",p);
		  return true;
		}
		if (!isNum(args[3])) {
		  sm("&c[x]请输入有效的且大于零的数字",p);
		  return true;
		}
		pd.set(args[2]+".Points", pd.getInt(args[2]+".Points") + Double.valueOf(args[3]));
		savepd();
		sm("&a[v]积分增添成功",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("take")) {
		if (args.length != 4) {
		  sm("&7正确格式：/dt p take <玩家名> <数额>",p);
		  return true;
		}
		if (!isNum(args[3])) {
		  sm("&c[x]请输入有效的且大于零的数字",p);
		  return true;
		}
		double value = Double.parseDouble(args[3]);
		double now = pd.getDouble(args[2]+".Points");
		if (now < value) {
		  pd.set(args[2]+".Points", 0);
		} else {
		  pd.set(args[2]+".Points", now - value);
		}
		savepd();
		sm("&a[v]积分扣除成功",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("set")) {
		if (args.length != 4) {
		  sm("&7正确格式：/dt p set <玩家名> <数额>",p);
		  return true;
		}
		if (!isNum(args[3])) {
		  sm("&c[x]请输入有效的且大于零的数字",p);
		  return true;
		}
		pd.set(args[2]+".Points", Double.valueOf(args[3]));
		savepd();
		sm("&a[v]积分设置成功",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("view")) {
		if (args.length != 3) {
		  sm("&7正确格式：/dt p view <玩家名>",p);
		  return true;
		}
		double o = pd.getDouble(args[2]+".Points");
		sm("&6玩家&e{player}&6的单挑积分余额 [right] {amount}",p,"player amount",new String[]{args[2],""+o});
		return true;
	  }
	  sendHelp(p);
	  return true;
	}

}

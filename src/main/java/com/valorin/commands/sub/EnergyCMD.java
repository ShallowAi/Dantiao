package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.commands.SubCommand;
import com.valorin.energy.Energy;

public class EnergyCMD extends SubCommand{

	public EnergyCMD() {
		super("energy","e");
	}

	public void sendHelp(Player p) {
		sm("",p);
		sm("&3&lDan&b&l&oTiao &f&l>> &a管理员帮助：决斗精力值操作",p,false);
		sm("&b/dt energy(e) add <玩家名> <数额> &f- &a为某玩家增加精力值",p,false);
		sm("&b/dt energy(e) set <玩家名> <数额> &f- &a设定某玩家的精力值",p,false);
		sm("",p);
	}
	
	public boolean isNum(String str) {
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
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  if (args.length == 1) {
		sendHelp(p);
		return true;
	  }
	  Energy e = Dantiao.getInstance().getEnergy();
	  if (!e.getEnable()) {
		sm("&c[x]精力值系统已被禁用！",p);
        return true;
	  }
	  if (args[1].equalsIgnoreCase("me")) {
		if (!(sender instanceof Player)) {
          sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
          return true;
        }
		sm("&6我的精力值 [right] &b{energy}/&3{maxenergy}",p,"energy maxenergy",
				new String[]{""+e.getEnergy(p.getName()),""+e.getMaxEnergy()});
		return true;
	  }
	  if (!sender.hasPermission("dt.admin")) {
    	sm("&c[x]无权限！",p);
    	return true;
      }
	  if (args[1].equalsIgnoreCase("add")) {
		if (args.length != 4) {
		  sm("&7正确格式：/dt e add <玩家名> <数额>",p);
		  return true;
		}
		if (!isNum(args[3])) {
		  sm("&c[x]请输入有效的且大于零的数字",p);
		  return true;
		}
		if (!getInstance().getPlayerSet().get().contains(args[2])) {
		  sm("&c[x]该玩家不存在！",p);
		  return true;
		}
		e.setEnergy(args[2], e.getEnergy(p.getName())+Double.parseDouble(args[3]));
		sm("&a[v]精力值增添成功",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("set")) {
		if (args.length != 4) {
		  sm("&7正确格式：/dt e set <玩家名> <数额>",p);
		  return true;
		}
		if (!isNum(args[3])) {
		  sm("&c[x]请输入有效的且大于零的数字",p);
		  return true;
		}
		if (!getInstance().getPlayerSet().get().contains(args[2])) {
		  sm("&c[x]该玩家不存在！",p);
		  return true;
		}
		e.setEnergy(args[2], Double.parseDouble(args[3]));
		sm("&a[v]精力值设置成功",p);
		return true;
	  }
	  sendHelp(p);
	  return true;
	}
}

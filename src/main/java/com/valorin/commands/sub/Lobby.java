package com.valorin.commands.sub;

import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.saveAreas;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;

public class Lobby extends SubCommand{

	public Lobby() {
		super("lobby","l");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  if (args.length == 1) {
		if (p == null) {
	      sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
		  return true;
	    }
		if (areas.getString("Dantiao-LobbyPoint.World") == null) {
		  sm("&c[x]服务器未设置大厅",p);
		  return true;
		}
		String world = areas.getString("Dantiao-LobbyPoint.World");
		double x = areas.getDouble("Dantiao-LobbyPoint.X");
		double y = areas.getDouble("Dantiao-LobbyPoint.Y");
		double z = areas.getDouble("Dantiao-LobbyPoint.Z");
		float yaw = (float)areas.getDouble("Dantiao-LobbyPoint.YAW");
		float pitch = (float)areas.getDouble("Dantiao-LobbyPoint.PITCH");
		p.teleport(new Location(Bukkit.getWorld(world),x,y,z,yaw,pitch));
		sm("&b传送至决斗大厅...",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("set")) {
		if (!sender.hasPermission("dt.admin")) {
          sm("&c[x]无权限！",p);
          return true;
        }
		if (p == null) {
	      sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
		  return true;
		}
		Location loc = p.getLocation();
		areas.set("Dantiao-LobbyPoint.World", loc.getWorld().getName());
		areas.set("Dantiao-LobbyPoint.X", loc.getX());
		areas.set("Dantiao-LobbyPoint.Y", loc.getY());
		areas.set("Dantiao-LobbyPoint.Z", loc.getZ());
		areas.set("Dantiao-LobbyPoint.YAW", (float)loc.getYaw());
		areas.set("Dantiao-LobbyPoint.PITCH", (float)loc.getPitch());
		saveAreas();
		sm("&a[v]决斗大厅设置完毕！玩家每次决斗结束后都会自动传送回到决斗大厅",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("delete")) {
		if (!sender.hasPermission("dt.admin")) {
          sm("&c[x]无权限！",p);
          return true;
        }
		if (areas.getString("Dantiao-LobbyPoint.World") == null) {
		  sm("&c[x]不存在决斗大厅！",p);
		}
		areas.set("Dantiao-LobbyPoint", null);
		saveAreas();
		sm("&a[v]决斗大厅删除完毕",p);
		return true;
	  }
	  sm("",p);
	  sm("&b/dt lobby set &f- &a设置服务器的决斗大厅传送点",p,false);
      sm("&b/dt lobby delete &f- &a取消大厅传送点",p,false);
      sm("",p);
	  return true;
	}

}

package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.arenas.ArenasManager.getArenasName;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.saveAreas;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.configuration.languagefile.MessageSender.sml;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.arenas.ArenaCreator;
import com.valorin.arenas.ArenaCreatorsHandler;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;
import com.valorin.itemstack.PlayerItems;
import com.valorin.util.ItemGiver;

public class ArenaOP extends SubCommand implements AdminCommands{

	public ArenaOP() {
		super("arena","a");
	}

	public void sendHelp(Player p) {
		sm("",p);
		sm("&3&lDan&b&l&oTiao &f&l>> &a管理员帮助：竞技场操作",p,false);
		sm("&b/dt arena(a) mode &f- &a进入/退出竞技场设置模式",p,false);
		sm("&b/dt arena(a) create <竞技场编辑名> <竞技场名称(支持颜色符号)> &f- &a创造一个已设置好的竞技场",p,false);
		sm("&b/dt arena(a) remove <竞技场编辑名> &f- &a删除一个已创建的竞技场",p,false);
		sm("&b/dt arena(a) list &f- &a查看所有已创建的竞技场",p,false);
		sm("&b/dt arena(a) sw <竞技场编辑名> &f- &a启用观战功能并设置观战点",p,false);
		sm("&b/dt arena(a) rw <竞技场编辑名> &f- &a取消观战功能并移除观战点",p,false);
		sm("&b/dt arena(a) commands add <竞技场编辑名> <执行方式(player/op/console)> <内容> &f- &a添加一条开赛时执行的指令",p,false);
		sm("&b/dt arena(a) commands clear <竞技场编辑名> &f- &a清空所有已添加的指令",p,false);
		sm("&b/dt arena(a) commands list <竞技场编辑名> &f- &a查看所有已添加的指令",p,false);
		sm("",p);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player) { p = (Player)sender; }
	  if (args.length == 1) {
		sendHelp(p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("commands")) {
		return ArenaOP_Commands.onCommand(sender, cmd, label, args);
	  }
	  if (args[1].equalsIgnoreCase("remove")) {
		if (!getArenasName().contains(args[2])) {
		  sm("&c[x]不存在的竞技场，请检查输入",p);
		  return true;
		}
		getInstance().getArenasHandler().removeArena(args[2]);
		areas.set("Arenas."+args[2], null);
		sm("&a[v]竞技场删除完毕！玩家将无法再进入这个竞技场",p);
		saveAreas();
		return true;
	  }
	  if (args[1].equalsIgnoreCase("list")) {
		List<String> list = getArenasName();
		if (list.size() == 0) {
		  sm("&c[x]未设置任何竞技场",p);
		  return true;
		} else {
		  sm("&6竞技场如下 [right]",p);
		  for (String an : list) {
			sender.sendMessage("§e"+an+"§7("+areas.getString("Arenas."+an+".Name").replace("&", "§")+"§7)");
		  }
		  sm("&6共计 &f&l{amount} &6个",p,"amount",new String[]{""+list.size()});
		}
		return true;
	  }
	  if (args[1].equalsIgnoreCase("rw")) {
		if (args.length != 3) {
		  sm("&7正确用法：/dt a rw <竞技场编辑名>",p);
		  return true;
		}
		String arenaName = args[2];
		if (!getArenasName().contains(arenaName)) {
		  sm("&c[x]不存在的竞技场，请检查输入",p);
		  return true;
		}
		if (areas.getString("Arenas."+arenaName+".WatchingPoint.World") == null) {
		  sm("&c[x]这个竞技场本来就没有设置观战点",p);
		  return true;
		}
		areas.set("Arenas."+arenaName+".WatchingPoint", null);
		saveAreas();
		sm("&a[v]观战点已移除",p);
		return true;
	  }
	  if (p == null) {
		sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("mode")) {
		ArenaCreatorsHandler acs = Dantiao.getInstance().getACS();
		if (acs.getCreators().contains(p.getName())) {
		  acs.removeAC(p.getName());
		  sm("&a[v]已退出竞技场创建模式",p);
		} else {
		  ItemGiver ig = new ItemGiver(p,PlayerItems.getCreator(p));
		  if (!ig.getIsReceive()) {
			return true;
		  }
		  acs.addAC(p.getName());
		  sml("&a[v]现在进入竞技场创建模式&2竞技场快捷创建工具已发送至你的背包，请按如下步骤操作：| | &6使用&f[&e&n左键&f]&6点击空气选择&b&lA&6点| &6使用&f[&e&n右键&f]&6点击空气选择&d&lB&6点| &d使用  &f/dt a create <竞技场编辑名> <竞技场名称(支持颜色符号)> &d完成创建| |&a最后记得再次输入/dt a mode退出创建模式",p);
		}
		return true;
	  }
	  if (args[1].equalsIgnoreCase("create")) {
		if (args.length != 4) {
		  sml("&7正确用法：/dt a create <竞技场编辑名> <竞技场名称(支持颜色符号)>|&7如：/dt a create test 初级竞技场",p);
		  return true;
		}
		ArenaCreatorsHandler acs = Dantiao.getInstance().getACS();
		if (!acs.getCreators().contains(p.getName())) {
		  sm("&c[x]请先进入竞技场创建模式！",p);
		  return true;
		}
		ArenaCreator ac = acs.getAC(p.getName());
		if (ac.getPointA() == null) {
		  sm("&c[x]未设置A点！",p);
		  return true;
		}
		if (ac.getPointB() == null) {
		  sm("&c[x]未设置B点！",p);
		  return true;
		}
		if (getArenasName().contains(args[2])) {
		  sm("&c[x]数据文件中已有竞技场&e{editname}&c了，请换一个编辑名！",p,"editname",new String[]{args[2]});
		  return true;
		}
		
		Location pointA = ac.getPointA(),pointB = ac.getPointB();
		
		areas.set("Arenas."+args[2]+".A.World", pointA.getWorld().getName());
		areas.set("Arenas."+args[2]+".A.X", pointA.getX());
		areas.set("Arenas."+args[2]+".A.Y", pointA.getY());
		areas.set("Arenas."+args[2]+".A.Z", pointA.getZ());
		areas.set("Arenas."+args[2]+".A.YAW", (float)pointA.getYaw());
		areas.set("Arenas."+args[2]+".A.PITCH", (float)pointA.getPitch());
		
		areas.set("Arenas."+args[2]+".B.World", pointB.getWorld().getName());
		areas.set("Arenas."+args[2]+".B.X", pointB.getX());
		areas.set("Arenas."+args[2]+".B.Y", pointB.getY());
		areas.set("Arenas."+args[2]+".B.Z", pointB.getZ());
		areas.set("Arenas."+args[2]+".B.YAW", (float)pointB.getYaw());
		areas.set("Arenas."+args[2]+".B.PITCH", (float)pointB.getPitch());
		
		areas.set("Arenas."+args[2]+".Name", args[3].replace("&", "§"));
		
		saveAreas();
  	    
		getInstance().getArenasHandler().addArena(args[2]);
		sm("&a[v]创建完成！现在玩家可以进入这个竞技场比赛了！现在你可以选择输入/dt a mode退出创建模式，也可以继续进行创建操作！",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("sw")) {
		if (args.length != 3) {
		  sm("&7正确用法：/dt a sw <竞技场编辑名>",p);
		  return true;
		}
		String arenaName = args[2];
		if (!getArenasName().contains(arenaName)) {
		  sm("&c[x]不存在的竞技场，请检查输入",p);
		  return true;
		}
		Location l = p.getLocation();
		
		String arenaWorldName = areas.getString("Arenas."+arenaName+".A.World");
		if (!arenaWorldName.equals(l.getWorld().getName())) {
		  sm("&c[x]观战点所处的世界应该与其对应的竞技场一致！",p);
		  return true;
		}
		
		areas.set("Arenas."+arenaName+".WatchingPoint.World", l.getWorld().getName());
		areas.set("Arenas."+arenaName+".WatchingPoint.X", l.getX());
		areas.set("Arenas."+arenaName+".WatchingPoint.Y", l.getY());
		areas.set("Arenas."+arenaName+".WatchingPoint.Z", l.getZ());
		areas.set("Arenas."+arenaName+".WatchingPoint.YAW", (float)l.getYaw());
		areas.set("Arenas."+arenaName+".WatchingPoint.PITCH", (float)l.getPitch());
		saveAreas();
		sm("&a[v]观战点设置完毕",p);
		return true;
	  }
	  sendHelp(p);
	  return true;
	}
}

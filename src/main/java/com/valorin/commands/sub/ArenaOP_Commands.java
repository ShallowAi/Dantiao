package com.valorin.commands.sub;

import static com.valorin.arenas.ArenasManager.getArenasName;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.saveAreas;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ArenaOP_Commands {
	
	public static void sendHelp(Player p) {
	  sm("&b/dt arena(a) commands add <竞技场编辑名> <执行方式(player/op/console)> <内容> &f- &a添加一条开赛时执行的指令",p);
	  sm("&b/dt arena(a) commands clear <竞技场编辑名> &f- &a清空所有已添加的指令",p);
	  sm("&b/dt arena(a) commands list <竞技场编辑名> &f- &a查看所有已添加的指令",p);
	}
	
	public static boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = null;
		if (sender instanceof Player)
		{ p = (Player)sender; }
		List<String> arenasNameList = getArenasName();
		if (args.length == 2) {
		  sendHelp(p);
		  return true;
		}
		if (args[2].equalsIgnoreCase("list")) {
		  if (args.length != 4) {
          	sm("&7正确格式：/dt a commands list <竞技场编辑名>",p);
            return true;
          }
          if (!arenasNameList.contains(args[3])) {
        	sm("&c[x]不存在的竞技场，请检查输入",p);
        	return true;
          }
          List<String> commandsList = areas.getStringList("Arenas."+args[3]+".Commands");
          if (commandsList.size() == 0) {
        	sm("&c[x]该竞技场不存在任何指令",p);
        	return true;
          }
          sm("&6指令如下 [right]",p);
  		  for (String command : commandsList) {
  			sender.sendMessage("§e"+command.replace("|",":"));
  		  }
  		  sm("&6共计 &f&l{amount} &6条",p,"amount",new String[]{""+commandsList.size()});
          return true;
        }
        if (args[2].equalsIgnoreCase("add")) {
          if (args.length != 6) {
          	sm("&7正确格式：/dt a commands add <竞技场编辑名> <执行方式> <内容>",p);
            return true;
          }
          if (!arenasNameList.contains(args[3])) {
            sm("&c[x]不存在的竞技场，请检查输入",p);
            return true;
          }
          String way = args[4];
          String command = args[5];
          if (!way.equalsIgnoreCase("op") && !way.equalsIgnoreCase("player") && !way.equalsIgnoreCase("console")) {
            sm("&c[x]执行方式请输入op/player/console(不区分大小写)，OP即以管理员身份执行，Player即以玩家自己的身份执行，Console即以后台身份执行",p);
            return true;
          }
          command = way+"|"+command.replace("_", " ");
          if (areas.getStringList("Arenas."+args[3]+".Commands").size() == 0) {
        	List<String> newcommands = new ArrayList<String>();
        	newcommands.add(command);
        	areas.set("Arenas."+args[3]+".Commands", newcommands);
        	saveAreas();
          } else {
        	List<String> commands = areas.getStringList("Arenas."+args[3]+".Commands");
        	commands.add(command);
        	areas.set("Arenas."+args[3]+".Commands", commands);
        	saveAreas();
          }
          sm("&a[v]添加成功 {command}",p,"command",new String[]{command});
          return true;
        }
        if (args[2].equalsIgnoreCase("clear")) {
          if (args.length != 4) {
            sm("&7正确格式：/dt a commands clear <竞技场编辑名>",p);
            return true;
          }
          if (!arenasNameList.contains(args[3])) {
        	sm("&c[x]不存在的竞技场，请检查输入",p);
        	return true;
          }
          if (areas.getStringList("Arenas."+args[3]+".Commands").size() == 0) {
        	sm("&c[x]该竞技场不存在任何指令",p);
        	return true;
          }
          areas.set("Arenas."+args[3]+".Commands", null);
          saveAreas();
          sm("&a[v]竞技场指令清空完毕",p);
          return true;
        }
        sendHelp(p);
  		return true;
	}
}

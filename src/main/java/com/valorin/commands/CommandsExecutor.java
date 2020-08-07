package com.valorin.commands;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import com.valorin.Dantiao;
import com.valorin.commands.sub.MainHelp;
import com.valorin.commands.way.AdminCommands;
import com.valorin.commands.way.InServerCommands;

public class CommandsExecutor implements TabExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
      Player p = null;
      if (sender instanceof Player)
      { p = (Player)sender; }
      if (args.length == 0) {
    	return new MainHelp().onCommand(sender, command, label, args);
      } else {
    	SubCommand subCommand = Dantiao.getInstance().getCommandHandler().getSubCommand(args[0]);
        if (subCommand == null) {
          String similarLabel = SimilarityComparer.getMostSimilarSubCommand(args[0]);
          if (similarLabel == null) {
            sm("&c不存在此则子指令，请检查输入",p);
          } else {
        	sm("&c不存在此则子指令，你是想输入 &e/dt {subcommand} &c吗？",p,"subcommand",new String[]{similarLabel});
          }
          return true;
        }
        if (subCommand instanceof AdminCommands) {
          if (!sender.hasPermission("dt.admin")) {
        	sm("&c[x]无权限！",p);
        	return true;
          }
          return subCommand.onCommand(sender, command, label, args);
        } else if (subCommand instanceof InServerCommands) {
          if (!(sender instanceof Player)) {
            sm("&c[x]这条指令只能由服务器内的玩家执行！后台无法使用！",p);
            return true;
          }
          return subCommand.onCommand(sender, command, label, args);
        } else {
          return subCommand.onCommand(sender, command, label, args);
        }
      }
    }
    
    private final String[] SUBCOMMANDS_ARENAOP = {"mode","create","remove","list","sw","rw","commands"};
    private final String[] SUBCOMMANDS_ARENAOP_COMMANDS = {"add","clear","list"};
    private final String[] SUBCOMMANDS_BLACKLIST = {"add","remove","list","clear"};
    private final String[] SUBCOMMANDS_SHOP = {"add","remove","des","rdes","bc","rbc"};
    private final String[] SUBCOMMANDS_POINTS = {"add","set","view"};
    private final String[] SUBCOMMANDS_ENERGY = {"add","set"};
    private final String[] SUBCOMMANDS_HD = {"win","winremove","kd","kdremove","refresh"};

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
	  if (args.length == 1) {
		  return Arrays.stream(SimilarityComparer.SUBCOMMANDS).filter(s -> s.startsWith(args[0])).collect(Collectors.toList());	
	  } else {
		  if (args[0].equalsIgnoreCase("send")) {
			  if (!(sender instanceof Player)) {
		          return new ArrayList<>();
		      }
			  List<String> playerList = new ArrayList<String>();
			  for (Player player : Bukkit.getOnlinePlayers()) {
				if (!player.getName().equals(((Player)sender).getName())) {
				  playerList.add(player.getName());
				}
			  }
			  return Arrays.stream(playerList.toArray(new String[playerList.size()])).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
		  }
		  
		  if (args[0].equalsIgnoreCase("changelang")) {
			  if (!(sender instanceof Player)) {
		          return new ArrayList<>();
		      }
			  List<File> fileList = Dantiao.getInstance().getLanguageFileLoader().getLanguagesList();
			  List<String> fileNameList = new ArrayList<String>();
			  for (File file : fileList) {
				  fileNameList.add(file.getName().replace(".txt", ""));
			  }
			  return fileNameList;
		  }
		  
		  if (!sender.hasPermission("dt.admin")) {
			  return new ArrayList<String>();
		  }
		  if (args[0].equalsIgnoreCase("a") || args[0].equalsIgnoreCase("arena")) {
			  if (args.length == 3) {
				  if (args[1].equalsIgnoreCase("commands")) {
					  return Arrays.stream(SUBCOMMANDS_ARENAOP_COMMANDS).filter(s -> s.startsWith(args[2])).collect(Collectors.toList());	
				  } else {
					  return new ArrayList<String>();
				  }
			  }
			  return Arrays.stream(SUBCOMMANDS_ARENAOP).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
		  }
		  if (args[0].equalsIgnoreCase("b") || args[0].equalsIgnoreCase("blacklist")) {
			  return Arrays.stream(SUBCOMMANDS_BLACKLIST).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());	
		  }
		  if (args[0].equalsIgnoreCase("s") || args[0].equalsIgnoreCase("shop")) {
			  return Arrays.stream(SUBCOMMANDS_SHOP).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());
		  }
		  if (args[0].equalsIgnoreCase("p") || args[0].equalsIgnoreCase("points")) {
			  return Arrays.stream(SUBCOMMANDS_POINTS).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());	
		  }
		  if (args[0].equalsIgnoreCase("e") || args[0].equalsIgnoreCase("energy")) {
			  return Arrays.stream(SUBCOMMANDS_ENERGY).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());	
		  }
		  if (args[0].equalsIgnoreCase("hd")) {
			  return Arrays.stream(SUBCOMMANDS_HD).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());	
		  }
		  return new ArrayList<String>();
	  }
	}
}

package com.valorin.commands;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;

import com.valorin.commands.sub.AdminHelp;
import com.valorin.commands.sub.ArenaInfo;
import com.valorin.commands.sub.ArenaOP;
import com.valorin.commands.sub.BlackList;
import com.valorin.commands.sub.Changelang;
import com.valorin.commands.sub.Checkv;
import com.valorin.commands.sub.Dan;
import com.valorin.commands.sub.EnergyCMD;
import com.valorin.commands.sub.Exp;
import com.valorin.commands.sub.Game;
import com.valorin.commands.sub.Lobby;
import com.valorin.commands.sub.PlayerHelp;
import com.valorin.commands.sub.Points;
import com.valorin.commands.sub.Quit;
import com.valorin.commands.sub.RankingOP;
import com.valorin.commands.sub.RankingPlayer;
import com.valorin.commands.sub.RecordsCMD;
import com.valorin.commands.sub.Reload;
import com.valorin.commands.sub.RequestAccept;
import com.valorin.commands.sub.RequestDeny;
import com.valorin.commands.sub.RequestSend;
import com.valorin.commands.sub.RequestSendAll;
import com.valorin.commands.sub.ShopCMD;
import com.valorin.commands.sub.Starting;
import com.valorin.commands.sub.Stop;
import com.valorin.commands.sub.Timetable;
import com.valorin.commands.sub.UpdateForcibly;
import com.valorin.commands.sub.WatchGame;

public class CommandsHandler {

    private Set<SubCommand> commands = new HashSet<>();

    public CommandsHandler(String name) {
    	commands.add(new AdminHelp());
    	commands.add(new ArenaOP());
    	commands.add(new BlackList());
    	commands.add(new Changelang());
    	commands.add(new Game());
    	commands.add(new Lobby());
    	commands.add(new PlayerHelp());
    	commands.add(new Points());
    	commands.add(new Quit());
    	commands.add(new RankingOP());
    	commands.add(new RankingPlayer());
    	commands.add(new Reload());
    	commands.add(new RequestAccept());
    	commands.add(new RequestDeny());
    	commands.add(new RequestSend());
    	commands.add(new Stop());
    	commands.add(new Timetable());
    	commands.add(new Dan());
    	commands.add(new RecordsCMD());
    	commands.add(new ShopCMD());
    	commands.add(new EnergyCMD());
    	commands.add(new Starting());
    	commands.add(new UpdateForcibly());
    	commands.add(new ArenaInfo());
    	commands.add(new WatchGame());
    	commands.add(new Checkv());
    	commands.add(new Exp());
    	commands.add(new RequestSendAll());
        Bukkit.getPluginCommand(name).setExecutor(new CommandsExecutor());
        //设置我们自定义的指令执行器
    }

    public SubCommand getSubCommand(String cmd) {
        for (SubCommand command : commands) {
          if (command.getName() != null) {//无简称
            if (command.getName().equalsIgnoreCase(cmd)) {
              return command;
            }
          } else {//有简称
        	if (command.getFullName().equalsIgnoreCase(cmd) || command.getAbbreviation().equalsIgnoreCase(cmd)) {
              return command;
            }
          }
        }
        return null;
    }

    public Set<SubCommand> getCommands() {
        return commands;
    }
}

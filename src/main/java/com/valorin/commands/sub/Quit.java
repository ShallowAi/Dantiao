package com.valorin.commands.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import com.valorin.arenas.Arena;
import com.valorin.arenas.ArenasManager;
import com.valorin.arenas.Finisher;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.InServerCommands;

public class Quit extends SubCommand implements InServerCommands{

	public Quit() {
		super("quit","q");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = (Player)sender;
	  ArenasManager ah = getInstance().getArenasHandler();
	  String arenaname = ah.getPlayerOfArena(p.getName());
	  Arena a = ah.getArena(arenaname);
	  if (arenaname != null)
	  {
		Player theother = Bukkit.getPlayerExact(a.getTheOtherPlayer(p.getName()));
		sm("&b对方向你认输了！",theother);
		Finisher.normalEnd(arenaname, getInstance().getArenasHandler().getTheOtherPlayer(p.getName()), p.getName(), false);
	  } else {
		sm("&c[x]你现在不在任何一场比赛中",p);
	  }
	  return true;
	}

}

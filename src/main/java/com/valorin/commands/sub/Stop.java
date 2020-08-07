package com.valorin.commands.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.arenas.Finisher;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;

import static com.valorin.configuration.languagefile.MessageSender.sm;

public class Stop extends SubCommand implements AdminCommands{

	public Stop() {
		super("stop");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  if (args.length != 2) {
		sm("&7正确格式：/dt stop <竞技场名称>",p);
		return true;
	  }
	  Finisher.compulsoryEnd(args[1], p);
	  return true;
	}

}

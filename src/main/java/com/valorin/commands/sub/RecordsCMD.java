package com.valorin.commands.sub;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;
import com.valorin.commands.way.InServerCommands;
import com.valorin.inventory.Records;

public class RecordsCMD extends SubCommand implements InServerCommands{

	public RecordsCMD() {
		super("records","r");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  Records.openInv(p.getName());
	  sm("&a[v]记录面板已打开，查看你的每一次精彩表现！",p);
	  return true;
	}
}

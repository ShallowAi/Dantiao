package com.valorin.commands.sub;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.valorin.Dantiao;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;
import com.valorin.task.VersionChecker;

public class Checkv extends SubCommand implements AdminCommands{

	public Checkv() {
		super("checkv");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		VersionChecker vc = new VersionChecker();
		vc.setSend(sender);
		vc.runTaskAsynchronously(Dantiao.getInstance());
	    return true;
	}

}

package com.valorin.commands.sub;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;
import com.valorin.configuration.updata.FileVersionChecker;

public class UpdateForcibly extends SubCommand implements AdminCommands{

	public UpdateForcibly() {
		super("updateforcibly");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  sender.sendMessage("§d§lDantiao: OK!");
	  Bukkit.getConsoleSender().sendMessage("§d§lDantiao: Updating file forcibly now!(开始强制更新跨版本配置)");
	  FileVersionChecker.execute(true);
	  return true;
	}
}

package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.commands.SubCommand;
import com.valorin.commands.way.InServerCommands;
import com.valorin.dan.DansHandler;

public class Dan extends SubCommand implements InServerCommands{

	public Dan() {
		super("dan");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  DansHandler dh = getInstance().getDansHandler();
	  sm("",p);
	  sm("&b我的段位信息 [right]",p,false);
	  sm("",p);
	  sm("   &e&l> &r{dan} &e&l<",p,"dan",new String[]{dh.getPlayerDan(p.getName()).getDanName()},false);
	  sm("",p);
	  sm("&a[v]现有经验：{exp}",p,"exp",new String[]{""+pd.getInt(p.getName() + ".Exp")},false);
	  sm("&a[v]升级所差：{needexp}",p,"needexp",new String[]{""+dh.getNeedExpToLevelUp(p.getName())},false);
	  sm("",p);
	  return true;
	}

}

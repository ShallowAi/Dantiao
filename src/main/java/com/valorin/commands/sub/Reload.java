package com.valorin.commands.sub;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.Dantiao.setPrefix;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.google.common.base.Charsets;
import com.valorin.Dantiao;
import com.valorin.commands.SubCommand;
import com.valorin.commands.way.AdminCommands;

public class Reload extends SubCommand implements AdminCommands{

	public Reload() {
		super("reload");
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  if (args.length == 1) {
		sm("&a输入 &b/dt reload c &a重载配置config.yml",p);
		sm("&a输入 &b/dt reload l &a重载所有语言文件",p);
		return true;
	  }
	  if (p != null) {
	    if (getInstance().getArenasHandler().isPlayerBusy(p.getName())) {//OP比赛时输入
		  return true;
	    }
	  }
	  if (args[1].equalsIgnoreCase("c")) {
	    try {
		  long start = System.currentTimeMillis();
		  reloadConfiguration();
		  getInstance().getHD().checkHD();
		  getInstance().getHD().unload(0);
		  getInstance().getHD().load(0);
		  getInstance().reloadConfig();
		  getInstance().getDansHandler().load();
		  setPrefix(getInstance().getConfig().getString("Message.Prefix").replace("&", "§"));
		  getInstance().reloadEnergy();
		  getInstance().reloadTimeTable();
		  long end = System.currentTimeMillis();
		  sm("&a[v]config.yml:重载完毕！耗时&d{ms}毫秒",p,"ms",new String[]{""+(end-start)});
	    } catch (Exception e) {
		  sm("&c[x]config.yml:重载时发生异常！建议重启本插件(若服务器装有具有重载其他插件功能的插件)或重启服务器",p);
	    }
		return true;
	  }
	  if (args[1].equalsIgnoreCase("l")) {
		try {
		  long start = System.currentTimeMillis();
		  getInstance().reloadLanguageFileLoad();
	      long end = System.currentTimeMillis();
	      sm("&a[v]Language file:重载完毕！耗时&d{ms}毫秒",p,"ms",new String[]{""+(end-start)});
		} catch (Exception e) {
		  sm("&c[x]Language file:重载时发生异常！建议重启本插件(若服务器装有具有重载其他插件功能的插件)或重启服务器",p);
		}
		return true;
	  }
	  return true;
	}
	
	public static void reloadConfiguration() {
	  Dantiao.getInstance().reloadConfig();
	  try {
        FileConfiguration config = new YamlConfiguration();
        File file = new File(getInstance().getDataFolder(), "config.yml");
        if (!file.exists()) {
          Dantiao.getInstance().saveResource("config.yml", false);
        }
        config.load(new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8)));
        
        FileConfiguration symbols = new YamlConfiguration();
        File file2 = new File(getInstance().getDataFolder(), "symbols.yml");
        if (!file2.exists()) {
          Dantiao.getInstance().saveResource("symbols.yml", false);
        }
        symbols.load(new BufferedReader(new InputStreamReader(new FileInputStream(file2), Charsets.UTF_8)));
	  } catch (Exception e) {
		e.printStackTrace();
	  }
	}
}

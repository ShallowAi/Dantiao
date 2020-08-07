package com.valorin.configuration.updata;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import com.valorin.Dantiao;

public class UpdateConfig {
   public UpdateConfig(boolean isForcibly) {
	 if (FileVersionChecker.get() || isForcibly) {
	   Bukkit.getConsoleSender().sendMessage("§7Dantiao: config.yml is updating now...(config.yml文件开始跨版本更新...)");
	   FileConfiguration config = Dantiao.getInstance().getConfig();
	   String logPrefix = config.getString("Prefix");
	   int logRewardPoints = config.getInt("Reward-Points");
	   double logTirednessRequirePerDantiao = config.getDouble("Tiredness.RequirePerDantiao");
	   double logTirednessResumePerSecond = config.getDouble("Tiredness.ResumePerSecond");
	   double logTirednessMax = config.getDouble("Tiredness.Max");
	   List<String> logTimetableSearching = config.getStringList("Timetable");
	   boolean logIsCheckVersion = config.getBoolean("isCheckVersion");
	   
	   config.set("Message.Prefix", logPrefix);
	   config.set("Energy.Enable", true);
	   config.set("Energy.Need", logTirednessRequirePerDantiao);
	   config.set("Energy.Max",logTirednessMax);
	   config.set("Energy.PerSecond", logTirednessResumePerSecond);
	   if (logTimetableSearching.size() != 0)
	   {
	     config.set("Timetable.Searching", logTimetableSearching);
	   }
	   config.set("Rewards.Points", (double)logRewardPoints);
	   config.set("CheckVersion", logIsCheckVersion);
	   
	   config.set("Auto-Heal",null);
	   config.set("Banduanwei", null);
	   
	   Dantiao.getInstance().saveConfig();
	   
	   Bukkit.getConsoleSender().sendMessage("§aDantiao: config.yml updated successfully!(config.yml文件更新成功！)");
	 }
   }
}

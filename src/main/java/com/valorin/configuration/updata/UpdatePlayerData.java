package com.valorin.configuration.updata;

import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.ranking;
import static com.valorin.configuration.DataFile.saveRanking;
import static com.valorin.configuration.DataFile.savepd;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class UpdatePlayerData {
   public UpdatePlayerData(boolean isForcibly) {
	 if (FileVersionChecker.get() || isForcibly) {
	   Bukkit.getConsoleSender().sendMessage("§7Dantiao: pd.yml and ranking.yml are updating now...(pd.yml和ranking.yml文件开始跨版本更新...)");
	   File logpdFile = new File("plugins/Dantiao/pd.yml");
	   FileConfiguration logpd = YamlConfiguration.loadConfiguration(logpdFile);
	   File logPointsFile = new File("plugins/Dantiao/points.yml");
	   FileConfiguration logPoints = YamlConfiguration.loadConfiguration(logPointsFile);
	   
	   List<String> logRankingWin = logpd.getStringList("Rank.win");
	   List<String> logRankingKD = logpd.getStringList("Rank.kd");
	   List<String> logPlayerSet = logpd.getStringList("Energy.Players");
	   Map<String,Integer> logPlayerPoints = new HashMap<String,Integer>();
	   for (String playerName : logPlayerSet)
	   {
		 logPlayerPoints.put(playerName, logPoints.getInt(playerName));
	   }
	   
	   for (String playerName : logPlayerSet)
	   {
		 pd.set(playerName+".Name", playerName);
		 pd.set(playerName+".Points", logPlayerPoints.get(playerName));
	   }
	   
	   if (logRankingWin.size() != 0)
	   {
	     ranking.set("Win", logRankingWin);
	   }
	   if (logRankingKD.size() != 0)
	   {
	     ranking.set("KD", logRankingKD);
	   }
	   
	   savepd();
	   saveRanking();
	   Bukkit.getConsoleSender().sendMessage("§aDantiao: pd.yml and ranking.yml updated successfully!(pd.yml和ranking.yml文件更新成功！)");
	 }
   }
}

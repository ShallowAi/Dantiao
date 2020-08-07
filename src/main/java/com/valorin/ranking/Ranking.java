package com.valorin.ranking;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import static com.valorin.configuration.DataFile.*;

public class Ranking {
	public void rank(String s,boolean isWin) {//添加排行榜元素
	  String type = "";
	  if (isWin)
	  {
		type = "Win";
	  } else {
		type = "KD";
	  }
	  if (ranking.getStringList(type).size() != 0) {
		List<String> list = ranking.getStringList(type);
		for (int i = 0;i < list.size();i++) {
		  if (list.get(i).split("\\|")[0].equals(s.split("\\|")[0])) {
			list.remove(i);
		  }
		}
		double n = Double.valueOf(s.split("\\|")[1]);
		int originalSize = list.size();
		list.add("");
		for (int i = (originalSize-1);i >= 0;i--) {
		  double a = Double.valueOf(list.get(i).split("\\|")[1]);
		  if (n <= a) {//如果小于等于比较对象，终止
			for (int i2 = (originalSize-1);i2 > i;i2--) {
			  list.set(i2+1, list.get(i2));
			}
			list.set(i+1, s);
			ranking.set(type, list);
			saveRanking();
			return;
		  }
		}
		for (int i2 = (originalSize-1);i2 >= 0;i2--) {
		  list.set(i2+1, list.get(i2));
		}
		list.set(0, s);
		ranking.set(type, list);
		saveRanking();
	  } else {
		List<String> list = new ArrayList<String>();
		list.add(s);
		ranking.set(type, list);
		saveRanking();
	  }
	}
	
	public void reloadRanking() {
	  for (String playerName : records.getKeys(false)) {
		rank(playerName+"|"+records.getInt(playerName+".Win"), true);
		
		if (records.getInt(playerName+".Lose") != 0) {
		  rank(playerName+"|"+
			  ((double)records.getInt(playerName+".Win")/
			  (double)records.getInt(playerName+".Lose")),false);
		} else {
		  rank(playerName+"|"+
			  ((double)records.getInt(playerName+".Win")),false);
		}
	  }
	  saveRecords();
	}
	
	public int getWin(Player p) {//获取胜场榜排名
	  int n = 0;
	  if (ranking.getStringList("Win").size() == 0) {
		return n;
	  }
	  for (int i = 0;i < ranking.getStringList("Win").size();i++) {
		if (ranking.getStringList("Win").get(i).split("\\|")[0].equals(p.getName())) {
		  n = i + 1;
		}
	  }
	  return n;
	}
	
	public String getPlayerByWin(int rank) {//获取胜场榜排名对应的玩家
	  List<String> list = ranking.getStringList("Win");
	  if (list.size() < rank) {
		return null;
	  }
	  return list.get(rank).split("\\|")[0];
	}
	
	public int getKD(Player p) {//获取KD榜排名
	  int n = 0;
	  if (ranking.getStringList("KD").size() == 0) {
		return n;
	  }
	  for (int i = 0;i < ranking.getStringList("KD").size();i++) {
		if (ranking.getStringList("KD").get(i).split("\\|")[0].equals(p.getName())) {
		  n = i + 1;
		}
	  }
	  return n;
	}
	
	public String getPlayerByKD(int rank) {//获取KD榜排名对应的玩家
	  List<String> list = ranking.getStringList("KD");
	  if (list.size() < rank) {
		return null;
	  }
	  return list.get(rank).split("\\|")[0];
	}
}
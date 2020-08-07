package com.valorin.dan;

import static com.valorin.configuration.DataFile.pd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;

import com.valorin.Dantiao;
import com.valorin.dan.type.Initial;

public class DansHandler 
{
  private Map<String,CommonDan> playerDans;
  private List<CommonDan> danList;
  private InitialDan initialDan;
  
  public DansHandler() {
	playerDans = new HashMap<String,CommonDan>();
	danList = new ArrayList<CommonDan>();
	initialDan = new InitialDan();
	load();
  }
  
  public List<CommonDan> getDanList() {
	return danList;
  }
  
  public Dan getPlayerDan(String playerName) {
	if (playerDans.containsKey(playerName)) {
	  return (CommonDan) playerDans.get(playerName);
	} else {
	  return initialDan;
	}
  }
  
  public int getNeedExpToLevelUp(String playerName) {
	int expNow = pd.getInt(playerName+".Exp");
	int expNeed;
	if (getPlayerDan(playerName) instanceof Initial) {
	  expNeed = danList.get(0).getExp() - expNow;
	} else {
	  if (getPlayerDan(playerName).getNum() == danList.size() - 1) {
	    expNeed = 0;
	  } else {
		expNeed = danList.get(getPlayerDan(playerName).getNum() + 1).getExp() - expNow;
	  }
	}
	return expNeed;
  }
  
  public void refreshPlayerDan(String playerName) {
	int exp = pd.getInt(playerName+".Exp");
	if (exp >= danList.get(0).getExp()) {
	  boolean max = true;
	  for (int num = 0;num < danList.size();num++) {
	    if (exp < danList.get(num).getExp()) {
	      playerDans.put(playerName, danList.get(num - 1));
	      max = false;
	      return;
	    }
	  }
	  if (max) {
		playerDans.put(playerName, danList.get(danList.size() - 1));
	  }
	} else {
      if (playerDans.containsKey(playerName)) {
    	playerDans.remove(playerName);
      }
	}
  }
  
  public void load() {//也可做reload
	danList.clear();
	boolean isUseDefault = false;
	int useDefaultReason = 0;
	
	Configuration config = Dantiao.getInstance().getConfig();
	List<String> editNameList = new ArrayList<String>();
	config.getConfigurationSection("Dan").getKeys(false).forEach(key-> {
	  if (!editNameList.contains(key)) {
		editNameList.add(key);
	  }
	});
	if (editNameList.size() == 0) {
	  useDefaultReason = 1;
	  isUseDefault = true;
	}
	
	List<CommonDan> danList = new ArrayList<CommonDan>();
	
	List<String> danNameList = new ArrayList<String>();
	int expMark = 0;
	for (int n = 0;n < editNameList.size();n++) {
	  String editName = editNameList.get(n);
	  
	  String danName = config.getString("Dan."+editName+".Name");
	  if (danNameList.contains(danName)) {
		isUseDefault = true;
		useDefaultReason = 2;
		break;
	  } else {
		danNameList.add(danName);
	  }
	  
	  int exp = config.getInt("Dan."+editName+".Exp");
	  if (exp <= expMark) {
		isUseDefault = true;
		useDefaultReason = 3;
		break;
	  }
	  
	  danList.add(new CommonDan(n, editName, danName, exp));
	}
	if (isUseDefault) {
	  this.danList = new DefaultDanLoader().get();
	  Bukkit.getConsoleSender().sendMessage("§8[§bDantiao§8]");
	  Bukkit.getConsoleSender().sendMessage("§c自定义段位加载失败！");
	  Bukkit.getConsoleSender().sendMessage("§cFailed to load the custom dans!");
	  if (useDefaultReason == 1) {
		Bukkit.getConsoleSender().sendMessage("§6[原因(Reason)] §e未发现任何自定义段位名，你可以尝试输入/dt reload c解决这个问题！"
				+ "(Cannot find any custom dan,you can try to use /dt reload c for fixing this problem!)");
	  }
	  if (useDefaultReason == 2) {
		Bukkit.getConsoleSender().sendMessage("§6[原因(Reason)] §e自定义段位的段位名出现了重复！"
				+ "(Duplicate dan names of the custom dans!)");
	  }
	  if (useDefaultReason == 3) {
	    Bukkit.getConsoleSender().sendMessage("§6[原因(Reason)] §e自定义段位请根据其所需经验值的多少来排序而设置，从少到多"
				+ "(If you want to have custom dans,you must order them according to their exp of need when you edit dans)");
	    Bukkit.getConsoleSender().sendMessage("§6[示例(Example)]");
	    Bukkit.getConsoleSender().sendMessage("§fDan:");
	    Bukkit.getConsoleSender().sendMessage("§f  myCustomDan1:");
	    Bukkit.getConsoleSender().sendMessage("§f    name: '&aPVP LEVEL I");
	    Bukkit.getConsoleSender().sendMessage("§f    exp: 50");
	    Bukkit.getConsoleSender().sendMessage("§f  myCustomDan2:");
	    Bukkit.getConsoleSender().sendMessage("§f    name: '&6PVP LEVEL II");
	    Bukkit.getConsoleSender().sendMessage("§f    exp: 200");
	  }
	} else {
	  this.danList = danList;
	}
	if (this.danList.size() != 0) {
	  List<String> playerNameList = Dantiao.getInstance().getPlayerSet().get();
	  for (String playerName : playerNameList) {
	    refreshPlayerDan(playerName);
	  }
	}
  }
}
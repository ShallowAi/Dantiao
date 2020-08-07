package com.valorin.papi;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.api.DantiaoAPI.getMaxEnergy;
import static com.valorin.api.DantiaoAPI.getPlayerDanName;
import static com.valorin.api.DantiaoAPI.getPlayerDraw;
import static com.valorin.api.DantiaoAPI.getPlayerEnergy;
import static com.valorin.api.DantiaoAPI.getPlayerKD;
import static com.valorin.api.DantiaoAPI.getPlayerKDRank;
import static com.valorin.api.DantiaoAPI.getPlayerLose;
import static com.valorin.api.DantiaoAPI.getPlayerPoints;
import static com.valorin.api.DantiaoAPI.getPlayerWin;
import static com.valorin.api.DantiaoAPI.getPlayerWinRank;
import static com.valorin.configuration.DataFile.records;
import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import me.clip.placeholderapi.PlaceholderAPI;
import me.clip.placeholderapi.PlaceholderHook;

import org.bukkit.entity.Player;

import com.valorin.request.RequestsHandler;

public class RegPAPI extends PlaceholderHook
{
  private static final String HOOK_NAME = "dantiao";
  
  @Override
  public String onPlaceholderRequest(Player p, String i)  {
    if (p == null) {
      return null;
    }
    if (i.equalsIgnoreCase("points")) {
      return String.valueOf(getPlayerPoints(p));
    }
    if (i.equalsIgnoreCase("win")) {
      return String.valueOf(getPlayerWin(p));
    }
    if (i.equalsIgnoreCase("lose")) {
      return String.valueOf(getPlayerLose(p));
    }
    if (i.equalsIgnoreCase("draw")) {
      return String.valueOf(getPlayerDraw(p));
    }
    if (i.equalsIgnoreCase("total")) {
      int winTime = getPlayerWin(p);
      int loseTime = getPlayerLose(p);
      int drawTime = getPlayerDraw(p);
      int totalTime = winTime + loseTime + drawTime;
      return String.valueOf(totalTime);
    }
    if (i.equalsIgnoreCase("isWin")) {
      int amount = records.getInt(p.getName() + ".Win") + records.getInt(p.getName() + ".Lose") + records.getInt(p.getName() + ".Draw");
      if (amount == 0) {
        return gm("&7无数据",p);
      }
      if (records.getBoolean(p.getName() + ".Record." + (amount - 1) + ".isWin")) {
        return gm("&a[v]胜利",p);
      } else {
        if (!records.getBoolean(p.getName() + ".Record." + (amount - 1) + ".isDraw")) {
          return gm("&c[x]败北",p);
        } else {
          return gm("&6=平局");
        }
      }
    }
    if (i.equalsIgnoreCase("energy")) {
      return String.valueOf(getPlayerEnergy(p));
    }
    if (i.equalsIgnoreCase("maxenergy")) {
      return String.valueOf(getMaxEnergy());
    }
    if (i.equalsIgnoreCase("kd")) {
      return String.valueOf(getPlayerKD(p));
    }
    if (i.equalsIgnoreCase("winrank")) {
      return String.valueOf(getPlayerWinRank(p));
    }
    if (i.equalsIgnoreCase("kdrank")) {
      return String.valueOf(getPlayerKDRank(p));
    }
    if (i.equalsIgnoreCase("isInvited")) {
      RequestsHandler request = getInstance().getRequestsHandler();
      if (request.getSenders(p.getName()).size() != 0) {
        return gm("&a{amount}条",p,"amount",new String[]{request.getSenders(p.getName()).size()+""});
      } else {
        return gm("&7暂无",p);
      }
    }
    if (i.equalsIgnoreCase("duanwei")) {
      return getPlayerDanName(p);
    }
    if (i.equalsIgnoreCase("lastdamage")) {
      int amount = records.getInt(p.getName() + ".Win") + records.getInt(p.getName() + ".Lose") + records.getInt(p.getName() + ".Draw");
      if (amount == 0) {
        return gm("&7无数据",p);
      }
      return String.valueOf(records.getInt(p.getName() + ".Record." + (amount - 1) + ".damage"));
    }
    if (i.equalsIgnoreCase("lastmaxdamage")) {
      int amount = records.getInt(p.getName() + ".Win") + records.getInt(p.getName() + ".Lose") + records.getInt(p.getName() + ".Draw");
      if (amount == 0) {
        return gm("&7无数据",p);
      }
      return String.valueOf(records.getInt(p.getName() + ".Record." + (amount - 1) + ".maxdamage"));
    }
    if (i.equalsIgnoreCase("lastopponent")) {
      int amount = records.getInt(p.getName() + ".Win") + records.getInt(p.getName() + ".Lose") + records.getInt(p.getName() + ".Draw");
      if (amount == 0) {
        return gm("&7无数据",p);
      }
      return records.getString(p.getName() + ".Record." + (amount - 1) + ".player");
    }
    if (i.equalsIgnoreCase("lasttime")) {
      int amount = records.getInt(p.getName() + ".Win") + records.getInt(p.getName() + ".Lose") + records.getInt(p.getName() + ".Draw");
      if (amount == 0) {
        return gm("&7无数据",p);
      }
      return String.valueOf(records.getInt(p.getName() + ".Record." + (amount - 1) + ".time"));
    }
    if (i.equalsIgnoreCase("lastdate")) {
      int amount = records.getInt(p.getName() + ".Win") + records.getInt(p.getName() + ".Lose") + records.getInt(p.getName() + ".Draw");
      if (amount == 0) {
        return gm("&7无数据",p);
      }
      return records.getString(p.getName() + ".Record." + (amount - 1) + ".date");
    }
    if (i.equalsIgnoreCase("winning-streak")) {
      return ""+pd.getInt(p.getName()+".Winning-Streak-Times");
    }
    if (i.equalsIgnoreCase("max-winning-streak")) {
      return ""+pd.getInt(p.getName()+".Max-Winning-Streak-Times");
    }
    if (i.startsWith("winrank")) {
      String numberString = i.replace("winrank", "");
      int rank = -1;
      try {
    	rank = Integer.parseInt(numberString);
      } catch (Exception e) { rank = -1; }
      if (rank == -1) {
    	return gm("&7无数据",p);
      } else {
    	String playerName = getInstance().getRanking().getPlayerByWin(rank - 1);
    	if (playerName == null) {
    	  return gm("&7无数据",p);
    	} else {
    	  return playerName;
    	}
      }
    }
    if (i.startsWith("kdrank")) {
      String numberString = i.replace("kdrank", "");
      int rank = -1;
      try {
      	rank = Integer.parseInt(numberString);
      } catch (Exception e) { rank = -1; }
      if (rank == -1) {
      	return gm("&7无数据",p);
      } else {
        String playerName = getInstance().getRanking().getPlayerByKD(rank - 1);
        if (playerName == null) {
      	  return gm("&7无数据",p);
        } else {
      	  return playerName;
        }
      }
    }
    return null;
  }

  public static void hook() {
      PlaceholderAPI.registerPlaceholderHook(HOOK_NAME, new RegPAPI());
  }

  public static void unhook() {
      PlaceholderAPI.unregisterPlaceholderHook(HOOK_NAME);
  }
}
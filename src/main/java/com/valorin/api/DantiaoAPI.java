package com.valorin.api;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.records;

import java.math.BigDecimal;

import org.bukkit.entity.Player;

import com.valorin.dan.DansHandler;
import com.valorin.energy.Energy;
import com.valorin.ranking.Ranking;

/**
 * 单挑插件新版API
 * @version 2.0正式版
 * @author Valorin
 * @since 2020/2/23
 */
public class DantiaoAPI
{
  /**
   * 获取某玩家的单挑积分
   * @param p 玩家
   * @return 玩家的单挑积分
   */
  public static double getPlayerPoints(Player p) {
    return pd.getDouble(p.getName()+".Points");
  }
  /**
   * 设置某玩家的单挑积分
   * @param p 玩家
   * @param value 值
   * @return 玩家的单挑积分
   */
  public static void setPlayerPoints(Player p,double value) {
    pd.set(p.getName()+".Points",value);
  }
  /**
   * 获取某玩家的胜利场数
   * @param p 玩家
   * @return 玩家的胜利场数
   */
  public static int getPlayerWin(Player p) {
    return records.getInt(p.getName() + ".Win");
  }
  
  /**
   * 获取某玩家的战败场数
   * @param p 玩家
   * @return 玩家的战败场数
   */
  public static int getPlayerLose(Player p) {
    return records.getInt(p.getName() + ".Lose");
  }
  
  /**
   * 获取某玩家的平局场数
   * @param p 玩家
   * @return 玩家的平局场数
   */
  public static int getPlayerDraw(Player p) {
    return records.getInt(p.getName() + ".Draw");
  }
  /**
   * 获取某玩家的精力值
   * @param p 玩家
   * @return >=0,实际的精力值
   * @return -1,无限能量（精力值系统被禁用时）
   */
  public static double getPlayerEnergy(Player p) {
    Energy energy = getInstance().getEnergy();
    if (energy.getEnable()) {
      BigDecimal bg = new BigDecimal(energy.getEnergy(p.getName()));
      double value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
      return value;
    } else {
      return -1;
    }
  }
  /**
   * 获取设置的最大精力值
   * @param p 玩家
   * @return >=0,实际设置的最大精力值
   * @return -1,无限能量（精力值系统被禁用时）
   */
  public static double getMaxEnergy() {
    Energy energy = getInstance().getEnergy();
    if (energy.getEnable() == true) {
      return energy.getMaxEnergy();
    } else {
      return -1;
    }
  }
  /**
   * 获取某玩家的KD值（胜场数/败场数的值）
   * @param p 玩家
   * @return 玩家的KD值
   */
  public static double getPlayerKD(Player p) {
    double kd = 0;
    if ((double)records.getInt(p.getName()+".Lose") != 0) {
 	  kd = (double)records.getInt(p.getName()+".Win")/
 		   (double)records.getInt(p.getName()+".Lose");
 	} else {
 	  kd = (double)records.getInt(p.getName()+".Win");
 	}
    BigDecimal bg = new BigDecimal(kd);
    double value = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    return value;
  }
  /**
   * 获取某玩家的胜场数排名
   * @param p 玩家
   * @return 玩家的胜场数排名
   */
  public static int getPlayerWinRank(Player p) {
    Ranking ranking = getInstance().getRanking();
    return ranking.getWin(p);
  }
  /**
   * 获取某玩家的KD值排名
   * @param p 玩家
   * @return 玩家的KD值排名
   */
  public static int getPlayerKDRank(Player p) {
    Ranking ranking = getInstance().getRanking();
    return ranking.getKD(p);
  }
  /**
   * 获取某玩家的段位名称
   * @param p 玩家
   * @return 玩家的胜场数排名
   */
  public static String getPlayerDanName(Player p) {
    DansHandler dan = getInstance().getDansHandler();
    return dan.getPlayerDan(p.getName()).getDanName();
  }
}
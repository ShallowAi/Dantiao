package com.valorin.arenas;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.valorin.Dantiao;
import com.valorin.dan.Dan;

public class Arena {
  /*
   * 竞技场属性：
   * 
   * 竞技场名称（编辑名）
   * 是否启用（有玩家在PVP，否为false，是为true）
   * 阶段（0为前三秒的准备，1为对打中）
   * 玩家1（String形式）
   * 玩家2（String形式）
   * 观战者们（List<String>形式）
   * Timer（独立的定时器，计时）
   * 时刻（进行的秒数，从-2开始）
   * 赛前倒计时
   * 玩家1的总伤害/最大伤害
   * 玩家2的总伤害/最大伤害
   * 玩家1积累的经验值
   * 玩家2积累的经验值
   * 玩家1原来的位置
   * 玩家2原来的位置
   * 玩家1原来的段位
   * 玩家2原来的段位
   */
  private String name;
  private String pn1;
  private String pn2;
  private List<String> watchers;
  
  private boolean enable = false;
  
  private BukkitTask timer;
  private int time;
  private int countDown;
  
  private int stage = -1;
  
  private double player1Damage;
  private double player1MaxDamage;
  private double player2Damage;
  private double player2MaxDamage;
  
  private int player1Exp;
  private int player2Exp;
  
  private int player1Mutilhit;
  private int player2Mutilhit;
  
  private Location player1Location;
  private Location player2Location;
  
  private Dan player1Dan;
  private Dan player2Dan;
  
  private boolean canTeleport;
  private boolean watchersTeleport;
  
  public Arena(String name) {//初始化 
	this.name = name;
  }
  
  public void start(String pn1,String pn2) {//加载
	this.pn1 = pn1;
	this.pn2 = pn2;
	watchers = new ArrayList<String>();
	canTeleport = false;
	watchersTeleport = false;
	enable = true;
	time = getCountDown();
	stage = 0; //阶段标志变量
	Player p1 = Bukkit.getPlayerExact(pn1);
	Player p2 = Bukkit.getPlayerExact(pn2);
	sm("&7决斗即将开始..",p1,p2);
	timer = new BukkitRunnable() {
	  @Override
	  public void run() {
		Player p1 = Bukkit.getPlayerExact(pn1);
		Player p2 = Bukkit.getPlayerExact(pn2);
		time = time + 1;
		if (time >= 0) {
		  if (time == 0) {
		  	stage = 1; //阶段变更：战前准备->战斗状态
		  	sm("&a决斗开始！！亮剑吧！",p1,p2);
		  	ArenaCommands.ExecuteArenaCommands(name, p1, p2);
		  }
		  if (time == 60) {
		  	sm("&7决斗已进行一分钟..",p1,p2);
		  }
		  if (time == 120) {
		  	sm("&7决斗已进行两分钟..",p1,p2);
		  }
		  if (time == 180) {
		  	sm("&7决斗已进行三分钟！达到五分钟时仍为决出胜负则将判定为平局！",p1,p2);
		  }
		  if (time == 240) {
		  	sm("&7决斗已进行四分钟！达到五分钟时仍为决出胜负则将判定为平局！请抓紧时间",p1,p2);
		  }
		  if (time == 300) {
		  	Finisher.normalEnd(name, pn1, pn2, true);
		  }
		} else {
		  sm("&7决斗开始倒计时 &b{time}s",p1,"time",new String[]{""+(0-time)});
		  sm("&7决斗开始倒计时 &b{time}s",p2,"time",new String[]{""+(0-time)});
		}
	  }
    }.runTaskTimerAsynchronously(Dantiao.getInstance(), 20, 20);
  }
  
  public void finish() {//结束这个竞技场的决斗
	timer.cancel();
	enable = false;
	pn1 = null;
	pn2 = null;
	watchers = null;
	player1Damage = 0;
	player1MaxDamage = 0;
	player2Damage = 0;
	player2MaxDamage = 0;
	player1Exp = 0;
	player2Exp = 0;
	player1Mutilhit = 0;
	player2Mutilhit = 0;
	player1Location = null;
	player2Location = null;
	stage = -1;
	watchersTeleport = false;
  }
  
  public String getName() {
	return name;
  }
  
  public String getp1() {
	return pn1;
  }
  
  public String getp2() {
	return pn2;
  }
  
  public List<String> getWatchers() {
	return watchers;
  }
  
  public void removeWatcher(String watcher) {
	if (!watchers.contains(watcher)) {
	  return;
	}
	watchers.remove(watcher);
  }
  
  public void addWatcher(String watcher) {
	if (watchers.contains(watcher)) {
	  return;
	}
	watchers.add(watcher);
  }
  
  public String getTheOtherPlayer(String pn) {
	String theother = null;
	if (pn.equals(pn1)) {
	  theother = pn2;
	}
	if (pn.equals(pn2)) {
	  theother = pn1;
	}
	return theother;
  }
  
  public boolean isp1(String pn) {
	if (pn1.equals(pn)) {
      return true;
	}
	return false;
  }
  
  public int getStage() {
	return stage;
  }
  
  public int getTime() {
	return time;
  }
  
  public int getCountDown() {
	if (countDown == 0) {
	  return -6;
	} else {
	  return 0-countDown;
	}
  }
  
  public void setCountDown(int countDown) {
	this.countDown = countDown;
  }
  
  public boolean getEnable() {
	return enable;
  }
  
  public double getDamage(boolean isp1) {
	if (isp1) {
	  return player1Damage;
	} else {
	  return player2Damage;
	}
  }
  
  public void addDamage(boolean isp1,double d) {
	if (isp1) {
	  player1Damage = player1Damage + d;
	} else {
	  player2Damage = player2Damage + d;
	}
  }
  
  public double getMaxDamage(boolean isp1) {
	if (isp1) {
	  return player1MaxDamage;
	} else {
	  return player2MaxDamage;
	}
  }
  
  public void setMaxDamage(boolean isp1,double d) {
	if (isp1) {
	  player1MaxDamage = d;
	} else {
	  player2MaxDamage = d;
	}
  }
  
  public int getExp(boolean isp1) {
	if (isp1) {
	  return player1Exp;
	} else {
	  return player2Exp;
	}
  }
  
  public void addExp(boolean isp1,int i) {
	if (isp1) {
	  player1Exp = player1Exp + i;
	} else {
	  player2Exp = player2Exp + i;
	}
  }
  
  public int getHit(boolean isp1) {
	if (isp1) {
	  return player1Mutilhit;
	} else {
	  return player2Mutilhit;
	}
  }
  
  public void addHit(boolean isp1) {
	if (isp1) {
	  player1Mutilhit = player1Mutilhit + 1;
	  player2Mutilhit = 0;
	} else {
	  player2Mutilhit = player2Mutilhit + 1;
	  player1Mutilhit = 0;
	}
  }
  
  public void setLocation(Player p1,Player p2) {
	if (p1 == null || p2 == null) {
	  return;
	}
    player1Location = p1.getLocation();
	player2Location = p2.getLocation();
  }
  
  public Location getLoaction(boolean isp1) {
	if (isp1) {
	  return player1Location;
	} else {
	  return player2Location;
	}
  }
  
  protected Dan getDan(boolean isp1) {
	if (isp1) {
	  return player1Dan;
	} else {
	  return player2Dan;
	}
  }
  
  protected void setDan(boolean isp1,Dan dan) {
	if (isp1) {
	  player1Dan = dan;
	} else {
	  player2Dan = dan;
	}
  }
  
  public boolean canTeleport() {
	return canTeleport;
  }
  
  protected void setCanTeleport(boolean canTeleport) {
	this.canTeleport = canTeleport;
  }
  
  public boolean getWatchersTeleport() {
	return watchersTeleport;
  }
  
  protected void setWatchersTeleport(boolean watchersTeleport) {
	this.watchersTeleport = watchersTeleport;
  }
}

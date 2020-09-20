package com.valorin.arenas;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.arenas.ArenasManager.*;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.util.SyncBroadcast.bc;

import java.util.List;

import lk.vexview.api.VexViewAPI;
import lk.vexview.tag.TagDirection;
import lk.vexview.tag.components.VexImageTag;
import lk.vexview.tag.components.VexTextTag;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.api.event.ArenaEventAbs;
import com.valorin.api.event.arena.ArenaDrawFinishEvent;
import com.valorin.api.event.arena.ArenaFinishEvent;
import com.valorin.dan.CommonDan;
import com.valorin.dan.DansHandler;
import com.valorin.dan.type.Common;
import com.valorin.effect.WinFirework;
import com.valorin.energy.Energy;
import com.valorin.event.game.CompulsoryTeleport;
import com.valorin.task.RecordData;

public class Finisher {
  public static void normalEnd(String name,String winner,String loser,boolean isDraw) {//正常结束
      Arena arena = getInstance().getArenasHandler().getArena(name);
      if (arena == null) {return;}
	  if (!arena.getEnable()) {return;}
	
      Player w = Bukkit.getPlayerExact(winner);
      Player l = Bukkit.getPlayerExact(loser);
    
      if (w != null) {
          try {
			if (w.isDead()) { w.spigot().respawn(); }
		  } catch (Exception e) { CompulsoryTeleport.players.put(winner, arena.getLoaction(arena.isp1(winner))); }
      }
      if (l != null) {
          try {
			if (l.isDead()) { l.spigot().respawn(); }
		  } catch (Exception e) { CompulsoryTeleport.players.put(loser, arena.getLoaction(arena.isp1(loser))); }
      }
	  Energy e = getInstance().getEnergy();
	  if (e.getEnable()) {
	      e.setEnergy(winner, e.getEnergy(winner) - e.getNeed());
	      e.setEnergy(loser, e.getEnergy(loser) - e.getNeed());
	  }

	  arena.setCanTeleport(true);
	  arena.setWatchersTeleport(true);
	  if (getInstance().getConfig().getBoolean("Rewards.Firework")) {
	      WinFirework.setFirework(w.getLocation());
	      sm("&a[v]WOW！服务器为你的获胜放了一朵烟花~",w);
	  }

	  getInstance().getDuelAmountData().add();
	
      ArenaEventAbs event;
      if (isDraw) {
          event = new ArenaDrawFinishEvent(w, l, arena);
      } else {
          event = new ArenaFinishEvent(w, l, arena);
      }
      Bukkit.getServer().getPluginManager().callEvent(event);
	  // VexView 决斗结束TAG动画
	  String arenaName = arena.getName();
	  TagDirection td = new TagDirection(0, 0, 0, true, false);
	  TagDirection td2 = new TagDirection(0, 180, 0,false,false);
	  double x_pos = (getArenasPointA(arenaName).getX() + getArenasPointB(arenaName).getX()) / 2;
	  double y_pos = (getArenasPointA(arenaName).getY() + getArenasPointB(arenaName).getY()) / 2 + 5;
	  double z_pos = (getArenasPointA(arenaName).getZ() + getArenasPointB(arenaName).getZ()) / 2;
	  // DUEL 文本
	  VexTextTag duel_text = new VexTextTag(arenaName + "text", x_pos - 1, y_pos, z_pos, "DUEL", false, td);
	  VexViewAPI.addWorldTag(arena.getLoaction(true).getWorld(), duel_text);
	  // 左 用户 框
	  VexImageTag duel_left = new VexImageTag(arenaName + "left", x_pos - 1, y_pos - 1, z_pos, "[local]duel_r.png", 2625, 774, 4, 1, td2);
	  VexViewAPI.addWorldTag(arena.getLoaction(true).getWorld(), duel_left);
	  // 左 用户 ID
	  VexTextTag duel_left_uid = new VexTextTag(arenaName + "left_uid", x_pos - 3, y_pos - 1.5, z_pos - 0.2, Bukkit.getPlayerExact(winner).getName(), false, td);
	  VexViewAPI.addWorldTag(arena.getLoaction(true).getWorld(), duel_left_uid);
	  // 中 VS 分界
	  VexImageTag duel_vs = new VexImageTag(arenaName + "vs", x_pos, y_pos - 1, z_pos - 0.01, "[local]VS2.0.png", 585, 396, 2, 1, td2);
	  VexViewAPI.addWorldTag(arena.getLoaction(true).getWorld(), duel_vs);
	  // 右 用户 框
	  VexImageTag duel_right = new VexImageTag(arenaName + "right", x_pos + 3, y_pos - 1, z_pos, "[local]duel_l.png", 2625, 774, 4, 1, td2);
	  VexViewAPI.addWorldTag(arena.getLoaction(true).getWorld(), duel_right);
	  // 右 用户 ID
	  VexTextTag duel_right_uid = new VexTextTag(arenaName + "right_uid", x_pos + 1, y_pos - 1.5, z_pos - 0.2, Bukkit.getPlayerExact(loser).getName(), false, td);
	  VexViewAPI.addWorldTag(arena.getLoaction(true).getWorld(), duel_right_uid);
	  // 计时 后 删除
	  new BukkitRunnable(){
		  public void run() {
			  VexViewAPI.removeWorldTag(getArenasPointA(arenaName).getWorld(), arenaName+"text");
			  VexViewAPI.removeWorldTag(getArenasPointA(arenaName).getWorld(), arenaName+"left");
			  VexViewAPI.removeWorldTag(getArenasPointA(arenaName).getWorld(), arenaName+"left_uid");
			  VexViewAPI.removeWorldTag(getArenasPointA(arenaName).getWorld(), arenaName+"vs");
			  VexViewAPI.removeWorldTag(getArenasPointA(arenaName).getWorld(), arenaName+"right");
			  VexViewAPI.removeWorldTag(getArenasPointA(arenaName).getWorld(), arenaName+"right_uid");
		  }
	  }.runTaskLaterAsynchronously(getInstance(),100);
      new BukkitRunnable() {
          public void run() {
        	  try {
	              RecordData.record(arena, w, l, isDraw);
        	  } catch (Exception e) { e.printStackTrace(); }
	
	          DansHandler dh = getInstance().getDansHandler();
	          dh.refreshPlayerDan(winner);
	          dh.refreshPlayerDan(loser);
	          if (arena.getDan(arena.isp1(winner)) instanceof Common) {
	              List<CommonDan> danList = dh.getDanList();
	              for (int i = 0;i < danList.size();i++) {
	                  if (danList.get(i).getDanName().equals(arena.getDan(arena.isp1(winner)).getDanName())) {
	                      int danNumBefore = arena.getDan(arena.isp1(winner)).getNum();
	                      int danNumNow = dh.getPlayerDan(winner).getNum();
	                      if (danNumNow > danNumBefore) {
	                          bc(gm("&a[恭喜]: &7玩家 &e{player} &7的决斗段位成功升到了&r{dan}",null,"player dan",new String[]{winner,dh.getPlayerDan(winner).getDanName()}));
	                      }
	                      break;
	                  }
	              }
	          } else {
	               if (dh.getPlayerDan(winner) instanceof Common) {
	                   bc(gm("&a[恭喜]: &7玩家 &e{player} &7突破了无段位的身份，首次获得了段位：&r{dan}&7！祝TA在决斗战斗的路上越走越远！",null,"player dan",new String[]{winner,dh.getPlayerDan(winner).getDanName()}));
	               }
	          }
	          if (arena.getDan(arena.isp1(loser)) instanceof Common) {
	              List<CommonDan> danList = dh.getDanList();
	              for (int i = 0;i < danList.size();i++) {
	                  if (danList.get(i).getDanName().equals(arena.getDan(arena.isp1(winner)).getDanName())) {
	                      int danNumBefore = arena.getDan(arena.isp1(loser)).getNum();
	                      int danNumNow = dh.getPlayerDan(loser).getNum();
	                      if (danNumNow > danNumBefore) {
		                      bc(gm("&a[恭喜]: &7玩家 &e{player} &7的决斗段位成功升到了&r{dan}",null,"player dan",new String[]{winner,dh.getPlayerDan(winner).getDanName()}));
	                      }
	                      break;
		              }
	              }
	          } else {
	              if (dh.getPlayerDan(loser) instanceof Common) {
		               bc(gm("&a[恭喜]: &7玩家 &e{player} &7突破了无段位的身份，首次获得了段位：&r{dan}&7！祝TA在决斗战斗的路上越走越远！",null,"player dan",new String[]{loser,dh.getPlayerDan(loser).getDanName()}));
	              }
	          }
              arena.finish();
			  getInstance().getArenasHandler().removeArena(arena.getName());
          }
      }.runTaskAsynchronously(getInstance());

  }
  
  public static void compulsoryEnd(String name,Player finisher) {//强制结束，不予记录
	Arena arena = getInstance().getArenasHandler().getArena(name);
	if (arena == null) { 
		sm("&c[x]不存在的竞技场，请检查输入",finisher); return;}
	if (!arena.getEnable()) { 
		sm("&c[x]这个竞技场还没有决斗呢！",finisher); return;}
	
	Player p1 = Bukkit.getPlayerExact(arena.getp1());
	Player p2 = Bukkit.getPlayerExact(arena.getp2());
	
	arena.setWatchersTeleport(true);
	List<String> watchers = arena.getWatchers();
	
	arena.finish();
	sm("&b&l决斗被管理员强制结束！本局决斗不会被记录！",p1,p2);
	sm("&a[v]已强制停止",finisher);
	busyArenasName.remove(arena.getName());
  }
}

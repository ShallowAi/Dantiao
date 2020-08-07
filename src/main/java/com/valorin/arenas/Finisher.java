package com.valorin.arenas;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.arenas.ArenasManager.busyArenasName;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.util.SyncBroadcast.bc;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import com.valorin.teleport.ToLobby;
import com.valorin.teleport.ToLogLocation;

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
	
	  w.setHealth(w.getMaxHealth());
	  w.setFoodLevel(20);
	  if (l != null && !l.isDead()) {
          l.setHealth(l.getMaxHealth());
          l.setFoodLevel(20);
	  }
	  Energy e = getInstance().getEnergy();
	  if (e.getEnable()) {
	      e.setEnergy(winner, e.getEnergy(winner) - e.getNeed());
	      e.setEnergy(loser, e.getEnergy(loser) - e.getNeed());
	  }

	  arena.setCanTeleport(true);
	  arena.setWatchersTeleport(true);
	  List<String> watchers = arena.getWatchers();
	  if (areas.getString("Dantiao-LobbyPoint.World") != null) {
          ToLobby.to(w);
	      ToLobby.to(l);
	      sm("&b已将你带回单挑大厅！",w);
	      if (l != null) {
	          sm("&b已将你带回单挑大厅！",l);
	      }
	      for (String watcher : watchers) {
		      if (Bukkit.getPlayerExact(watcher) != null) {
		          ToLobby.to(Bukkit.getPlayerExact(watcher));
		          sm("&b已将你带回单挑大厅！",l);
		      }
	      }
	  } else {
	      Location winnerLocation = arena.getLoaction(arena.isp1(w.getName()));
	      Location loserLocation = arena.getLoaction(arena.isp1(l.getName()));
	      ToLogLocation.to(w,l,winnerLocation,loserLocation);
	      for (String watcher : watchers) {
		      if (Bukkit.getPlayerExact(watcher) != null) {
		          sm("&b[报告] &7你所观战的竞技场上的比赛已结束，请自行传送回家...",Bukkit.getPlayerExact(watcher),false);
		      }
	      }
	  }//回到原处
	  if (getInstance().getConfig().getBoolean("Rewards.Firework")) {
	      WinFirework.setFirework(w.getLocation());
	      sm("&a[v]WOW！服务器专门为你的获胜放了一朵烟花~",w);
	  }

	  busyArenasName.remove(arena.getName());
	  getInstance().getDuelAmountData().add();
	
      ArenaEventAbs event;
      if (isDraw) {
          event = new ArenaDrawFinishEvent(w, l, arena);
      } else {
          event = new ArenaFinishEvent(w, l, arena);
      }
      Bukkit.getServer().getPluginManager().callEvent(event);
	
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
	                          bc(gm("&a[恭喜]: &7玩家 &e{player} &7的单挑段位成功升到了&r{dan}",null,"player dan",new String[]{winner,dh.getPlayerDan(winner).getDanName()}));
	                      }
	                      break;
	                  }
	              }
	          } else {
	               if (dh.getPlayerDan(winner) instanceof Common) {
	                   bc(gm("&a[恭喜]: &7玩家 &e{player} &7突破了无段位的身份，首次获得了段位：&r{dan}&7！祝TA在单挑战斗的路上越走越远！",null,"player dan",new String[]{winner,dh.getPlayerDan(winner).getDanName()}));
	               }
	          }
	          if (arena.getDan(arena.isp1(loser)) instanceof Common) {
	              List<CommonDan> danList = dh.getDanList();
	              for (int i = 0;i < danList.size();i++) {
	                  if (danList.get(i).getDanName().equals(arena.getDan(arena.isp1(winner)).getDanName())) {
	                      int danNumBefore = arena.getDan(arena.isp1(loser)).getNum();
	                      int danNumNow = dh.getPlayerDan(loser).getNum();
	                      if (danNumNow > danNumBefore) {
		                      bc(gm("&a[恭喜]: &7玩家 &e{player} &7的单挑段位成功升到了&r{dan}",null,"player dan",new String[]{winner,dh.getPlayerDan(winner).getDanName()}));
	                      }
	                      break;
		              }
	              }
	          } else {
	              if (dh.getPlayerDan(loser) instanceof Common) {
		               bc(gm("&a[恭喜]: &7玩家 &e{player} &7突破了无段位的身份，首次获得了段位：&r{dan}&7！祝TA在单挑战斗的路上越走越远！",null,"player dan",new String[]{loser,dh.getPlayerDan(loser).getDanName()}));
	              }
	          }
              arena.finish();
          }
      }.runTaskAsynchronously(getInstance());
  }
  
  public static void compulsoryEnd(String name,Player finisher) {//强制结束，不予记录
	Arena arena = getInstance().getArenasHandler().getArena(name);
	if (arena == null) { 
		sm("&c[x]不存在的竞技场，请检查输入",finisher); return;}
	if (!arena.getEnable()) { 
		sm("&c[x]这个竞技场还没有比赛呢！",finisher); return;}
	
	Player p1 = Bukkit.getPlayerExact(arena.getp1());
	Player p2 = Bukkit.getPlayerExact(arena.getp2());
	
	arena.setWatchersTeleport(true);
	List<String> watchers = arena.getWatchers();
	if (areas.getString("Dantiao-LobbyPoint.World") != null) {
	  if (p1 != null) {
        ToLobby.to(p1);
	    sm("&b已将你带回单挑大厅！",p1);
	  }
	  if (p2 != null) {
		ToLobby.to(p2);
	    sm("&b已将你带回单挑大厅！",p2);
	  }
	  for (String watcher : watchers) {
		if (Bukkit.getPlayerExact(watcher) != null) {
		  ToLobby.to(Bukkit.getPlayerExact(watcher));
		  sm("&b已将你带回单挑大厅！",p2);
		}
	  }
	} else {
	  Location winnerLocation = arena.getLoaction(arena.isp1(p1.getName()));
	  Location loserLocation = arena.getLoaction(arena.isp1(p2.getName()));
	  ToLogLocation.to(p1,p2,winnerLocation,loserLocation);
	  for (String watcher : watchers) {
		if (Bukkit.getPlayerExact(watcher) != null) {
		  sm("&b[报告] &7你所观战的竞技场上的比赛已结束，请自行传送回家...",Bukkit.getPlayerExact(watcher));
		}
	  }
	}//回到原处
	
	arena.finish();
	sm("&b&l比赛被管理员强制结束！本局比赛不会被记录！",p1,p2);
	sm("&a[v]已强制停止",finisher);
	busyArenasName.remove(arena.getName());
  }
}

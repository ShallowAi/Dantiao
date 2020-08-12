package com.valorin.task;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.ranking;
import static com.valorin.configuration.DataFile.records;
import static com.valorin.configuration.DataFile.saveRecords;
import static com.valorin.configuration.DataFile.savepd;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.configuration.languagefile.MessageSender.sml;
import static com.valorin.dan.ExpChange.changeExp;
import static com.valorin.util.SyncBroadcast.bc;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.valorin.Dantiao;
import com.valorin.arenas.Arena;
import com.valorin.dan.CommonDan;
import com.valorin.dan.DansHandler;
import com.valorin.ranking.Ranking;


public class RecordData {
    public static void record(Arena arena,Player w,Player l,boolean isDraw) {
	    int time = arena.getTime();
	    String winner = w.getName();
	    String loser = l.getName();
	
	    double player1Damage = arena.getDamage(true);
	    double player1MaxDamage = arena.getMaxDamage(true);
	    double player2Damage = arena.getDamage(false);
	    double player2MaxDamage = arena.getMaxDamage(false);
	
	    double winnerDamage,winnerMaxDamage,loserDamage,loserMaxDamage;
	    if (arena.isp1(winner)) {
	       winnerDamage = player1Damage;
	       winnerMaxDamage = player1MaxDamage;
	       loserDamage = player2Damage;
	       loserMaxDamage = player2MaxDamage;
	    } else {
	       winnerDamage = player2Damage;
	       winnerMaxDamage = player2MaxDamage;
	       loserDamage = player1Damage;
	       loserMaxDamage = player1MaxDamage;
	    }
	
	    Calendar cal = Calendar.getInstance(); 
	    SimpleDateFormat formatter =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    String mDateTime = formatter.format(cal.getTime());
	
	    int loserWin = records.getInt(loser + ".Win");
	    int loserLose = records.getInt(loser + ".Lose");
	    int loserDraw = records.getInt(loser + ".Draw");
	    int loserGameTimes = loserWin + loserLose + loserDraw; 
	    records.set(loser +".Record" + "." + loserGameTimes + ".player", winner);
	    records.set(loser +".Record" + "." + loserGameTimes + ".time", time);
	    records.set(loser +".Record" + "." + loserGameTimes + ".date", mDateTime);
	    records.set(loser +".Record" + "." + loserGameTimes + ".damage", loserDamage);
	    records.set(loser +".Record" + "." + loserGameTimes + ".maxdamage", loserMaxDamage);
	    records.set(loser +".Record" + "." + loserGameTimes + ".isWin", false);
	    if (isDraw) {
	        records.set(loser +".Record" + "." + loserGameTimes + ".isDraw", true);
	    } else {
	        records.set(loser +".Record" + "." + loserGameTimes + ".isDraw", false);
	    }

	    int winnerWin = records.getInt(winner + ".Win");
	    int winnerLose = records.getInt(winner + ".Lose");
	    int winnerDraw = records.getInt(winner + ".Draw");
	    int winnerGameTimes = winnerWin + winnerLose + winnerDraw;
	    records.set(winner +".Record" + "." + winnerGameTimes + ".player", loser);
	    records.set(winner +".Record" + "." + winnerGameTimes + ".time", time);
	    records.set(winner +".Record" + "." + winnerGameTimes + ".date", mDateTime);
	    records.set(winner +".Record" + "." + winnerGameTimes + ".damage", winnerDamage);
	    records.set(winner +".Record" + "." + winnerGameTimes + ".maxdamage", winnerMaxDamage);
	    if (isDraw) {
	        records.set(winner +".Record" + "." + winnerGameTimes + ".isWin", false);
	        records.set(winner +".Record" + "." + winnerGameTimes + ".isDraw", true);
	    } else {
	        records.set(winner +".Record" + "." + winnerGameTimes + ".isWin", true);
	        records.set(winner +".Record" + "." + winnerGameTimes + ".isDraw", false);
	        double reward = getInstance().getConfig().getDouble("Rewards.Points");
	        pd.set(winner+".Points", pd.getDouble(winner+".Points") + reward);
	        savepd();
	        sm("&b做的不错！奖励你 &d{points} &b点单挑积分",w,"points",new String[]{""+reward});
	    }
    
        if (isDraw) {
  	        records.set(winner +".Draw", winnerDraw + 1);
  	        records.set(loser +".Draw", loserDraw + 1);
  	    } else {
  	        records.set(winner +".Win", winnerWin + 1);
  	        records.set(loser +".Lose", loserLose + 1);
  	    }
    
        saveRecords();
        getInstance().getDansHandler().load();
    
        if (isDraw) { return; }
        
        Ranking r = getInstance().getRanking();
        int winnerRank = r.getWin(w);
	    int loserRank = r.getWin(l);
	    int winnerRank2 = r.getKD(w);
	    int loserRank2 = r.getKD(l);
	  
	    int winnerOrder = 0;
	    if (ranking.getStringList("Win") != null) {
		    for (int i = 0;i < ranking.getStringList("Win").size();i++) {
	            if (ranking.getStringList("Win").get(i).split("\\|")[0].equals(winner)) {
	                winnerOrder = i;
	            }
	        }
	    }
	    int loserOrder = 0;
	    if (ranking.getStringList("Win") != null) {
		    for (int i = 0;i < ranking.getStringList("Win").size();i++) {
	            if (ranking.getStringList("Win").get(i).split("\\|")[0].equals(loser)) {
	    	        loserOrder = i;
	            }
		    }
	    }
	  
	    if (winnerOrder <= loserOrder) {//winner本来就强过loser 等号则代表没有排行数据，winner优先
	        r.rank(winner+"|"+records.getInt(winner+".Win"),true);
	        r.rank(loser+"|"+records.getInt(loser+".Win"),true);
	    } else {
		    r.rank(loser+"|"+records.getInt(loser+".Win"),true);
	        r.rank(winner+"|"+records.getInt(winner+".Win"),true);
	    }
	  
	    int winnerOrder2 = 0;
	    if (ranking.getStringList("KD") != null) {
		    for (int i = 0;i < ranking.getStringList("KD").size();i++) {
	            if (ranking.getStringList("KD").get(i).split("\\|")[0].equals(winner)) {
	                winnerOrder2 = i;
	            }
		    }
	    }
	    int loserOrder2 = 0;
	    if (ranking.getStringList("KD") != null) {
	        for (int i = 0;i < ranking.getStringList("KD").size();i++) {
	            if (ranking.getStringList("KD").get(i).split("\\|")[0].equals(loser)) {
	                loserOrder2 = i;
	            }
		    }
	    }
	    if (winnerOrder2 <= loserOrder2) {//winner本来就强过loser 等号则代表没有排行数据，winner优先
	        if (records.getInt(winner+".Lose") != 0) {
	            r.rank(winner+"|"+
	            ((double)records.getInt(winner+".Win")/
			     (double)records.getInt(winner+".Lose")),false);
	        } else {
	            r.rank(winner+"|"+
		        ((double)records.getInt(winner+".Win")),false);
	        }
	  
	        if (records.getInt(loser+".Lose") != 0) {
	            r.rank(loser+"|"+
	            ((double)records.getInt(loser+".Win")/
			     (double)records.getInt(loser+".Lose")),false);
	        } else {
	            r.rank(loser+"|"+
	            ((double)records.getInt(loser+".Win")),false);
	        }
	   } else {
		   if (records.getInt(loser+".Lose") != 0) {
			   r.rank(loser+"|"+
		       ((double)records.getInt(loser+".Win")/
			    (double)records.getInt(loser+".Lose")),false);
		   } else {
			   r.rank(loser+"|"+
		       ((double)records.getInt(loser+".Win")),false);
		   }
		   if (records.getInt(winner+".Lose") != 0) {
			   r.rank(winner+"|"+
		       ((double)records.getInt(winner+".Win")/
				(double)records.getInt(winner+".Lose")),false);
		   } else {
			   r.rank(winner+"|"+
			   ((double)records.getInt(winner+".Win")),false);
		   }
	  }
	  
	  if (winnerRank != r.getWin(w) && r.getWin(w) != 0) {
		  sm("&b胜场排名发生变更！&e{before}->{now}",w,"before now",
		    new String[]{
		    ""+winnerRank,
		    ""+r.getWin(w)});
	  }
	  if (loserRank != r.getWin(l) && r.getWin(l) != 0) {
		  sm("&b胜场排名发生变更！&e{before}->{now}",l,"before now",
		    new String[]{
		    ""+loserRank,
		    ""+r.getWin(l)});
	  }
	  if (winnerRank2 != r.getKD(w) && r.getWin(w) != 0) {
		  sm("&bKD排名发生变更！&e{before}->{now}",w,"before now",
		    new String[]{
		    ""+winnerRank2,
		    ""+r.getKD(w)});
	  }
	  if (loserRank2 != r.getKD(l) && r.getWin(l) != 0) {
		  sm("&bKD排名发生变更！&e{before}->{now}",l,"before now",
		    new String[]{
		    ""+loserRank2,
		    ""+r.getKD(l)});
	  }
	  
	  if (!isDraw) {//正常
		  boolean b1 = arena.isp1(winner);
		  int winnerExp;
		  int loserExp;
		  int maxExp = getInstance().getConfig().getInt("Rewards.MaxExp");
		  if (arena.getExp(b1) > maxExp) {
		      winnerExp = maxExp;
		  } else {
		      winnerExp = arena.getExp(b1);
		  }
	      winnerExp = winnerExp + getInstance().getConfig().getInt("Rewards.WinExp");
		  loserExp = (int)winnerExp/3;
			
		  int firstLevelExp;
		  DansHandler dh = getInstance().getDansHandler();
		  List<CommonDan> danList = dh.getDanList();
		  boolean isUseDefault = false;
		  if (danList != null) {
			  if (danList.size() == 0) {
				  isUseDefault = true;
			  }
		  } else {
			  isUseDefault = true;
		  }
		  if (isUseDefault) {
			  firstLevelExp = 50;
		  } else {
			  firstLevelExp = danList.get(0).getExp();
		  }
	      
		  boolean protection;
		  int protectionExp = getInstance().getConfig().getInt("ProtectionExp");
		  int winnerExpNow = pd.getInt(winner+".Exp");
	      int loserExpNow = pd.getInt(loser+".Exp");
		  if (protectionExp == 0) { //有保护措施
			  protection = false;
		  } else {
			  if (winnerExpNow - loserExpNow >= protectionExp ||
				  loserExpNow - winnerExpNow >= protectionExp) {
				  protection = true;
			  } else {
				  protection = false;
			  }
		  }

		  int loserExpShow;
		  int winnerExpShow;
		  if (protection) {
			  loserExpShow = 0;
			  winnerExpShow = 0;
			  sm("&c双方段位差距过大，段位经验不会变更",w,l);
		  } else {
			  changeExp(w,pd.getInt(winner+".Exp")+winnerExp);
			  winnerExpShow = winnerExp;
		      loserExpShow = loserExp;
		      if (loserExpNow - loserExp > firstLevelExp) {
			      changeExp(l,loserExpNow-loserExp); //正常扣除经验
		      } else {
			      if (loserExpNow > firstLevelExp) {
				      loserExpShow = loserExpNow - firstLevelExp;
				      changeExp(l,firstLevelExp); //设置为保底经验
		          } else {
			          loserExpShow = 0; //不扣经验
		          }
		      }
		  }
		  
		    sml("&7============================================================| |                    &b决斗结束！|          &7恭喜获得了胜利，期待你下一次更加精彩得表现！|          &7同时获得了 &a{exp} &7经验| |&7============================================================"
			,w,"exp",new String[]{""+winnerExpShow});
		    if (l != null) {
		      sml("&7============================================================| |                    &b决斗结束！|          &7你没有获胜，不要灰心，再接再厉！|          &7同时损失了 &c{exp} &7经验| |&7============================================================"
			  ,l,"exp",new String[]{""+loserExpShow});
		    }
		    String arenaName = areas.getString("Arenas."+arena.getName()+".Name");
			if (arenaName == null) {
			  arenaName = "";
			}
		    bc(gm("&b[战报]: &7玩家 &e{winner} &7在单挑赛场&r{arenaname}&r&7上以 &6{time}秒 &7击败玩家 &e{loser}",null,"winner arenaname time loser",new String[]{winner,arenaName,arena.getTime()+"",loser}));
	  } else { //平局
		  int drawExp = getInstance().getConfig().getInt("Rewards.DrawExp");
		  changeExp(w,pd.getInt(winner+".Exp")+drawExp);
		  changeExp(l,pd.getInt(loser+".Exp")+drawExp);
		  sml("&7============================================================| |                    &b决斗结束！|          &7决斗超时！未决出胜负，判定为平局！|          &7同时获得了 &a{exp} &7经验| |&7============================================================"
			,w,"exp",new String[]{""+drawExp});
		  if (l != null) {
			  sml("&7============================================================| |                    &b决斗结束！|          &7决斗超时！未决出胜负，判定为平局！|          &7同时获得了 &a{exp} &7经验| |&7============================================================"
			  ,l,"exp",new String[]{""+drawExp});
		  }
		  new BukkitRunnable() {
			  public void run() {
			      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "/dt hd refresh");
			  }
		  }.runTask(Dantiao.getInstance());
		  String arenaDisplayName = areas.getString("Arenas."+arena.getName()+".Name");
		  if (arenaDisplayName == null) {
			  arenaDisplayName = "";
		  }
		  bc(gm("&b[战报]: &7玩家 &e{p1} &7与 &e{p2} &7在单挑赛场&r{arenaname}&r&7上打成平手，实为精妙！",null,"p1 arenaname p2",new String[]{winner,arenaDisplayName,loser}));
	  }
	  
	  int winTime = pd.getInt(winner+".Winning-Streak-Times");
	  int maxWinTime = pd.getInt(winner+".Max-Winning-Streak-Times");
	  pd.set(winner+".Winning-Streak-Times",winTime + 1);
	  if (winTime + 1 > maxWinTime) {
		  pd.set(winner+".Max-Winning-Streak-Times",winTime + 1);
	  }
	  pd.set(loser+".Winning-Streak-Times",0);
	  FileConfiguration config = Dantiao.getInstance().getConfig();
	  int reportTimes = config.getInt("Broadcast-Winning-Streak-Times");
	  if (reportTimes == 0) {
		  reportTimes = 3;
	  }
	  if (winTime + 1 >= reportTimes) {
		  bc(gm("&a[恭喜]: &7玩家 &e{player} &7在竞技场上完成了 &b{times} &7连胜！",null,"player times",new String[]{winner, (winTime + 1)+""}));
	  }
	  savepd();
  }
}

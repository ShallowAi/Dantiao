package com.valorin.event.game;

import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;

import com.valorin.Dantiao;
import com.valorin.arenas.Arena;
import com.valorin.arenas.ArenasManager;

public class Protection implements Listener {
	@EventHandler(priority=EventPriority.HIGHEST)
	public void protection(EntityDamageByEntityEvent e) { //玩家：常规保护
	  if (e.getEntity() instanceof Player && e.getDamager() instanceof Player) { //判断互打双方都是玩家
		Player p = (Player)e.getEntity(); //确定受击者
		String pn = p.getName(); 
		ArenasManager ah = Dantiao.getInstance().getArenasHandler();
		if (!ah.isPlayerBusy(pn)) { //如果这个受击者不是正在比赛的玩家，那就看看攻击者是不是比赛中的选手
		  if (ah.isPlayerBusy(((Player)e.getDamager()).getName())) { //是比赛选手，设置禁止伤害场外玩家
			e.setCancelled(true);
		  }
		} else {
		  Arena arena = ah.getArena(ah.getPlayerOfArena(pn)); //获取竞技场
		  String theOther = arena.getTheOtherPlayer(pn); //获取受击者的对手
		  Player attacker = (Player)e.getDamager();
		
		  if (attacker.getName().equals(theOther)) { //判定：攻击者就是受击者的对手
		    if (arena.getStage() == 0) {
			  sm("&7战斗未开始...",attacker);
			  e.setCancelled(true);
		    }
		  } else { //攻击者不是自己的对手，是别人的对手
		    sm("&c[x]请勿干扰他人比赛！",attacker);
		    e.setCancelled(true);
		  }
		}
	  }
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void protection2(EntityDamageByEntityEvent e) { //所有实体类型：弹射物保护
		if (!(e.getEntity() instanceof Player)) { //受击者是玩家
	      return;
		}
		Player bearerPlayer = (Player) e.getEntity(); //确认受击者
		String bearer = bearerPlayer.getName();
		
		ProjectileSource shooter = null;
		boolean isShoot = false;
		if (e.getDamager() instanceof Arrow) {
		  isShoot = true;
		  if (((Arrow)e.getDamager()).getShooter() instanceof Player)
		  shooter = ((Arrow)e.getDamager()).getShooter();
		} else if (e.getDamager() instanceof FishHook) {
	      isShoot = true;
		  if (((FishHook)e.getDamager()).getShooter() instanceof Player)
		  shooter = ((FishHook)e.getDamager()).getShooter();
		} else if (e.getDamager() instanceof Snowball) {
		  isShoot = true;
		  if (((Snowball)e.getDamager()).getShooter() instanceof Player)
		  shooter = ((Snowball)e.getDamager()).getShooter();
		} else if (e.getDamager() instanceof Fireball) {
		  isShoot = true;
		  if (((Fireball)e.getDamager()).getShooter() instanceof Player)
		  shooter = ((Fireball)e.getDamager()).getShooter();
		}
		if (!isShoot) { //不是弹射物伤害，就跟这个事件无关了
		  return;
		}
		ArenasManager ah = Dantiao.getInstance().getArenasHandler();
		if (!ah.isPlayerBusy(bearer)) { //受击者是比赛场上的选手
		  if (shooter != null) { //人为发射，只判定这个
			Player shooterPlayer = (Player)shooter;
			String shooterPlayerName = shooterPlayer.getName();
			if (ah.isPlayerBusy(shooterPlayerName)) {
			  e.setCancelled(true);
			}
		  }
		} else {
		  if (shooter == null) { //发射者是非玩家实体，比如说怪物，则取消事件
		    e.setCancelled(true);
		  } else {//说明人为发射
		    Arena arena = ah.getArena(ah.getPlayerOfArena(bearer));
		    String theOther = arena.getTheOtherPlayer(bearer);
		  
		    Player shooterPlayer = (Player)shooter;
		    if (shooterPlayer.getName().equals(theOther)) { 
			  if (arena.getStage() == 0) {
			    sm("&7战斗未开始...",shooterPlayer);
			    e.setCancelled(true);
			  }
		    } else { //如果发射者不是自己的对手，那取消事件
			  if (!shooterPlayer.getName().equals(bearer)) { //误伤自己也算
			    sm("&c[x]请勿干扰他人比赛！",shooterPlayer);
			    e.setCancelled(true);
			  }
		    }
		  }
		}
	}
	
	@EventHandler(priority=EventPriority.HIGHEST)
	public void protection3(EntityDamageByEntityEvent e) //非玩家实体：常规保护
	{
	  if (!(e.getEntity() instanceof Player)) { //受击者若不是玩家，return
		return;
	  }
	  if (e.getDamager() instanceof Player) { //攻击者是玩家，return
		return;
	  }
	  boolean legal = true;
	  if (e.getDamager() instanceof Arrow) {
		legal = false;
	  } else if (e.getDamager() instanceof FishHook) {
		legal = false;
	  } else if (e.getDamager() instanceof Snowball) {
		legal = false;
	  } else if (e.getDamager() instanceof Fireball) {
		legal = false;
	  }
	  if (!legal) {
		return;
	  }
	  Player p = (Player)e.getEntity();  //确认受击者
	  String pn = p.getName();
	  ArenasManager ah = Dantiao.getInstance().getArenasHandler();
	  if (ah.isPlayerBusy(pn)) { //受击者为比赛选手玩家
		e.setCancelled(true);
	  }
	}
}

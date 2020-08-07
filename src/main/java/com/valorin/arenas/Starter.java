package com.valorin.arenas;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.arenas.ArenasManager.arenas;
import static com.valorin.arenas.ArenasManager.busyArenasName;
import static com.valorin.arenas.ArenasManager.getArenasName;
import static com.valorin.arenas.ArenasManager.getArenasPointA;
import static com.valorin.arenas.ArenasManager.getArenasPointB;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.valorin.api.event.arena.ArenaStartEvent;
import com.valorin.request.RequestsHandler;
import com.valorin.util.ItemChecker;
import com.valorin.dan.DansHandler;

public class Starter {
  
  public Arena getRandomArena() {
	if (getArenasName().size() == 0) {
      return null;
	}
	List<String> freeArenasName = getArenasName();
	freeArenasName.removeAll(busyArenasName);
    if (freeArenasName.size() == 0) {
      return null;
    }
	Random random = new Random();
	return getInstance().getArenasHandler().getArena(freeArenasName.get(random.nextInt(freeArenasName.size())));
  }
  
  /*
   * p1：选手1号
   * p2：选手2号
   * arenaName：指定竞技场，null则为随机
   * starter：强制开启比赛的管理员，null则为正常情况的开赛
   */
  public Starter(Player p1,Player p2,String arenaName,Player starter) {
	if (p1 == null || p2 == null) {//玩家遁地了？！
	  sm("&c[x]警告：开赛时发生异常，不予开赛！",starter);
	  return;
	}
	if (getArenasName().size() == 0) {//居然不设置竞技场？！
	  sm("&c服务器内没有设置任何竞技场！请联系管理员！",p1,p2);
	  return;
	}
	if (getInstance().getConfig().getBoolean("WorldLimit.Enable")) {
	  List<String> worldlist = getInstance().getConfig().getStringList("WorldLimit.Worlds");
	  if (worldlist != null) {
	    if (!worldlist.contains(p1.getWorld().getName())) {
	      sm("&c[x]你所在的世界已被禁止比赛，请移动到允许比赛的世界再开赛",p1);
	      sm("&c[x]对手{player}所在的世界已被禁止比赛，请等待TA移动到允许比赛的世界再开赛",p2,"player",new String[]{p1.getName()});
	      return;
	    }
	    if (!worldlist.contains(p2.getWorld().getName())) {
	      sm("&c[x]你所在的世界已被禁止比赛，请移动到允许比赛的世界再开赛",p2);
		  sm("&c[x]对手{player}所在的世界已被禁止比赛，请等待TA移动到允许比赛的世界再开赛",p1,"player",new String[]{p2.getName()});
	      return;
	    }
	  }
	}
	
	List<String> MaterialNameList = getInstance().getConfig().getStringList("ItemLimit.Material");
	if (MaterialNameList.size() != 0) {
	  for (String materialName : MaterialNameList) {
		Material material = Material.getMaterial(materialName);
		if (material != null) {
		  ItemChecker ic1 = new ItemChecker(p1, material);
		  if (ic1.isHasItem()) {
			sm("&c[x]你的背包里携带有违禁品！不予开赛",p1);
			sm("&c[x]对手{player}的背包里携带有违禁品！不予开赛",p2,"player",new String[]{p1.getName()});
			p1.closeInventory();p2.closeInventory();
			return;
		  }
		  ItemChecker ic2 = new ItemChecker(p2, material);
		  if (ic2.isHasItem()) {
			sm("&c[x]你的背包里携带有违禁品！不予开赛",p2);
			sm("&c[x]对手{player}的背包里携带有违禁品！不予开赛",p1,"player",new String[]{p2.getName()});
			p1.closeInventory();p2.closeInventory();
			return;
		  }
		}
	  }
	}
	
	List<String> loreList = getInstance().getConfig().getStringList("ItemLimit.Lore");
	if (loreList.size() != 0) {
	  for (String lore : loreList) {
		ItemChecker ic1 = new ItemChecker(p1, lore);
		if (ic1.isHasItem()) {
	      sm("&c[x]你的背包里携带有违禁品！不予开赛",p1);
		  sm("&c[x]对手{player}的背包里携带有违禁品！不予开赛",p2);
		  p1.closeInventory();p2.closeInventory();
		  return;
		}
		ItemChecker ic2 = new ItemChecker(p2, lore);
		if (ic2.isHasItem()) {
		  sm("&c[x]你的背包里携带有违禁品！不予开赛",p2);
		  sm("&c[x]对手{player}的背包里携带有违禁品！不予开赛",p1);
		  p1.closeInventory();p2.closeInventory();
		  return;
		}
	  }
	}
	
	if (arenaName == null) { //非指定竞技场，即随机
	  if (busyArenasName.size() == arenas.size()) {//竞技场都满了
	    p1.closeInventory();
	    p2.closeInventory();
	    sm("&c所有竞技场都满了！请稍后再试~&e(小提示：输入/dt ainfo可以查看所有竞技场的实时信息)",p1,p2);
	    return;
	  }
	} else {
	  if (!arenas.contains(getInstance().getArenasHandler().getArena(arenaName))) {
	    sm("&c不存在这个竞技场，请检查输入",starter);
	    return;
	  }
	  if (busyArenasName.contains(arenaName)) {//竞技场满了
		sm("&c这个竞技场正在比赛中！请换一个试试",starter);
		return;
	  }
	}
	Arena arena = null;
	if (arenaName == null) { arena = getRandomArena(); }//随机获取一个竞技场
	else { arena = getInstance().getArenasHandler().getArena(arenaName); }//获取OP强制开始时指定的竞技场
	if (arena == null) { 
	  sm("&c[x]警告：开赛时发生异常，不予开赛！",p1,p2); 
	  p1.closeInventory();p2.closeInventory();
	  return; 
	}
	String pn1 = p1.getName(), pn2 = p2.getName();
	
	ArenaStartEvent event = new ArenaStartEvent(p1, p2, arena);
	
	arenaName = arena.getName();
	
	arena.setLocation(p1, p2);
	
	DansHandler dh = getInstance().getDansHandler();
	arena.setDan(true, dh.getPlayerDan(pn1));
	arena.setDan(false, dh.getPlayerDan(pn2));
	
	p1.teleport(getArenasPointA(arenaName));
	p2.teleport(getArenasPointB(arenaName));
	if (p1.isFlying()) {
	  if (!p1.isOp()) {
	    p1.setFlying(false); 
	  }
	}
	if (p2.isFlying()) {
	  if (!p2.isOp()) {
		p2.setFlying(false); 
	  }
	}
	p1.setHealth(p1.getMaxHealth());
	p2.setHealth(p2.getMaxHealth());
	p1.setFoodLevel(20);
	p2.setFoodLevel(20);
	p1.setGameMode(GameMode.SURVIVAL);
	p2.setGameMode(GameMode.SURVIVAL);
	
	arena.start(pn1, pn2);
	busyArenasName.add(arenaName);
	
	String arenaDisplayName = areas.getString("Arenas."+arena.getName()+".Name");
	if (arenaDisplayName == null) {
	  arenaDisplayName = "";
	}
	sm("&b您已进入竞技场&r{arenaname}",p1,"arenaname",new String[]{arenaDisplayName});
	sm("&b您已进入竞技场&r{arenaname}",p2,"arenaname",new String[]{arenaDisplayName});
	
	RequestsHandler rh = getInstance().getRequestsHandler();
	rh.clearRequests(pn1,0,pn2);
	rh.clearRequests(pn2,0,pn1);
	
	Bukkit.getServer().getPluginManager().callEvent(event);
	
	if (starter != null) { sm("&a[v]已强制开始",starter); }
  }
}

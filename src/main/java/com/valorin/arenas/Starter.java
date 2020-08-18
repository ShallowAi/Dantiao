package com.valorin.arenas;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.arenas.ArenasManager.*;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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
	 * arenaName：指定竞技场，null则为随机 魔改后已无效
	 * starter：强制开启决斗的管理员，null则为正常情况的开赛
	 */
	public Starter(Player p1,Player p2,String arenaName,Player starter) {
		if (p1 == null || p2 == null) {//玩家遁地了？！
			sm("&c[x]警告：开赛时发生异常，找不到玩家",starter);
			return;
		}

		// 检查是否有人被禁赛
		if (getInstance().getConfig().getBoolean("WorldLimit.Enable")) {
			List<String> disworldlist = getInstance().getConfig().getStringList("WorldLimit.Worlds");
			if (disworldlist != null) {
				if (!disworldlist.contains(p1.getWorld().getName())) {
					sm("&c[x]你所在的世界已被禁止决斗，请移动到允许决斗的世界再开赛",p1);
					sm("&c[x]对手{player}所在的世界已被禁止决斗，请等待TA移动到允许决斗的世界再开赛",p2,"player",new String[]{p1.getName()});
					return;
				}
				if (!disworldlist.contains(p2.getWorld().getName())) {
					sm("&c[x]你所在的世界已被禁止决斗，请移动到允许决斗的世界再开赛",p2);
					sm("&c[x]对手{player}所在的世界已被禁止决斗，请等待TA移动到允许决斗的世界再开赛",p1,"player",new String[]{p2.getName()});
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

		// 检查是否有违禁物品
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

		Location pointA = p1.getLocation(),pointB = p2.getLocation();
		String pn1 = p1.getName(), pn2 = p2.getName();
		arenaName = pn1+"_"+pn2;

		areas.set("Arenas."+arenaName+".A.World", pointA.getWorld().getName());
		areas.set("Arenas."+arenaName+".A.X", pointA.getX());
		areas.set("Arenas."+arenaName+".A.Y", pointA.getY());
		areas.set("Arenas."+arenaName+".A.Z", pointA.getZ());
		areas.set("Arenas."+arenaName+".A.YAW", (float)pointA.getYaw());
		areas.set("Arenas."+arenaName+".A.PITCH", (float)pointA.getPitch());

		areas.set("Arenas."+arenaName+".B.World", pointB.getWorld().getName());
		areas.set("Arenas."+arenaName+".B.X", pointB.getX());
		areas.set("Arenas."+arenaName+".B.Y", pointB.getY());
		areas.set("Arenas."+arenaName+".B.Z", pointB.getZ());
		areas.set("Arenas."+arenaName+".B.YAW", (float)pointB.getYaw());
		areas.set("Arenas."+arenaName+".B.PITCH", (float)pointB.getPitch());
		areas.set("Arenas."+arenaName+".Name", arenaName);

		getInstance().getArenasHandler().addArena(arenaName);
		Arena arena = getInstance().getArenasHandler().getArena(arenaName);

		if (arena == null) {
			sm("&c[x]警告：开赛时发生异常，请联系管理员",p1,p2);
			p1.closeInventory();
			p2.closeInventory();
			return;
		}

		ArenaStartEvent event = new ArenaStartEvent(p1, p2, arena);

		arena.setLocation(p1, p2);

		DansHandler dh = getInstance().getDansHandler();
		arena.setDan(true, dh.getPlayerDan(pn1));
		arena.setDan(false, dh.getPlayerDan(pn2));

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

		arena.start(pn1, pn2);

		RequestsHandler rh = getInstance().getRequestsHandler();
		rh.clearRequests(pn1,0,pn2);
		rh.clearRequests(pn2,0,pn1);

		Bukkit.getServer().getPluginManager().callEvent(event);

		if (starter != null) { sm("&a[v]已强制开始决斗",starter); }
	}
}
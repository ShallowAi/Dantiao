package com.valorin.itemstack;

import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.records;
import static com.valorin.configuration.DataFile.shop;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.gml;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.valorin.inventory.special.StartInvHolder;
import com.valorin.specialtext.Dec;
import com.valorin.util.ItemCreator;

public class GUIItems {
  public static ItemStack getGlass(int n) {
	return new ItemCreator(Material.STAINED_GLASS_PANE, " ", null,n,false).get();
  }
  
  public static ItemStack getPage(String pn, int page, int maxpage) {//records和shop通用
	Player p = Bukkit.getPlayerExact(pn);
	return new ItemCreator(Material.EMPTY_MAP, 
                           gm("&f当前页码：{page}/{maxpage}",p,"page maxpage",new String[]{""+page,""+maxpage}),
                           gml(" |&9<左键下一页/右键上一页| ",p)
                           ,0,true).get();
  }
  
  public static ItemStack getStart(String pn) {
	Player p = Bukkit.getPlayerExact(pn);
	String displayname = 
			gm("&f<[ 全服匹配 &f]>",p);
	List<String> lore = new ArrayList<String>();
	lore.addAll(
			gml(" |&e在线寻找对手|&f&l>> &a点击开始",p));
	return new ItemCreator(Material.GOLD_AXE, displayname, lore, 0,false).get();
  }
  
  public static ItemStack updataStart(StartInvHolder holder) {
	String opener_name = holder.getOpenerName();
	Player opener = Bukkit.getPlayerExact(opener_name);
	String displayname = 
			gm("&f<[ &7全服匹配 &f]>",opener);
	List<String> lore = new ArrayList<String>();
	int second = holder.getSec();
	lore.addAll(
			gml(" |&7在线寻找对手|&f&l>> &6搜寻中..{second}s",opener,"second",new String[]{second+""}));

	return new ItemCreator(Material.GOLD_AXE, displayname, lore, 0, true).get();
  }
  
  public static ItemStack getRecords(String pn,int num) {
	Player p = Bukkit.getPlayerExact(pn);
	String displayname = 
			gm("&a作战编号 &2#{num}",p,"num",new String[]{""+num});
	List<String> lore = new ArrayList<String>();
	
	String pre = pn+".Record."+num+".";
	
	String result;
	if (records.getBoolean(pre+"isWin")) {
	  result = "&a[v]胜利";
	} else {
	  result = "&c[x]败北";
	}
	if (records.getBoolean(pre+"isDraw")) {
	  result = "&6=平局";
	}
	
	String damage;
	if (records.getDouble(pre+"damage") != 0) {
	  BigDecimal bg = new BigDecimal(records.getDouble(pre+"damage"));
	  damage = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
	} else {
	  damage = 
			  gm("&7&m未记录&r",p);
	}
	
	String maxdamage;
	if (records.getDouble(pre+"maxdamage") != 0) {
	  BigDecimal bg = new BigDecimal(records.getDouble(pre+"maxdamage"));
	  maxdamage = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue()+"";
	} else {
	  maxdamage = 
			  gm("&7&m未记录&r",p);
	}
	
	String result_string = gm(result,p);
	try {
	lore =
         gml(" |&b结果 &f[right] {result}|&b对手 &f[right] &e{opponent}|&b日期 &f[right] &e{date}|&b耗时  &f[right] &e{time}|&b伤害输出  &f[right] &e{damage}|&b最大输出  &f[right] &e{maxdamage}| ",p,
        	 "result opponent date time damage maxdamage",
        	 new String[]{ result_string,
        	 records.getString(pre+"player"),
        	 records.getString(pre+"date"),
        	 records.getInt(pre+"time")+"",
        	 damage,
        	 maxdamage,
             });
	lore.add("");
	lore.add(gm("&f[right] &b左键&7将本战绩分享到聊天框"));
	lore.add(gm("&f[right] &d右键&7将本战绩打印到纸上"));
	} catch (Exception e) {}
	
	return new ItemCreator(Material.PAPER, displayname, lore,0,false).get();
  }
  
  public static ItemStack getGood(String pn,int num) {
	Player p = Bukkit.getPlayerExact(pn);
	
	String pre = "n"+num+".";
	ItemStack good = shop.getItemStack(pre+"Item");
	double price = shop.getDouble(pre+"Price");
	
	List<String> lore = new ArrayList<String>();
	String dec = Dec.getStr(5);
	lore.add(dec);
	lore.add(
			gm("&b商品Lore &f&l>>",p));
	boolean haslore = true;
	if (good.hasItemMeta()) {
	  if (good.getItemMeta().getLore() != null) {
		lore.addAll(good.getItemMeta().getLore());
	  } else {
		haslore = false;
	  }
	} else {
	  haslore = false;
	}
	if (!haslore) {
	  lore.add(
			  gm("&7无",p));
	}
	lore.add(dec);
	lore.add(
			gm("&b商品信息 &f&l>>",p));
	if (shop.getString(pre+"Description") != null) {
	  lore.add(
			  gm("&6备注： ",p)+shop.getString(pre+"Description")
			  .replace("&", "§")
			  .replace("{player}", pn)
			  .replace("_", " "));
	} else {
	  lore.add(
			  gm("&6备注： &7无",p));
	}
	lore.add(
			gm("&6价格： &e{price}",p,"price",new String[]{""+price}));
	double points = pd.getDouble(pn+".Points");
	lore.add(
			gm("&6余额： &e{points}",p,"points",new String[]{""+points}));
	lore.add(dec);
	lore.add("");
	
	if (points >= price) {
	  lore.add(
			  gm("&a[v]点击购买",p));
	} else {
	  lore.add(
			  gm("&c[x]余额不足",p));
	}
	
	ItemStack item = new ItemStack(good);
	ItemMeta im = item.getItemMeta();
	im.setLore(lore);
	item.setItemMeta(im);
	
	return item;
  }
}

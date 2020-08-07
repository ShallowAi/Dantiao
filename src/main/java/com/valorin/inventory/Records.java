package com.valorin.inventory;

import static com.valorin.configuration.DataFile.records;
import static com.valorin.configuration.languagefile.MessageSender.gm;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.valorin.itemstack.GUIItems;

public class Records{
  public static HashMap<String, Integer> pages = new HashMap<String, Integer>();
  public static HashMap<String, Integer> maxPages = new HashMap<String, Integer>();
  
  public static void openInv(String openerName) {
	Player opener = Bukkit.getPlayerExact(openerName);
	Inventory inv = Bukkit.createInventory(null, 54, 
			gm("&0&l我的比赛记录 &9&l[right]",opener));
	
	int[] glass = {0,1,2,3,4,5,6,7,8,45,46,47,48,50,51,52,53};
    for (int i : glass) {
      inv.setItem(i, GUIItems.getGlass(0));
    }
	
    int page;
    if (!pages.containsKey(openerName)) {
      page = 1;
      pages.put(openerName, 1);
    } else {
      page = pages.get(openerName);
    }
    int win = records.getInt(openerName + ".Win");
	int draw = records.getInt(openerName + ".Draw");
	int lose = records.getInt(openerName + ".Lose");
    int times = win + lose + draw;
    if (times%36 != 0) {
      maxPages.put(openerName, times/36+1);
    } else {
      maxPages.put(openerName, times/36);
    }
    if (times == 0) {
      maxPages.put(openerName, 1);
    }
	
	int maxpage = maxPages.get(openerName);
    
    inv.setItem(49, GUIItems.getPage(openerName, page, maxpage));
    
	loadItem(inv,openerName,page);
	opener.openInventory(inv);
  }
  
  public static void loadPageItem(Inventory inv,String pn,int page) {
	inv.setItem(49, GUIItems.getPage(pn, page, maxPages.get(pn)));
  }
  
  public static void loadItem(Inventory inv,String openerName,int page) {
	int win = records.getInt(openerName + ".Win");
	int draw = records.getInt(openerName + ".Draw");
	int lose = records.getInt(openerName + ".Lose");
    int times = win + lose + draw; //总场数
		
	int n;
	if (times-((page-1)*36) > 36) {
	  n = 36;
	} else {
	  n = times-((page-1)*36);
	}
	for (int i = 0;i < n;i++) {
	  inv.setItem(i+9, GUIItems.getRecords(openerName, (page-1)*36+i));
	}
	for (int i = n;i < 36;i++) {
	  inv.setItem(i+9, new ItemStack(Material.AIR));
	}
  }
}

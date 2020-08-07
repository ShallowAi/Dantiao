package com.valorin.event.gui;

import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.inventory.Records.pages;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.valorin.inventory.Records;
import com.valorin.specialtext.Show;

public class RecordsGUI implements Listener {
	private Map<String, Long> show = new HashMap<String, Long>();
	
	@EventHandler
	public void page(InventoryClickEvent e) {
	  Player p = (Player) e.getWhoClicked();
	  String pn = p.getName();
	  Inventory inv = e.getInventory();
	  if (inv == null) {
		return;
	  }
	  if (e.getView().getTitle().equals(
			  gm("&0&l我的比赛记录 &9&l[right]",p)))
	  {
		e.setCancelled(true);
		if (e.getRawSlot() == 49) {//翻页
		  int page = Records.pages.get(pn);
		  int maxpage = Records.maxPages.get(pn);
		  if (e.getClick().equals(ClickType.LEFT)) {
		    if (page != maxpage) {
			  Records.loadItem(inv, pn, page+1);
			  pages.put(pn, page+1);
		      Records.loadPageItem(inv, pn, page+1);
		    }
		  }
		  if (e.getClick().equals(ClickType.RIGHT)) {
		    if (page != 1) {
			  Records.loadItem(inv, pn, page-1);
			  pages.put(pn, page-1);
		      Records.loadPageItem(inv, pn, page-1);
		    }
		  }
		  return;
		}
		if (e.getRawSlot() > 8 && e.getRawSlot() < 45) {
		  if (inv.getItem(e.getRawSlot()) != null) {
			if (e.getClick().equals(ClickType.LEFT)) {
			  if (show.containsKey(pn)) {
				if (System.currentTimeMillis() - show.get(pn) < 15000) {
				  sm("&a战绩展示太频繁啦~请等会再展示",p);
				  return;
				}
			  }
			  show.put(pn, System.currentTimeMillis());
			  new Show().showRecord(p, inv.getItem(e.getRawSlot()),null);
			}
			if (e.getClick().equals(ClickType.RIGHT)) {
			  ItemStack item = p.getItemInHand();
			  if (item != null) {
				if (item.getType().equals(Material.PAPER)) {
				  if (item.getAmount() == 1) {
				    if (!item.hasItemMeta()) {
					  ItemStack recordItem = inv.getItem(e.getRawSlot());
				      ItemStack printedPaper = new ItemStack(Material.PAPER);
				      ItemMeta printedPaperMeta = printedPaper.getItemMeta();
				      printedPaperMeta.setDisplayName(gm("&2{player}：",p,"player",new String[]{pn})+recordItem.getItemMeta().getDisplayName());
				      List<String> lore = recordItem.getItemMeta().getLore();
				      lore.remove(lore.size() - 1);lore.remove(lore.size() - 1);lore.remove(lore.size() - 1);
				      printedPaperMeta.setLore(lore);
				      printedPaper.setItemMeta(printedPaperMeta);
				      p.setItemInHand(printedPaper);
				      sm("&a战绩打印成功",p);
				      return;
				    }
				  }
				}
			  }
			  sm("&a请将单张纸拿在手上才能打印战绩！",p);
			}
		  }
		}
	  }
	}
}

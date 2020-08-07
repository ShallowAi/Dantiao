package com.valorin.event.gui;

import static com.valorin.configuration.DataFile.pd;
import static com.valorin.configuration.DataFile.savepd;
import static com.valorin.configuration.DataFile.shop;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.configuration.languagefile.MessageSender.sml;
import static com.valorin.inventory.Shop.goodList;
import static com.valorin.inventory.Shop.pages;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.valorin.api.event.ShopEvent;
import com.valorin.commands.sub.ShopCMD;
import com.valorin.inventory.Shop;
import com.valorin.util.ItemGiver;

public class ShopGUI implements Listener {
	@EventHandler
	public void page(InventoryClickEvent e) {
	  Player p = (Player) e.getWhoClicked();
	  String pn = p.getName();
	  Inventory inv = e.getInventory();
	  if (inv == null) {
		return;
	  }
	  if (e.getView().getTitle().equals(
			  gm("&0&l积分商城 &9&l[right]",p)))
	  {
		e.setCancelled(true);
		if (e.getRawSlot() == 49) { 
		  int page = Shop.pages.get(pn);
		  int maxpage = Shop.getMaxPage();
		  if (e.getClick().equals(ClickType.LEFT)) {
		    if (page != maxpage) {
			  Shop.loadItem(inv, pn, page+1);
			  pages.put(pn, page+1);
			  Shop.loadPageItem(inv, pn, page+1);
		    }
		  }
		  if (e.getClick().equals(ClickType.RIGHT)) {
		    if (page != 1) {
		      Shop.loadItem(inv, pn, page-1);
		      pages.put(pn, page-1);
		      Shop.loadPageItem(inv, pn, page-1);
		    }
		  }
		}
		if (e.getRawSlot() > 8 && e.getRawSlot() < 45) //买东西
		{
		  if (inv.getItem(e.getRawSlot()) == null) {
			return;
		  }
		  
		  int page = Shop.pages.get(pn);
		  int row;
		  if ((e.getRawSlot()+1)%9 == 0) {
			row = ((e.getRawSlot()+1) - 9)/9;
		  } else {
			row = ((e.getRawSlot()+1) - 9)/9 + 1;
		  }
		  int column;
		  if ((e.getRawSlot()+1)%9 == 0) {
			column = 9;
		  } else {
			column = (e.getRawSlot()-9) - (row-1)*9 + 1;
		  }
		  int num = ShopCMD.getNum(page, row, column);
		  
		  String n = new ArrayList<String>(goodList).get(num);
		  double price = shop.getDouble(n+".Price");
		  double points = pd.getDouble(p.getName()+".Points");
		  if (points < price) {
			sm("&c[x]积分余额不足！该商品需要&e{price}&c积分，而你只有&e{points}&c积分",p,"price points",new String[]{""+price,""+points});
		    return;
		  }
		  
		  ShopEvent event = new ShopEvent(p, page, row, column);
		  Bukkit.getServer().getPluginManager().callEvent(event);
		  if (event.isCancelled()) { //购买事件被取消
			return;
		  }

		  ItemStack item = shop.getItemStack(n+".Item");
		  ItemGiver ig = new ItemGiver(p,item);
		  if (ig.getIsReceive()) {
			pd.set(p.getName()+".Points", points - price);
			savepd();
			sml("&7========================================|&a[v]恭喜购买成功，现在你获得了这个道具|&7========================================",p);
			if (shop.getString(n+".Broadcast") != null)
			{
		      Bukkit.broadcastMessage(shop.getString(n+".Broadcast").replace("&", "§").replace("_", " ").replace("{player}", p.getName()));
			}
			Shop.loadInv(pn, inv);
		  }
		}
	  }
	}
}

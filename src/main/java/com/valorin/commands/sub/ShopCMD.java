package com.valorin.commands.sub;

import static com.valorin.configuration.DataFile.saveShop;
import static com.valorin.configuration.DataFile.shop;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;
import static com.valorin.inventory.Shop.goodList;
import static com.valorin.inventory.Shop.reloadList;

import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import com.valorin.commands.SubCommand;
import com.valorin.commands.way.InServerCommands;
import com.valorin.inventory.Shop;

public class ShopCMD extends SubCommand implements InServerCommands{

	public ShopCMD() {
		super("shop","s");
	}
	
	public void sendHelp(Player p) {
	  sm("",p);
	  sm("&3&lDan&b&l&oTiao &f&l>> &a管理员帮助：商城操作",p,false);
	  sm("&b/dt shop(s) add <价格> &f- &a上架手中的物品作为商品",p,false);
	  sm("&b/dt shop(s) remove <页数> <行> <列> &f- &a下架某个商品",p,false);
	  sm("&b/dt shop(s) des <页数> <行> <列> <内容>&f- &a为已有商品添加备注，支持颜色代码",p,false);
	  sm("&b/dt shop(s) rdes <页数> <行> <列> &f- &a删除商品备注",p,false);
	  sm("&b/dt shop(s) bc <页数> <行> <列> <内容> &f- &a设置玩家购买成功后发送的全服公告，支持颜色代码，以{player}代表玩家名",p,false);
	  sm("&b/dt shop(s) rbc <页数> <行> <列> &f- &a删除购买成功后发送的全服公告",p,false);
	  sm("",p);
	}
	
	public Integer[] getLocation(int num) {
      int page;
      int row = 0;
      int column = 0;
	  if (num%36 != 0) {
	    page = num/36 + 1;
	  } else {
		page = num/36;
	  }
	  if ((num-(page-1))%9 != 0) {
		row = (num-(page-1))/9+1;
		column = (num - page*36 - row*9)+1;
	  } else {
		row = (num-(page-1))/9;
		column = 9;
	  }
	  return new Integer[]{page,row,column};
	}
	
	public static int getNum(int page,int row,int column) {
	  return (page - 1)*36+(row - 1)*9+(column - 1);
	}
	
	public static boolean isOutOfRange(int page,int row,int column,
			String pageArg,String rowArg,String columnArg,Player p) {
	  boolean result = false;
	  page = Integer.parseInt(pageArg);
	  if (page > Shop.getMaxPage() || page == 0) {
		sm("&c[x]页码超出值域，请检查是否存在此页",p);
		result = true;
	  }
	  row = Integer.parseInt(rowArg);
	  if (row > 4 || row == 0) {
		sm("&c[x]行数超出值域，请输入1~4",p);
		result = true;
	  }
	  column = Integer.parseInt(columnArg);
	  if (column > 9 || column == 0) {
		sm("&c[x]列数超出值域，请输入1~9",p);
		result = true;
	  }
	  return result;
	}
	
	public boolean isInt(String...nl)
	{
	  boolean result = true;
	  for (String n : nl) {
		Pattern pattern = Pattern.compile("[0-9]+");
		Matcher matcher = pattern.matcher((CharSequence) n);
		if (!matcher.matches()) {
		  result = false;
		}
	  }
	  return result;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{
	  Player p = null;
	  if (sender instanceof Player) { p = (Player)sender; }
	  if (args.length == 1) {
		Inventory inv = Bukkit.createInventory(null, 54, 
					gm("&0&l积分商城 &9&l[right]",p));
	    Shop.loadInv(p.getName(),inv);
	    sm("&a[v]欢迎光临决斗积分商城！",p);
	    return true;
	  }
	  if (!p.hasPermission("dt.admin")) {
    	sm("&c[x]无权限！",p);
    	return true;
      }
	  if (args[1].equalsIgnoreCase("help")) {
		sendHelp(p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("add")) {
		if (args.length != 3) {
		  sm("&7正确用法：/dt s add <价格>，注意请将要上架的物品拿在手上",p);
		  return true;
		}
	    ItemStack now = p.getInventory().getItemInMainHand();
	    if (now.equals(new ItemStack(Material.AIR))) {
	      sm("&c[x]请将要上架的物品拿在手中！",p);
	      return true;
	    }
	    if (!isInt(args[2])) {
	      sm("&c[x]请输入有效的阿拉伯数字！",p);
		  return true;
	    }
	    int price = Integer.parseInt(args[2]);
	    if (price <= 0) {
	      sm("&c[x]请输入大于零的阿拉伯数字！",p);
		  return true;
	    }
	    
	    int num = shop.getInt("Num");
	    shop.set("n"+num+".Item", p.getInventory().getItemInMainHand());
		shop.set("n"+num+".Price", price);
		shop.set("Num", num+1);
		reloadList();
		saveShop();
		sm("&a[v]商品上架成功！",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("remove")) {
		if (args.length != 5) {
	      sm("&7正确用法：/dt s remove <页数> <行> <列>，行和列不考虑GUI顶部和底部的玻璃板",p);
		  return true;
		}
		if (!isInt(args[2],args[3],args[4])) {
		  sm("&c[x]请输入有效的阿拉伯数字！",p);
		  return true;
		}
		int page = Integer.parseInt(args[2]);
		int row = Integer.parseInt(args[3]);
		int column = Integer.parseInt(args[4]);
		int num = getNum(page,row,column);
		if (num >= goodList.size()) {
		  sm("&c[x]不存在这个商品",p);
		  return true;
		}
		shop.set(new ArrayList<String>(goodList).get(num), null);
		saveShop();
		Set<String> s = shop.getKeys(false);
		s.remove("Num");
		goodList = s;
		sm("&a[v]商品下架完毕！",p);
		return true;
	  }
	  if (args[1].equalsIgnoreCase("des") || args[1].equalsIgnoreCase("bc")) {
		if (args.length != 6) {
		  if (args[1].equalsIgnoreCase("des")) {
	        sm("&7正确用法：/dt s des <页数> <行> <列> <内容>，行和列不考虑GUI顶部和底部的玻璃板，支持颜色代码",p);
		  } else {
			sm("&7正确用法：/dt s bc <页数> <行> <列> <内容>，行和列不考虑GUI顶部和底部的玻璃板，支持颜色代码，可以用{player}代替玩家名称",p);
		  }
		  return true;
		}
		if (!isInt(args[2],args[3],args[4])) {
		  sm("&c[x]请输入有效的阿拉伯数字！",p);
		  return true;
		}
		int page = Integer.parseInt(args[2]);
		int row = Integer.parseInt(args[3]);
		int column = Integer.parseInt(args[4]);
		if (isOutOfRange(page,row,column,args[2],args[3],args[4],p)) {
		  return true;
		}
		
		int num = getNum(page,row,column);
		if (num >= goodList.size()) {
		  sm("&c[x]不存在这个商品",p);
		  return true;
		}
		if (args[1].equalsIgnoreCase("des")) {
		  shop.set(new ArrayList<String>(goodList).get(num)+".Description", args[5]);
		  saveShop();
		  sm("&a[v]备注设置完毕！",p);
		} else {
		  shop.set(new ArrayList<String>(goodList).get(num)+".Broadcast", args[5]);
		  saveShop();
		  sm("&a[v]公告设置完毕！",p);
		}
		return true;
	  }
	  if (args[1].equalsIgnoreCase("rdes") || args[1].equalsIgnoreCase("rbc")) {
		if (args.length != 5) {
		  if (args[1].equalsIgnoreCase("rdes")) {
	        sm("&7正确用法：/dt s rdes <页数> <行> <列>，行和列不考虑GUI顶部和底部的玻璃板",p);
		  } else {
		    sm("&7正确用法：/dt s rbc <页数> <行> <列>，行和列不考虑GUI顶部和底部的玻璃板",p);
		  }
		  return true;
		}
		if (!isInt(args[2],args[3],args[4])) {
		  sm("&c[x]请输入有效的阿拉伯数字！",p);
		  return true;
		}
		int page = Integer.parseInt(args[2]);
		int row = Integer.parseInt(args[3]);
		int column = Integer.parseInt(args[4]);
		if (isOutOfRange(page,row,column,args[2],args[3],args[4],p)) {
		  return true;
		}
		
		int num = getNum(page,row,column);
		if (num >= goodList.size()) {
		  sm("&c[x]不存在这个商品",p);
		  return true;
		}
		if (args[1].equalsIgnoreCase("rdes")) {
		  shop.set(new ArrayList<String>(goodList).get(num)+".Description", null);
		  saveShop();
		  sm("&a[v]备注删除完毕！",p);
		} else {
	      shop.set(new ArrayList<String>(goodList).get(num)+".Broadcast", null);
		  saveShop();
		  sm("&a[v]公告删除完毕！",p);
		}
		return true;
	  }
	  sendHelp(p);
	  return true;
	}
}

package com.valorin.itemstack;

import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.gml;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.valorin.Dantiao;
import com.valorin.util.ItemCreator;

public class PlayerItems {
	public static String mark1 = "§4§1§1§4§2§0§9§1§1§5§1";//d(4) a(1) n(14) t(20) i(9) a(1) o(15) 编号(1)= 41142091151
    public static ItemStack getCreator(Player p) {
      Material material = Material.STICK;
      
      String customMaterialName = Dantiao.getInstance().getConfig().getString("Arena-Creator-Material");
      if (customMaterialName != null) {
    	if (Material.getMaterial(customMaterialName) != null) {
    	  material = Material.valueOf(customMaterialName);
    	}
      }
      
	  return new ItemCreator(
	  material,
	  gm("&7&l[&b单挑竞技场&7&l]&f&l-&e快捷创建工具&a[v]",p), 
	  gml("&6[right] &f&n左键&7点击空气设置&b&nA&7点|&6[right] &f&n右键&7点击空气设置&d&nB&7点| |&e保存-输入：|&e/dt a create <竞技场编辑名> <竞技场名称>",p),
	  mark1,true)
	  .get();
	}
    
    public static String mark2 = "§4§1§1§4§2§0§9§1§1§5§2";//d(4) a(1) n(14) t(20) i(9) a(1) o(15) 编号(2)= 41142091152
    public static ItemStack getInvitation(Player p) {
	  return new ItemCreator(
	  Material.PAPER, 
	  gm("&f&l[&b单挑&7-&b全服邀请函&f&l]",p), 
	  gml("&6[right] &7输入&f&n/dt sendall&7即可在聊天框里发送单挑请求|&6[right] &7每次消耗1个本道具| ",p),
	  mark2,true)
	  .get();
	}
}

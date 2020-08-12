package com.valorin.specialtext;

import static com.valorin.configuration.languagefile.MessageSender.gm;

import java.util.List;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;


public class Show {
	public void showRecord(Player player,ItemStack recordItem,Player shower) {
		
		TextComponent txt = new TextComponent();
		if (shower != null) {
			txt.setText(gm("&f{shower} &7: &b[分享：{player}的决斗战绩&7(鼠标移动到此处查看详情)&b]",null
					,"shower player",new String[]{shower.getName(), player.getName()}));
		} else {
			txt.setText(gm("&f{shower} &7: &b[分享：{player}的决斗战绩&7(鼠标移动到此处查看详情)&b]",null
					,"shower player",new String[]{player.getName(), player.getName()}));
		}
	    List<String> lore = recordItem.getItemMeta().getLore();
	    lore.remove(lore.size() - 1);lore.remove(lore.size() - 1);lore.remove(lore.size() - 1);
	    String n = gm("&2{player}：",player,"player",new String[]{player.getName()})+
	    		recordItem.getItemMeta().getDisplayName();
	    for (String s : lore)
	    {
	      n = n+"\n"+s;
	    }
	    n.substring(2);
	    txt.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(n).create()));
	    Bukkit.spigot().broadcast(txt);
	}
}

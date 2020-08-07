package com.valorin.specialtext;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import static com.valorin.configuration.languagefile.MessageSender.gm;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Click {
  
    public static void sendRequest(String sn,String rn) {
	    Player r = Bukkit.getPlayerExact(rn);
	
	    TextComponent txt4 = new TextComponent();
	    txt4.setText(
			gm("&c[x]&f&n点击拒绝&r",r));
	    txt4.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dt deny "+sn));
	
	    TextComponent txt3 = new TextComponent();
	    txt3.setText(Dec.getStr(3));
	    txt3.addExtra(txt4);
	
	    TextComponent txt2 = new TextComponent();
	    txt2.setText(
			gm("&a[v]&f&n点击接受&r",r));
	    txt2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dt accept "+sn));
	    txt2.addExtra(txt3);
	
	    TextComponent txt = new TextComponent();
	    txt.setText(Dec.getStr(1));
	    txt.addExtra(txt2);
	
	    r.spigot().sendMessage(txt);
    }
    public static TextComponent invitationToAll(String sn) {
	    TextComponent txt2 = new TextComponent();
	    txt2.setText(
			gm("&a[v]&f&n点击挑战他&r"));
	    txt2.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/dt accept "+sn));
	
	    TextComponent txt = new TextComponent();
	    txt.setText(Dec.getStr(6));
	    txt.addExtra(txt2);
	    
	    return txt;
    }
}

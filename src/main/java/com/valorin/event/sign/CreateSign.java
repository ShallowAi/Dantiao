package com.valorin.event.sign;

import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class CreateSign implements Listener{
	@EventHandler
	public void onCreateMatchingSign(SignChangeEvent e) {
	  String content = e.getLine(0);
	  if (content.equals(gm("[决斗匹配]",null))) {
	    e.setLine(0, "§9"+content);
	    sm("&a[v]快捷木牌创建成功！",e.getPlayer());
	  }
    }
}

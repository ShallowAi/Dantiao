package com.valorin.configuration.languagefile;

import static com.valorin.Dantiao.getInstance;
import static com.valorin.configuration.languagefile.MessageBuilder.gmLog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class MessageSender {
	
	//完整的
	public static void sm(String Chinese,Player p,String v,String[] vl,Boolean prefix) {
	  if (p == null) {
		Bukkit.getConsoleSender().sendMessage(gmLog(Chinese,null,v,vl,prefix,false));
	  } else {
	    p.sendMessage(gmLog(Chinese,p,v,vl,prefix,false));
	  }
	}
	//常用：无变量，默认有前缀
	public static void sm(String Chinese,Player p) {
	  if (p == null) {
		if (Chinese.length() == 0) {
		  Bukkit.getConsoleSender().sendMessage(gmLog(Chinese,null,null,null,false,false));
		} else {
		  Bukkit.getConsoleSender().sendMessage(gmLog(Chinese,null,null,null,true,false));
		}
	  } else {
		if (Chinese.length() == 0) {
	      p.sendMessage(gmLog(Chinese,p,null,null,false,false));
	    } else {
	      p.sendMessage(gmLog(Chinese,p,null,null,true,false));
	    }
	  }
	}
	//常用：无变量，默认无前缀
	public static void sm(String Chinese,Player p,boolean prefix) {
	  if (p == null) {
		Bukkit.getConsoleSender().sendMessage(gmLog(Chinese,null,null,null,false,false));
	  } else {
	    p.sendMessage(gmLog(Chinese,p,null,null,false,false));
	  }
	}
	//专用：无变量，默认有前缀，仅用于双向发送
	public static void sm(String Chinese,Player p1,Player p2) {
	  p1.sendMessage(gmLog(Chinese,p1,null,null,true,false));
	  p2.sendMessage(gmLog(Chinese,p2,null,null,true,false));
	}
	//常用：有变量，默认有前缀，无论prefix如何都返回有前缀
	public static void sm(String Chinese,Player p,String v,String[] vl) {
	  if (p == null) {
		Bukkit.getConsoleSender().sendMessage(gmLog(Chinese,null,v,vl,true,false));
	  } else {
		p.sendMessage(gmLog(Chinese,p,v,vl,true,false));
	  }
	}
	//不常用：发送多行消息，无变量，默认无前缀
	public static void sml(String Chinese,Player p) {
	  if (gmLog(Chinese,p,null,null,false,false) == null) {return;}
	  for (String str : gmLog(Chinese,p,null,null,false,false).split("\\|")) {
		p.sendMessage(str);
	  }
	}
	//不常用：发送多行消息，有变量，默认无前缀
	public static void sml(String Chinese,Player p,String v,String[] vl) {
	  if (gmLog(Chinese,p,null,null,false,false) == null) {return;}
	  for (String str : gmLog(Chinese,p,v,vl,false,false).split("\\|")) {
	    p.sendMessage(str);
	  }
	}
	
	//常用：无变量，默认无前缀
	public static String gm(String Chinese,Player p) {
	  return gmLog(Chinese,p,null,null,false,false);
	}
	//常用：有变量，默认无前缀
	public static String gm(String Chinese,Player p,String v,String[] vl) {
	  return gmLog(Chinese,p,v,vl,false,false);
	}
	
	//不常用：用于获取各个语言文件的Tag，antiClashTag项仅用于防止方法冲突，随便输，无任何其他作用
	public static String gm(String Chinese,File file,int antiClashTag) {
	  LanguageFileLoader lfl = getInstance().getLanguageFileLoader();
	  String finalmessage = "";
	  int loc = -1;
	  for (int i = 0;i < lfl.getDefaultLang().size();i++) {
		if (lfl.getDefaultLang().get(i).equals(Chinese)) {
		  loc = i;
		}
	  }
	  if (loc == -1) {
		return finalmessage;
	  }
	  List<String> flist = lfl.getLang().get(file);
	  if (flist.size() <= loc) {
		return finalmessage;
	  }
	  finalmessage = flist.get(loc);
	  return finalmessage;
	}
	
	//不常用：仅用于HD标题
	public static String gm(String Chinese) {
	  return gmLog(Chinese,null,null,null,false,true);
	}
	
	//常用：多行消息，常用于Lores，无变量，默认无前缀
	public static List<String> gml(String Chinese,Player p) {
	  if (gmLog(Chinese,p,null,null,false,false) == null) {return null;}
	  List<String> list = new ArrayList<String>();
	  for (String str : gmLog(Chinese,p,null,null,false,false).split("\\|")) {
		list.add(str);
	  }
	  return list;
	}
	
	//不常用：多行消息，常用于Lores，有变量，默认无前缀
	public static List<String> gml(String Chinese,Player p,String v,String[] vl) {
	  if (gmLog(Chinese,p,v,vl,false,false) == null) {return null;}
	  List<String> list = new ArrayList<String>();
	  for (String str : gmLog(Chinese,p,v,vl,false,false).split("\\|")) {
	    list.add(str);
	  }
	  return list;
	}
}

package com.valorin.timetable;

import static com.valorin.Dantiao.getInstance;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.bukkit.entity.Player;

public class TimeChecker {
  
  public static boolean isInTheTime(Player p,boolean isSearching) {
	List<String> type;
	if (isSearching) {
	  type = getInstance().getTimeTable().getSearching();
	} else {
	  type = getInstance().getTimeTable().getInvite();
	}
	boolean inTheTime = false;
	if (type.size() != 0) {
	  Timetable timetable = new Timetable();
	  List<String> list = new ArrayList<String>();
	  if (isSearching) { list = timetable.getSearching(); }
	  else { list = timetable.getInvite(); }
	  for (String s : list) { // 格式 13:00-15:00
	    try {
	      String[] s1 = s.split("\\-");
	      String start = s1[0];
	      String end = s1[1];
	      int startMinutes = Integer.parseInt(start.split("\\:")[0])*60 + Integer.parseInt(start.split("\\:")[1]);
	      int endMinutes = Integer.parseInt(end.split("\\:")[0])*60 + Integer.parseInt(end.split("\\:")[1]);
			     
	      if (startMinutes < endMinutes) {
	    	Calendar calendar = Calendar.getInstance();
	    	SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	    	String ss = formatter.format(calendar.getTime());
	       	int myMinutes = Integer.parseInt(ss.split("\\:")[0])*60 + Integer.parseInt(ss.split("\\:")[1]);
	    	if (myMinutes >= startMinutes && myMinutes <= endMinutes) {
	    	  inTheTime = true;
	    	}
	      }
	    } catch (java.lang.ArrayIndexOutOfBoundsException e) {} 
	      catch (java.lang.NumberFormatException e) {}
	  }
	}
	return inTheTime;
  }
}

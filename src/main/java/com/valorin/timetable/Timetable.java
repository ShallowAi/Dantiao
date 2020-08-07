package com.valorin.timetable;

import java.util.ArrayList;
import java.util.List;

import com.valorin.Dantiao;

public class Timetable {
  
  List<String> searching = new ArrayList<String>();
  List<String> invite = new ArrayList<String>();
  
  public List<String> getSearching() {
	return searching;
  }
  
  public List<String> getInvite() {
	return invite;
  }
  
  public void close() {
	searching = new ArrayList<String>();
	invite = new ArrayList<String>();
  }
  
  public Timetable()  {
   String[] types = {"Searching","Invite"};
   for (String type : types) {
	List<String> timeTable = new ArrayList<String>();
	boolean hasTimeTable = false;
	List<String> list = Dantiao.getInstance().getConfig().getStringList("Timetable."+type);
	if (list.size() == 0) {
	  return;
	}
	for (String s : list) { // 格式 13:00-15:00
	  try {
		String[] s1 = s.split("\\-");
		String start = s1[0];
		String end = s1[1];
		int startMinutes = Integer.parseInt(start.split("\\:")[0])*60 + Integer.parseInt(start.split("\\:")[1]);
		int endMinutes = Integer.parseInt(end.split("\\:")[0])*60 + Integer.parseInt(end.split("\\:")[1]);
		if (startMinutes < endMinutes) {
		  timeTable.add(s);
		  hasTimeTable = true;
		}
	  } catch (java.lang.ArrayIndexOutOfBoundsException e) {
	  } catch (java.lang.NumberFormatException e) { }
	}
	if (!hasTimeTable) {
	  continue;
	}
	if (type.equals("Searching")) { this.searching = timeTable; }
	if (type.equals("Invite")) { this.invite = timeTable; }
   }
  }
}

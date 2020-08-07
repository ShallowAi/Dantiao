package com.valorin.configuration.updata;

import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.saveAreas;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class UpdateAreas {
   public UpdateAreas(boolean isForcibly) {
	 if (FileVersionChecker.get() || isForcibly) {
	   Bukkit.getConsoleSender().sendMessage("§7Dantiao: areas.yml is updating now...(areas.yml文件开始跨版本更新...)");
	   List<String> arenaList = new ArrayList<String>(areas.getKeys(false));
	   if (arenaList.equals("Dantiao-HD-Win")) {
		 arenaList.remove(arenaList.indexOf("Dantiao-HD-Win"));
	   }
	   if (arenaList.equals("Dantiao-HD-KD")) {
		 arenaList.remove(arenaList.indexOf("Dantiao-HD-KD"));
	   }
	   
	   for (String arenaName : arenaList) {
		 areas.set("Arenas."+arenaName+".Name", areas.getString(arenaName+".Name"));
		 
		 areas.set("Arenas."+arenaName+".A.World", areas.getString(arenaName+".A.World"));
		 areas.set("Arenas."+arenaName+".A.X", areas.getDouble(arenaName+".A.X"));
		 areas.set("Arenas."+arenaName+".A.Y", areas.getDouble(arenaName+".A.Y"));
		 areas.set("Arenas."+arenaName+".A.Z", areas.getDouble(arenaName+".A.Z"));
		 areas.set("Arenas."+arenaName+".A.YAW", (float)areas.getDouble(arenaName+".A.YAW"));
		 areas.set("Arenas."+arenaName+".A.PITCH", (float)areas.getDouble(arenaName+".A.PITCH"));
		 areas.set("Arenas."+arenaName+".B.World", areas.getString(arenaName+".B.World"));
		 areas.set("Arenas."+arenaName+".B.X", areas.getDouble(arenaName+".B.X"));
		 areas.set("Arenas."+arenaName+".B.Y", areas.getDouble(arenaName+".B.Y"));
		 areas.set("Arenas."+arenaName+".B.Z", areas.getDouble(arenaName+".B.Z"));
		 areas.set("Arenas."+arenaName+".B.YAW", (float)areas.getDouble(arenaName+".B.YAW"));
		 areas.set("Arenas."+arenaName+".B.PITCH", (float)areas.getDouble(arenaName+".B.PITCH"));
	   }
	   
	   for (String arenaName : arenaList) {
		 areas.set(arenaName, null);
	   }
	   
	   saveAreas();
	   
	   Bukkit.getConsoleSender().sendMessage("§aDantiao: areas.yml updated successfully!(areas.yml文件更新成功！)");
	 }
   }
}

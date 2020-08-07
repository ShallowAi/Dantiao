package com.valorin.configuration.updata;

import static com.valorin.configuration.DataFile.saveShop;
import static com.valorin.configuration.DataFile.shop;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

public class UpdateShop {
   public UpdateShop(boolean isForcibly) {
	 if (FileVersionChecker.get() || isForcibly) {
	   Bukkit.getConsoleSender().sendMessage("§7Dantiao: shop.yml is updating now...(shop.yml文件开始跨版本更新...)");
	   List<String> goodNameList = new ArrayList<String>(shop.getKeys(false));
	   
	   for (String goodName : goodNameList)
	   {
		 shop.set("n"+goodNameList.lastIndexOf(goodName)+".Item", shop.getItemStack(goodName+".Item"));
		 shop.set("n"+goodNameList.lastIndexOf(goodName)+".Price", shop.getItemStack(goodName+".Price"));
	   }
	   
	   for (String goodName : goodNameList)
	   {
		 shop.set(goodName, null);
	   }
	   
	   shop.set("Num", goodNameList.size());
	   
	   saveShop();
	   
	   try {
		 File logLangfile = new File("plugins/Dantiao/Lang");
	     File[] logLanguageymls = logLangfile.listFiles();
	     for (File log_languageyml : logLanguageymls)
	     {
	       log_languageyml.delete();
	     }
	     logLangfile.delete();
	     
	     File logpdFile = new File("plugins/Dantiao/pd.yml");
		 File logPointsFile = new File("plugins/Dantiao/points.yml");
		 logpdFile.delete();
		 logPointsFile.delete();
	   } catch (Exception e) {
		 Bukkit.getConsoleSender().sendMessage("§aDantiao: Some Errors occurred.File 'Lang' needs to be deleted manually by Admin!"
		 		+ "If this file isn't exist before,just ignore it!");
	   }
	   
	   
	   Bukkit.getConsoleSender().sendMessage("§aDantiao: shop.yml updated successfully!(shop.yml文件更新成功！)");
	 }
   }
}

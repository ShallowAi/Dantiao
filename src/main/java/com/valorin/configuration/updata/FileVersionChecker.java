package com.valorin.configuration.updata;

import static com.valorin.configuration.DataFile.rankingFile;

import java.io.File;

public class FileVersionChecker {
  public static boolean get() {//以ranking.yml不存在，但pd.yml在为判断标志
	if (!new File("plugins/Dantiao").exists()) {
	  return false;
	}
	File pdFile = new File("plugins/Dantiao/pd.yml");
	if (rankingFile.length() == 0 && pdFile.exists()) {
	  return true;
	}
	return false;
  }
  
  public static void execute(boolean isForcibly) {
	new UpdateAreas(isForcibly);
	new UpdateConfig(isForcibly);
	new UpdatePlayerData(isForcibly);
	new UpdateShop(isForcibly);
  }
}

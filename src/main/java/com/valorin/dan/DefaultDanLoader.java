package com.valorin.dan;

import java.util.ArrayList;
import java.util.List;

public class DefaultDanLoader {
  private List<CommonDan> danList = new ArrayList<CommonDan>();
  
  public DefaultDanLoader() {
	danList.add(new CommonDan(0, "dan1", "&f[&2青铜I&f]&r", 50));
	danList.add(new CommonDan(1, "dan2", "&f[&2青铜II&f]&r", 150));
	danList.add(new CommonDan(2, "dan3", "&f[&2青铜III&f]&r", 280));
	danList.add(new CommonDan(3, "dan4", "&f[&3黑铁I&f]&r", 500));
	danList.add(new CommonDan(4, "dan5", "&f[&3黑铁II&f]&r", 760));
	danList.add(new CommonDan(5, "dan6", "&f[&3黑铁III&f]&r", 1200));
	danList.add(new CommonDan(6, "dan7", "&f[&e黄金I&f]&r", 1550));
	danList.add(new CommonDan(7, "dan8", "&f[&e黄金II&f]&r", 2100));
	danList.add(new CommonDan(8, "dan9", "&f[&e黄金III&f]&r", 3500));
	danList.add(new CommonDan(9, "dan10", "&f[&b钻石I&f]&r", 5000));
	danList.add(new CommonDan(10, "dan11", "&f[&b钻石II&f]&r", 6666));
	danList.add(new CommonDan(11, "dan12", "&f[&b钻石III&f]&r", 8888));
	danList.add(new CommonDan(12, "dan13", "&f[&6&l战斗天尊&f]&r", 10000));
	danList.add(new CommonDan(13, "dan14", "&f[&d&l荣耀王者&f]&r", 15000));
  }
  
  public List<CommonDan> get() {
	return danList;
  }
}

package com.valorin.dan;

import com.valorin.Dantiao;
import com.valorin.dan.type.Initial;

public class InitialDan extends Dan implements Initial {
  public InitialDan() {
	super(-1,
			null,
			Dantiao.getInstance().getConfig().getString("InitialDan"),0);
  }
}

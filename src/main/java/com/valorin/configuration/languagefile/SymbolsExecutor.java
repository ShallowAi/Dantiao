package com.valorin.configuration.languagefile;

import static com.valorin.Dantiao.getInstance;

public class SymbolsExecutor {
  public static String execute(String str) {
	String finalStr = str;
	SymbolLoader sl = getInstance().getSymbolLoader();
	for (String symbolString : sl.getSymbolsMark()) {
	  finalStr = finalStr.replace(symbolString, sl.getSymbols().get(symbolString));
	}
	return finalStr;
  }
}

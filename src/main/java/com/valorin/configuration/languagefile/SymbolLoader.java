package com.valorin.configuration.languagefile;

import static com.valorin.configuration.DataFile.symbolsFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.base.Charsets;
import com.valorin.Dantiao;
import com.valorin.configuration.DataFile;

public class SymbolLoader {
	public SymbolLoader() {
	  if (symbolsFile.exists()) {
		if (DataFile.symbols.getKeys(false).size() == 0) {
	      try {
	    	FileConfiguration defaultSymbol = new YamlConfiguration();
			defaultSymbol.load(new BufferedReader(new InputStreamReader(Dantiao.getInstance().getResource("symbols.yml"), Charsets.UTF_8)));
			for (String mark : defaultSymbol.getKeys(false))
			{
			  DataFile.symbols.set(mark, defaultSymbol.getString(mark));
			}
			DataFile.saveSymbols();
	      } catch (Exception e) {
	    	loadDefaultSymbols();
	      }
		} else {
	      loadSymbols();
		}
	  } else {
		loadDefaultSymbols();
	  }
	}
	private Map<String, String> symbols = new HashMap<String, String>();
	private List<String> symbolsMark = new ArrayList<String>();
	
	public List<String> getSymbolsMark() {
      return symbolsMark;
	}
	
	public Map<String, String> getSymbols() {
      return symbols;
	}
	
	public void loadSymbols() {
		for (String symbolString : DataFile.symbols.getKeys(false)) {
          String symbol = DataFile.symbols.getString(symbolString);
          symbols.put(symbolString, symbol);
          symbolsMark.add(symbolString);
        }
	}
	
	private void loadDefaultSymbols() {
	    FileConfiguration defaultSymbol = new YamlConfiguration();
        try {
			defaultSymbol.load(new BufferedReader(new InputStreamReader(Dantiao.getInstance().getResource("symbols.yml"), Charsets.UTF_8)));
		} catch (Exception e) {
			e.printStackTrace();
		}
        for (String symbolString : defaultSymbol.getKeys(false)) {
          String symbol = defaultSymbol.getString(symbolString);
          symbols.put(symbolString, symbol);
          symbolsMark.add(symbolString);
        }
	}
}

package com.valorin;

import lk.vexview.api.VexViewAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.valorin.arenas.ArenaCreatorsHandler;
import com.valorin.arenas.ArenasManager;
import com.valorin.commands.CommandsHandler;
import com.valorin.commands.sub.Reload;
import com.valorin.configuration.DataFile;
import com.valorin.configuration.PlayerSet;
import com.valorin.configuration.languagefile.LanguageFileLoader;
import com.valorin.configuration.languagefile.SymbolLoader;
import com.valorin.configuration.updata.FileVersionChecker;
import com.valorin.dan.DansHandler;
import com.valorin.data.CommandTypeAmountData;
import com.valorin.data.DuelAmountData;
import com.valorin.energy.Energy;
import com.valorin.event.EventRegister;
import com.valorin.inventory.Shop;
import com.valorin.metrics.Metrics;
import com.valorin.network.Update;
import com.valorin.papi.RegPAPI;
import com.valorin.queue.SearchingQueue;
import com.valorin.ranking.HD;
import com.valorin.ranking.Ranking;
import com.valorin.request.RequestsHandler;
import com.valorin.task.VersionChecker;
import com.valorin.timetable.Timetable;

public class Dantiao extends JavaPlugin {
	/**
	 * Dantiao Plugin
	 * 
	 * @author Valorin（创始、初代开发、更新、维护）&Jonjs（仅1.0初代开发）
	 * @date 2018/8（Version:1.0）2020/2（Version:2.0，源码全部重写）
	 */
	private static String prefix;
	private static String version;
    private static Dantiao instance;
    private CommandsHandler commandsHandler;
    private ArenasManager arenaHandler;
    private ArenaCreatorsHandler acsHandler;
    private Timetable timeTable;
    private RequestsHandler requestHandler;
    private Ranking ranking;
    private HD hd;
    private DansHandler dansHandler;
    private SymbolLoader symbolLoader;
    private Energy energy;
    private SearchingQueue searchingQueue;
    private LanguageFileLoader languageFileLoader;
    private DuelAmountData duelAmountData;
    private CommandTypeAmountData commandTypeAmountData;
    private PlayerSet playerSet;
    private Update update;
    private Metrics metrics;
    
    public static String getPrefix() {
        return prefix;
    }
    
    public static String getVersion() {
        return version;
    }
    
    public static void setPrefix(String newprefix) {
    	try {
        prefix = newprefix;
    	} catch (Exception e) { }
    }
    
    public static Dantiao getInstance() {
        return instance;
    }

    public CommandsHandler getCommandHandler() {
        return commandsHandler;
    }
    
    public ArenasManager getArenasHandler() {
        return arenaHandler;
    }
    
    public ArenaCreatorsHandler getACS() {
        return acsHandler;
    }
    
    public Timetable getTimeTable() {
        return timeTable;
    }
    
    public void reloadTimeTable() {
    	timeTable.close();
    	timeTable = new Timetable();
    }
    
    public RequestsHandler getRequestsHandler() {
        return requestHandler;
    }
    
    public Ranking getRanking() {
        return ranking;
    }
    
    public HD getHD() {
        return hd;
    }
    
    public DansHandler getDansHandler() {
        return dansHandler;
    }
    
    public SymbolLoader getSymbolLoader() {
        return symbolLoader;
    }
    
    public Energy getEnergy() {
        return energy;
    }
    
    public void reloadEnergy() {
        energy.close();
        energy = new Energy();
    }
    
    public SearchingQueue getSearchingQueue() {
        return searchingQueue;
    }
    
    public LanguageFileLoader getLanguageFileLoader() {
        return languageFileLoader;
    }
    
    public void reloadLanguageFileLoad() {
        languageFileLoader.close();
        languageFileLoader = new LanguageFileLoader();
    }
    
    public DuelAmountData getDuelAmountData() {
        return duelAmountData;
    }
    
    public CommandTypeAmountData getCommandTypeAmountData() {
        return commandTypeAmountData;
    }
    
    public PlayerSet getPlayerSet() {
        return playerSet;
    }
    
    public Update getUpdate() {
        return update;
    }
    
    public Metrics getMetrics() {
        return metrics;
    }
    
    @Override
    public void onEnable() {
    	instance = this;//一定要是第一个
    	
    	DataFile.loadData();
    	DataFile.saveAreas();
    	DataFile.saveBlackList();
    	DataFile.savepd();
    	DataFile.saveRanking();
    	DataFile.saveRecords();
    	DataFile.saveShop();
    	DataFile.saveSymbols();
    	
    	FileVersionChecker.execute(false);
    	
        Reload.reloadConfiguration();
        
        try {
          prefix = getConfig().getString("Message.Prefix").replace("&", "§");
        } catch (Exception e) {
          prefix = "§8§l[§bPVP§8§l] ";
        }
        version = getDescription().getVersion();
    	Shop.reloadList();
    	new SymbolLoader();
    	languageFileLoader = new LanguageFileLoader();
        symbolLoader = new SymbolLoader();
    	acsHandler = new ArenaCreatorsHandler();
        arenaHandler = new ArenasManager();
        timeTable = new Timetable();
        requestHandler = new RequestsHandler();
        ranking = new Ranking();
        hd = new HD();
        playerSet = new PlayerSet();
        dansHandler = new DansHandler();
        energy = new Energy();
        searchingQueue = new SearchingQueue();
        duelAmountData = new DuelAmountData();
        commandTypeAmountData = new CommandTypeAmountData();
        update = new Update();
        commandsHandler = new CommandsHandler("dt");
        
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        
        EventRegister.registerEvents();
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      	    RegPAPI.hook();
      	} else {
      		console.sendMessage("§8[§bDantiao§8]");
      		console.sendMessage("§f>> §c未发现PlaceholderAPI变量插件，将无法使用PAPI的相关功能，若您刚安装，请尝试重启服务器");
      		console.sendMessage("§f>> §cPlugin PlaceholderAPI is not found!");
      	}
        if (Bukkit.getPluginManager().isPluginEnabled("VexView")) {
            console.sendMessage("§8[§bDantiao§8]");
            console.sendMessage("发现 VexView 前置, 版本"+ VexViewAPI.getVexView().getVersion());
        } else {
            console.sendMessage("§8[§bDantiao§8]");
            console.sendMessage("§f>> §c未发现VexView插件，将无法使用VexView的相关功能，若您刚安装，请尝试重启服务器");
            console.sendMessage("§f>> §cPlugin VexView is not found!");
        }
        
        new VersionChecker().runTaskLaterAsynchronously(instance, 200L);
        
        metrics = new Metrics(this, 6343);
        metrics.addCustomChart(new Metrics.SingleLineChart("duel_amount", () -> duelAmountData.getAndClear()));
        metrics.addCustomChart(new Metrics.SingleLineChart("command_type_amount", () -> commandTypeAmountData.getAndClear()));
        
        console.sendMessage("§8[§bDantiao§8]");
        console.sendMessage("§f>> §a决斗插件V2已成功载入服务器！");
        console.sendMessage("§f>> §aDantiaoV2 by:Valorin");
    }

    @Override
    public void onDisable() {
    	energy.close();
    	hd.unload(0);
    	languageFileLoader.close();
    	if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
      	    RegPAPI.unhook();
      	}
        Bukkit.getScheduler().cancelTasks(instance);
        ConsoleCommandSender console = Bukkit.getConsoleSender();
        console.sendMessage("§8[§bDantiao§8]");
        console.sendMessage("§f>> §7决斗插件V2已关闭");
        console.sendMessage("§f>> §7DantiaoV2 by:Valorin");
    }
}

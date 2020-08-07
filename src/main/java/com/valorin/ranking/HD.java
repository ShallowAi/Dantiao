package com.valorin.ranking;

import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.DataFile.ranking;
import static com.valorin.configuration.languagefile.MessageBuilder.gmLog;
import static com.valorin.configuration.languagefile.MessageSender.gm;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import me.arasple.mc.trhologram.api.TrHologramAPI;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;
import com.valorin.Dantiao;
import com.valorin.ranking.type.TypeHolographicDisplays;
import com.valorin.ranking.type.TypeTrHologram;

public class HD {
	private boolean hasHD = false;
	private int useHD;//1-HolographicDisplays 2-TrHologram
	
	public void checkHD() {
		String hd = Dantiao.getInstance().getConfig().getString("hd");
		if (hd != null) {
			switch(hd) {
			case "HolographicDisplays" : 
				if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
					if (Bukkit.getPluginManager().getPlugin("HolographicDisplays").isEnabled()) {
				        useHD = 1;
	    	            hasHD = true;
	    	            break;
					}
				}
			case "TrHologram" : 
				if (Bukkit.getPluginManager().getPlugin("TrHologram") != null) {
					if (Bukkit.getPluginManager().getPlugin("TrHologram").isEnabled()) {
				        useHD = 2;
	    	            hasHD = true;
	    	            break;
					}
				}
			default :
				if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
					if (Bukkit.getPluginManager().getPlugin("HolographicDisplays").isEnabled()) {
				        useHD = 1;
	    	            hasHD = true;
	    	            break;
					}
				}
				if (Bukkit.getPluginManager().getPlugin("TrHologram") != null) {
					if (Bukkit.getPluginManager().getPlugin("TrHologram").isEnabled()) {
				        useHD = 2;
	    	            hasHD = true;
	    	            break;
					}
				}
			}
		}
	}
	
	public HD() {
		checkHD();
	    if (!hasHD) {
		    Bukkit.getConsoleSender().sendMessage("§8[§bDantiao§8]");
		    Bukkit.getConsoleSender().sendMessage("§e未发现 §6HolographicDisplays/TrHologram §e全息插件，将无法使用全息排行榜功能，若您刚安装，请尝试重启服务器");
		    Bukkit.getConsoleSender().sendMessage("§ePlugin §6HolographicDisplays/Trhologram §eis not found!HD ranking can't be used before you fix it");
		    return;
	    }
        
        load(0);
	}
	
	public void load(int n) {
		Dantiao plugin = Dantiao.getInstance();
		
	    List<String> winList = new ArrayList<String>();
	    List<String> winListData = ranking.getStringList("Win");
	    int max = 0;
        if (winListData.size() > 10) {
            max = 10;
        } else {
            max = winListData.size();
        }
        winList.add(gm("&b[star1]单挑-胜场排行榜[star2]"));
        for (int i = 0;i < max;i++) {
        	String rankingString = getRankingString(i, winListData, true);
        	if (rankingString != null) {
        		winList.add(rankingString);
        	}
        }
        List<String> KDList = new ArrayList<String>();
	    List<String> KDListData = ranking.getStringList("KD");
	    int max2 = 0;
        if (KDListData.size() > 10) {
            max2 = 10;
        } else {
            max2 = KDListData.size();
        }
        KDList.add(gm("&b[star1]单挑-KD比值排行榜[star2]"));
        for (int i = 0;i < max2;i++) {
        	String rankingString = getRankingString(i, KDListData, false);
        	if (rankingString != null) {
        		KDList.add(rankingString);
        	}
        }
        
		if (!hasHD) {
			return;
		}
		if (n == 0) {
			if (useHD == 1) {
				for (com.gmail.filoghost.holographicdisplays.api.Hologram hologram : HologramsAPI.getHolograms(plugin)) {
			        hologram.clearLines();
			    }
			}
            if (useHD == 2) {
            	if (TrHologramAPI.getHologramById("Win") != null) {
    		        TrHologramAPI.getHologramById("Win").delete();
            	}
            	if (TrHologramAPI.getHologramById("KD") != null) {
    		        TrHologramAPI.getHologramById("KD").delete();
            	}
			}
		}
		  
		if (n == 0 || n == 1) {
			if (useHD == 1) {
				if (TypeHolographicDisplays.hologramWin != null) {
					TypeHolographicDisplays.hologramWin.delete();
					TypeHolographicDisplays.hologramWin = null;
			    }
			}
            if (useHD == 2) {
            	if (TypeTrHologram.hologramWin != null) {
            		TypeTrHologram.hologramWin.delete();
            		TypeTrHologram.hologramWin = null;
			    }
			}
            
		    if (areas.getString("Dantiao-HD-Win.World") != null) {
	            World world = Bukkit.getWorld(areas.getString("Dantiao-HD-Win.World"));
	            if (world != null) {
	                double x = areas.getDouble("Dantiao-HD-Win.X");
	                double y = areas.getDouble("Dantiao-HD-Win.Y");
	                double z = areas.getDouble("Dantiao-HD-Win.Z");
	                if (useHD == 1) {
	                	TypeHolographicDisplays.hologramWin = (Hologram)HologramsAPI.createHologram(plugin, new Location(world,x,y,z));
	                	TypeHolographicDisplays.hologramWin.appendItemLine(new ItemStack(Material.GOLD_SWORD));
	 	                for (String rankingString : winList) {
	 	                	TypeHolographicDisplays.hologramWin.appendTextLine(rankingString);
	 	                }
	                }
	                if (useHD == 2) {
	                	List<String> winListEx = new ArrayList<String>();
	                	winListEx.add("item:GOLD_SWORD");
	                	winListEx.addAll(winList);
	                	TypeTrHologram.hologramWin = TrHologramAPI.createHologram((Plugin)plugin, "Win", new Location(world,x,y,z), winListEx);
	                }
	            } else {
	                Bukkit.getConsoleSender().sendMessage("§8[§bDantiao§8]");
	                Bukkit.getConsoleSender().sendMessage("§c检测到[胜场排行榜]的全息所在的世界不存在，全息图加载失败，建议将该全息图换个位置");
	                Bukkit.getConsoleSender().sendMessage("§cIt's found that the world which the HD of [Win Ranking] locate in is not exist!Please change the place for it!");
	            }
		    }
	    }
	    if (n == 0 || n == 2) {
			if (useHD == 1) {
				if (TypeHolographicDisplays.hologramKD != null) {
					TypeHolographicDisplays.hologramKD.delete();
					TypeHolographicDisplays.hologramKD = null;
			    }
			}
            if (useHD == 2) {
            	if (TypeTrHologram.hologramKD != null) {
            		TypeTrHologram.hologramKD.delete();
            		TypeTrHologram.hologramKD = null;
			    }
			}
            
		    if (areas.getString("Dantiao-HD-KD.World") != null) {
	            World world = Bukkit.getWorld(areas.getString("Dantiao-HD-KD.World"));
	            if (world != null) {
	                double x = areas.getDouble("Dantiao-HD-KD.X");
	                double y = areas.getDouble("Dantiao-HD-KD.Y");
	                double z = areas.getDouble("Dantiao-HD-KD.Z");
	                if (useHD == 1) {
	                	TypeHolographicDisplays.hologramKD = (Hologram)HologramsAPI.createHologram(plugin, new Location(world,x,y,z));
	                	TypeHolographicDisplays.hologramKD.appendItemLine(new ItemStack(Material.GOLD_AXE));
	 	                for (String rankingString : KDList) {
	 	                	TypeHolographicDisplays.hologramKD.appendTextLine(rankingString);
	 	                }
	                }
	                if (useHD == 2) {
	                	List<String> KDListEx = new ArrayList<String>();
	                	KDListEx.add("item:GOLD_AXE");
	                	KDListEx.addAll(KDList);
	                	TypeTrHologram.hologramKD = TrHologramAPI.createHologram((Plugin)plugin, "KD", new Location(world,x,y,z), KDListEx);
	                }
	            } else {
	                Bukkit.getConsoleSender().sendMessage("§8[§bDantiao§8]");
	                Bukkit.getConsoleSender().sendMessage("§c检测到[KD排行榜]的全息所在的世界不存在，全息图加载失败，建议将该全息图换个位置");
	                Bukkit.getConsoleSender().sendMessage("§cIt's found that the world which the HD of [KD Ranking] locate in is not exist!Please change the place for it!");
	            }
		    }
	    }
	}
		
	public void unload(int n) {
		if (!hasHD) {
			return;
		}
		if (n == 0 || n == 1) {
			if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
				if (TypeHolographicDisplays.hologramWin != null) {
					TypeHolographicDisplays.hologramWin.delete();
					TypeHolographicDisplays.hologramWin = null;
			    }
			}
			if (Bukkit.getPluginManager().getPlugin("TrHologram") != null) {
            	if (TypeTrHologram.hologramWin != null) {
            		TypeTrHologram.hologramWin.delete();
            		TypeTrHologram.hologramWin = null;
			    }
			}
		}
		if (n == 0 || n == 2) {
			if (Bukkit.getPluginManager().getPlugin("HolographicDisplays") != null) {
				if (TypeHolographicDisplays.hologramKD != null) {
					TypeHolographicDisplays.hologramKD.delete();
					TypeHolographicDisplays.hologramKD = null;
			    }
			}
			if (Bukkit.getPluginManager().getPlugin("TrHologram") != null) {
            	if (TypeTrHologram.hologramKD != null) {
            		TypeTrHologram.hologramKD.delete();
        			TypeTrHologram.hologramKD = null;
			    }
			}
		}
	}
	
	public boolean isEnabled() {
		return hasHD;
	}
	
	private String getRankingString(int rank,List<String> dataList,boolean isWin) {
		if (rank+1 > dataList.size()) {
			return null;
		} else {
			String playerName = dataList.get(rank).split("\\|")[0];
			BigDecimal bg = new BigDecimal(Double.valueOf(dataList.get(rank).split("\\|")[1]));
			double value = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

			switch(rank) {
			case 0 :
				if (isWin)
					return gmLog("&b&l[n1] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
				    return gmLog("&b&l[n1] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 1 :
		    	if (isWin)
					return gmLog("&e&l[n2] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&e&l[n2] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 2 :
		    	if (isWin)
					return gmLog("&6&l[n3] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&6&l[n3] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 3 :
		    	if (isWin)
					return gmLog("&b[n4] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n4] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 4 :
		    	if (isWin)
					return gmLog("&b[n5] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n5] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 5 :
		    	if (isWin)
					return gmLog("&b[n6] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n6] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 6 :
		    	if (isWin)
					return gmLog("&b[n7] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n7] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 7 :
		    	if (isWin)
					return gmLog("&b[n8] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n8] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 8 :
		    	if (isWin)
					return gmLog("&b[n9] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n9] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
		    case 9 :
		    	if (isWin)
					return gmLog("&b[n10] &f{player} &7[right] &a{value}场",null,"player value",new String[]{playerName,""+(int)value},false,true);
			    else
		    	    return gmLog("&b[n10] &f{player} &7[right] &a{value}",null,"player value",new String[]{playerName,""+value},false,true);
			}
			return null;
		}
	}
}

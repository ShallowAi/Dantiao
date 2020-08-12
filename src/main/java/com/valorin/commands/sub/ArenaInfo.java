package com.valorin.commands.sub;

import static com.valorin.arenas.ArenasManager.getArenasName;
import static com.valorin.configuration.DataFile.areas;
import static com.valorin.configuration.languagefile.MessageSender.gm;
import static com.valorin.configuration.languagefile.MessageSender.sm;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.valorin.arenas.ArenasManager;
import com.valorin.commands.SubCommand;

public class ArenaInfo extends SubCommand{

	public ArenaInfo() {
		super("arenainfo", "ainfo");
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  Player p = null;
	  if (sender instanceof Player)
	  { p = (Player)sender; }
	  if (getArenasName().size() == 0) {
		sm("&c服务器内没有设置任何竞技场！请联系管理员！",p);
		return true;
	  }
	  sm("&b各竞技场实时信息如下：",p);
	  for (String arenaEditName : getArenasName()) {
		String arenaDisplayName;
		if (areas.getString("Arenas."+arenaEditName+".Name") != null)
		{
		  arenaDisplayName = areas.getString("Arenas."+arenaEditName+".Name").replace("&", "§");
		} else {
		  arenaDisplayName = gm("&7未命名",p);
		}
		String state;
		if (ArenasManager.busyArenasName.contains(arenaEditName))
		{
		  state = gm("&c正在决斗中...",p);
		} else {
		  state = gm("&a空闲",p);
		}
		String watch;
		if (areas.getString("Arenas."+arenaEditName+".WatchingPoint.World") == null)
		{
		  watch = gm("&c不支持观战",p);
		} else {
		  watch = gm("&a可观战",p);
		}
		sm("&f[&r{arenadisplayname}&r&f] &2编号:{arenaeditname} {state} {watch}",p,
				"arenadisplayname arenaeditname state watch",new String[]{
				arenaDisplayName,
			    arenaEditName,
			    state,
			    watch},false);
	  }
	  if (p != null)
	  {
		sm("&b输入 &e/dt watch <编号> &b即可观战",p);
	  }
	  return true;
	}
}

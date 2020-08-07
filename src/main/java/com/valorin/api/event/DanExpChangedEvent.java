package com.valorin.api.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DanExpChangedEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;
    private Player player;
    private int before;
    private int now;
    /**
	 * 玩家在经验变更时调用
	 * @param player 玩家
	 * @param before 变更前的经验
	 * @param now 变更后的经验
	 */
    public DanExpChangedEvent(Player player,int before,int now)
    {
      this.player = player;
      this.before = before;
      this.now = now;
	}
    
    public Player getPlayer() {
    	return player;
    }
    
    public int getBefore() {
        return before;
    }
    
    public int getNow() {
    	return now;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
    public boolean isCancelled() {
        return cancelled;
    }
    
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}
